package com.practice.aravind.wahter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ForgotPasswordOTP extends AppCompatActivity {

    private String verificationId;
    private FirebaseAuth mAuth;
    private EditText otpTxt;
    String phoneNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_otp);
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        sendVerificationCode("+91" + phoneNumber);

    }

    public void onForgotPasswordOTP(View v) {

        String phoneNumber = getIntent().getStringExtra("phoneNumber");
        mAuth = FirebaseAuth.getInstance();
        otpTxt = findViewById(R.id.otpTxt);
        otpTxt = findViewById(R.id.otpTxt);
        String otp = otpTxt.getText().toString().trim();

        if (otp.isEmpty() || otp.length() < 6) {

            otpTxt.setError("Enter code...");
            otpTxt.requestFocus();
            return;
        }
        verifyCode(otp);
    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        signInWithCredential(credential);
    }

    private void signInWithCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //todo //FirebaseAuth.getInstance().signOut();
                            Intent intent = new Intent(ForgotPasswordOTP.this, ForgotPasswordNewAndConfirmActivity.class);
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            intent.putExtra("phoneNumber", phoneNumber);
                            startActivity(intent);

                        } else {
                            Toast.makeText(ForgotPasswordOTP.this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void sendVerificationCode(String number) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallBack
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallBack = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            verificationId = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if (code != null) {
                otpTxt.setText(code);
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(ForgotPasswordOTP.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };
}
