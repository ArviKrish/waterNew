package com.practice.aravind.wahter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.practice.aravind.wahter.api.APIClient;
import com.practice.aravind.wahter.api.APIInterface;
import com.practice.aravind.wahter.documents.Response;
import com.practice.aravind.wahter.documents.Users;
import com.practice.aravind.wahter.util.WahterConstants;
import com.practice.aravind.wahter.util.WahterUtility;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;

public class ForgotPasswordNewAndConfirmActivity extends AppCompatActivity {

    private EditText newPasswordTxt;
    private EditText confirmPasswordTxt;
    private String phoneNumber;
    private APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_new_and_confirm);
        phoneNumber = getIntent().getStringExtra(WahterConstants.PHONE_NUMBER);
    }

    public void onChangePassword(View v) {
        newPasswordTxt = (EditText) findViewById(R.id.newPasswordTxt);
        confirmPasswordTxt = (EditText) findViewById(R.id.confirmPasswordTxt);
        boolean isValidationSuccessful = true;
        final String newPassword = newPasswordTxt.getText().toString().trim();
        final String confirmPassword = confirmPasswordTxt.getText().toString().trim();

        if (!WahterUtility.isValidPassword(newPassword)) {
            isValidationSuccessful = false;
            newPasswordTxt.setError(WahterConstants.INVALID_PASSWORD);
            newPasswordTxt.requestFocus();
            return;
        }
        if (!WahterUtility.checkforSamePassword(newPassword, confirmPassword)) {
            isValidationSuccessful = false;
            WahterUtility.showToast(getApplicationContext(), WahterConstants.PASSWORD_CONFIRM_ERROR);
            confirmPasswordTxt.requestFocus();
            //return;
        }
        if (!isValidationSuccessful) {
            return;
        }

        if(FirebaseAuth.getInstance().getCurrentUser() !=null) {
            if (!WahterUtility.checkforSame(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber(), WahterConstants.COUNTRY_CODE + phoneNumber)) {
                WahterUtility.showToast(getApplicationContext(), "Please Try Again...");
                FirebaseAuth.getInstance().signOut();
                FirebaseAuth.getInstance().signOut();
            }
        }

        newPasswordTxt.setEnabled(false);
        confirmPasswordTxt.setEnabled(false);
        Users users = new Users();
        users.setPhoneNumber(phoneNumber);
        users.setPassword(newPassword);
        Call<Response> updateUserService = apiInterface.updateUser(users);
        updateUserService.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    processResponse(response.body());
                } else {
                    Response errorResponse = WahterUtility.extractError(response);
                    String textReceived = errorResponse.getMessage();
                    WahterUtility.showToast(getApplicationContext(), textReceived);
                    newPasswordTxt.setEnabled(true);
                    confirmPasswordTxt.setEnabled(true);
                }
            }

            private void processResponse(Response serviceResponse) {
                FirebaseAuth.getInstance().signOut();
                WahterUtility.showToast(getApplicationContext(), serviceResponse.getMessage());
                Intent indexActivity = new Intent(ForgotPasswordNewAndConfirmActivity.this, LoginActivity.class);
                startActivity(indexActivity);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                WahterUtility.showToast(getApplicationContext(),WahterConstants.CONNECTION_ERROR);
                newPasswordTxt.setEnabled(true);
                confirmPasswordTxt.setEnabled(true);
                t.printStackTrace();
            }
        });
    }
}
