package com.practice.aravind.wahter;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;


import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;

public class LoginActivity extends Activity {

    private ProgressBar spinner;
    EditText phoneNumberText;
    EditText passwordText;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        spinner=(ProgressBar)findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

    }

    public void onLogin(View v) {


        Intent loginIntent = new Intent(getApplicationContext(), LoginActivity.class);
        loginIntent.putExtra("keep", true);

        phoneNumberText = (EditText) findViewById(R.id.phoneNumberTxt);
        passwordText = (EditText) findViewById(R.id.passwordTxt);
        boolean isValidationSuccessful = true;
        final String phoneNumber = phoneNumberText.getText().toString();
        if (!isValidPhone(phoneNumber)) {
            isValidationSuccessful = false;
            phoneNumberText.setError("Invalid Phone number");
        }
        final String password = passwordText.getText().toString();
        if (!isValidPassword(password)) {
            isValidationSuccessful = false;
            passwordText.setError("Invalid Password");
        }
        spinner=(ProgressBar)findViewById(R.id.progressBar);

        if (isValidationSuccessful) {
            spinner.setVisibility(View.VISIBLE);
            phoneNumberText.setEnabled(false);
            passwordText.setEnabled(false);

            apiInterface = APIClient.getClient().create(APIInterface.class);

            Call<com.practice.aravind.wahter.Response> authenticateService = apiInterface.authenticateUser(phoneNumber, password);
            authenticateService.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                    if(response.isSuccessful()) {
                        processResponse(response.body());
                        Response serviceResponse = response.body();
                        String textReceived = serviceResponse.getMessage();
                        Toast.makeText(getApplicationContext(), textReceived, Toast.LENGTH_LONG).show();
                    } else {
                        Converter<ResponseBody, Response> converter
                                = APIClient.getClient().responseBodyConverter(Response.class, (Annotation[]) new Annotation[0]);
                        Response errorResponse = null;
                        try {
                            errorResponse = converter.convert(response.errorBody());
                            processResponse(errorResponse);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

                private void processResponse(Response serviceResponse){

                    String textReceived = serviceResponse.getMessage();
                    Toast toast = Toast.makeText(getApplicationContext(), textReceived, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 250);
                    toast.show();
                    if (textReceived.equalsIgnoreCase("Authentication Success")) {
                        Intent indexActivity = new Intent(LoginActivity.this, IndexActivity.class);
                        startActivity(indexActivity);
                    } else {
                            phoneNumberText .setEnabled(true);
                            passwordText.setEnabled(true);
                            passwordText.setText("");
                            spinner.setVisibility(View.GONE);
                        }
                    }
                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    //Logging goes here
                    Toast.makeText(getApplicationContext(), "Unable to connect to server", Toast.LENGTH_LONG).show();
                    phoneNumberText .setEnabled(true);
                    passwordText.setEnabled(true);
                    passwordText.setText("");
                    spinner.setVisibility(View.GONE);
                    t.printStackTrace();
                }
            });
        }
    }

    private boolean isValidPhone(String phoneNumber) {
        if (phoneNumber != null && phoneNumber.length() == 10) {
            return true;
        }
        return false;
    }

    private boolean isValidPassword(String pass) {
        if (pass != null && pass.length() >= 6) {
            return true;
        }
        return false;
    }
}