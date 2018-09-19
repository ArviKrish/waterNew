package com.practice.aravind.wahter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.practice.aravind.wahter.util.WahterConstants;
import com.practice.aravind.wahter.util.WahterUtility;

import java.util.concurrent.TimeUnit;

public class OTPVerification extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText otpTxt;
    private String phoneNumber;
    private String verificationId;
    private Class nextActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.otp_verification);
        otpTxt = findViewById(R.id.otpTxt);
        phoneNumber = getIntent().getStringExtra(WahterConstants.PHONE_NUMBER);
        nextActivity = WahterUtility.getClass(getIntent().getStringExtra(WahterConstants.NEXT_ACTIVITY));
        mAuth = FirebaseAuth.getInstance();
        sendVerificationCode(WahterConstants.COUNTRY_CODE + phoneNumber);
    }

    public void onForgotPasswordOTP(View v) {
        final String otp = otpTxt.getText().toString().trim();
        if (!WahterUtility.isValidOTP(otp)) {
            otpTxt.setError(WahterConstants.INVALID_OTP);
            otpTxt.requestFocus();
            return;
        }
        otpTxt.setEnabled(false);
        verifyCode(otp);
    }

    private void verifyCode(String otp) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, otp);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Intent intent = new Intent(OTPVerification.this, nextActivity);
                            intent.putExtra(WahterConstants.PHONE_NUMBER, phoneNumber);
                            intent.putExtra(WahterConstants.IS_OTP_VERIFICATION_SUCCESSFUL, true);
                            startActivity(intent);
                        } else {
                            otpTxt.setEnabled(true);
                            WahterUtility.showToast(OTPVerification.this, task.getException().getMessage());
                        }
                    }
                });
    }

    private void sendVerificationCode(String phoneNumber) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(phoneNumber,WahterConstants.LONG_60, TimeUnit.SECONDS, TaskExecutors.MAIN_THREAD, mCallBack);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String verificationIdReceived, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(verificationIdReceived, forceResendingToken);
            verificationId = verificationIdReceived;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String otp = phoneAuthCredential.getSmsCode();
            if (!otp.isEmpty()) {
                otpTxt.setText(otp);
                verifyCode(otp);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            WahterUtility.showToast(OTPVerification.this, WahterConstants.OTP_FAILED);
            otpTxt.setEnabled(true);
            e.printStackTrace();
        }
    };
}
