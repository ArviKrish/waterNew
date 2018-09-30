package com.practice.aravind.wahter;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.practice.aravind.wahter.api.APIClient;
import com.practice.aravind.wahter.api.APIInterface;
import com.practice.aravind.wahter.documents.Response;
import com.practice.aravind.wahter.receiver.ConnectionReceiver;
import com.practice.aravind.wahter.util.WahterConstants;
import com.practice.aravind.wahter.util.WahterUtility;

import retrofit2.Call;
import retrofit2.Callback;

public class LoginActivity_With_Broadcast_Registration extends Activity {

    ConnectionReceiver receiver;
    IntentFilter intentFilter;
    private ProgressBar spinner;
    private EditText phoneNumberText;
    private EditText passwordText;
    private APIInterface apiInterface;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onResume() {
        super.onResume();
        registerReceiver(receiver, intentFilter);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
        receiver = new ConnectionReceiver();
        intentFilter = new IntentFilter("com.practice.aravind.wahter.LoginActivity");


        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(LoginActivity_With_Broadcast_Registration.this,
                Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            System.out.println("X");
            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(LoginActivity_With_Broadcast_Registration.this,
                    Manifest.permission.READ_PHONE_STATE)) {
                System.out.println("Y");
                ActivityCompat.requestPermissions(LoginActivity_With_Broadcast_Registration.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        1);
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                System.out.println("Z");
                ActivityCompat.requestPermissions(LoginActivity_With_Broadcast_Registration.this,
                        new String[]{Manifest.permission.READ_PHONE_STATE},
                        1);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            System.out.println("A");
        }


    }

    public void onLogin(View v) {
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        phoneNumberText = (EditText) findViewById(R.id.phoneNumberTxt);
        passwordText = (EditText) findViewById(R.id.passwordTxt);
        constraintLayout = (ConstraintLayout) findViewById(R.id.linearLayout2);
        boolean isValidationSuccessful = true;
        final String password = passwordText.getText().toString().trim();
        if (!WahterUtility.isValidPassword(password)) {
            isValidationSuccessful = false;
            passwordText.setError(WahterConstants.INVALID_PASSWORD);
            passwordText.requestFocus();
        }
        final String phoneNumber = phoneNumberText.getText().toString().trim();
        if (!WahterUtility.isValidPhone(phoneNumber)) {
            isValidationSuccessful = false;
            phoneNumberText.setError(WahterConstants.INVALID_PHONE_NUMBER);
            phoneNumberText.requestFocus();
        }
        if (!isValidationSuccessful) {
            return;
        }
        spinner.setVisibility(View.VISIBLE);
        phoneNumberText.setEnabled(false);
        passwordText.setEnabled(false);

        apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<Response> authenticateService = apiInterface.authenticateUser(phoneNumber, password);
        authenticateService.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                if (response.isSuccessful()) {
                    processResponse(response.body());
                } else {
                    Response errorResponse = WahterUtility.extractError(response);
                    processResponse(errorResponse);
                }
            }

            private void processResponse(Response serviceResponse) {

                String textReceived = serviceResponse.getMessage();
                WahterUtility.showToast(getApplicationContext(), textReceived);
                if (serviceResponse.getResponseCode().equalsIgnoreCase(WahterConstants.ERROR_CODE_001)) {
                    Intent indexActivity = new Intent(LoginActivity_With_Broadcast_Registration.this, RegisterActivity.class);
                    startActivity(indexActivity);
                } else {
                    phoneNumberText.setEnabled(true);
                    passwordText.setEnabled(true);
                    passwordText.setText(WahterConstants.EMPTY_STRING);
                    spinner.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                //todo logging
                View.OnClickListener listener = new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        WahterUtility.showToast(getApplicationContext(), "To be implemented");
                    }
                };
                WahterUtility.showSnackBar(constraintLayout, WahterConstants.CONNECTION_ERROR, WahterConstants.RETRY, listener);
                //WahterUtility.showSnackBar(constraintLayout, WahterConstants.CONNECTION_ERROR, WahterConstants.EMPTY_STRING, null);
                phoneNumberText.setEnabled(true);
                passwordText.setEnabled(true);
                passwordText.setText(WahterConstants.EMPTY_STRING);
                spinner.setVisibility(View.GONE);
                t.printStackTrace();
            }
        });

    }


    public void onForgotPassword(View v) {
        startActivity(new Intent(LoginActivity_With_Broadcast_Registration.this, ForgotPasswordMobileActivity.class));
    }

    public void onSignUpBtnClick(View v) {
        startActivity(new Intent(LoginActivity_With_Broadcast_Registration.this, MobileSignupActivity.class));
    }

}