package com.practice.aravind.wahter;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.practice.aravind.wahter.api.APIClient;
import com.practice.aravind.wahter.api.APIInterface;
import com.practice.aravind.wahter.documents.Response;
import com.practice.aravind.wahter.util.WahterConstants;
import com.practice.aravind.wahter.util.WahterUtility;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;

public class LoginActivity extends Activity {

    private ProgressBar spinner;
    private EditText phoneNumberText;
    private EditText passwordText;
    private APIInterface apiInterface;
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);
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
                    WahterUtility.showToast(getApplicationContext(),textReceived);
                    if (serviceResponse.getResponseCode().equalsIgnoreCase(WahterConstants.ERROR_CODE_001)) {
                        Intent indexActivity = new Intent(LoginActivity.this, RegisterActivity.class);
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
                    Snackbar snackbar = Snackbar
                            .make(constraintLayout, WahterConstants.CONNECTION_ERROR, Snackbar.LENGTH_LONG)
                            .setAction("RETRY", new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    WahterUtility.showToast(getApplicationContext(),"To be implemented");
                                }
                            });
                    snackbar.setActionTextColor(Color.RED);
                    View sbView = snackbar.getView();
                    TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.YELLOW);
                    snackbar.show();
                    //WahterUtility.showToast(getApplicationContext(),WahterConstants.CONNECTION_ERROR);
                    phoneNumberText.setEnabled(true);
                    passwordText.setEnabled(true);
                    passwordText.setText(WahterConstants.EMPTY_STRING);
                    spinner.setVisibility(View.GONE);
                    t.printStackTrace();
                }
            });

    }

    public void onForgotPassword(View v) {
        startActivity(new Intent(LoginActivity.this, ForgotPasswordMobileActivity.class));
    }

    public void onSignUpBtnClick(View v) {
        startActivity(new Intent(LoginActivity.this, MobileSignupActivity.class));
    }

}