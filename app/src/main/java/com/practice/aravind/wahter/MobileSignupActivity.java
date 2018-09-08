package com.practice.aravind.wahter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.practice.aravind.wahter.api.APIClient;
import com.practice.aravind.wahter.api.APIInterface;
import com.practice.aravind.wahter.documents.Response;
import com.practice.aravind.wahter.documents.UserMobileNumbers;
import com.practice.aravind.wahter.util.WahterConstants;
import com.practice.aravind.wahter.util.WahterUtility;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;

public class MobileSignupActivity extends Activity {

    private ProgressBar spinner;
    EditText phoneNumberText;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_mobile);
        Button next = findViewById(R.id.next);
        Button previous = findViewById(R.id.previous);
        phoneNumberText = (EditText) findViewById(R.id.phoneNumberSignUp);
        if(getIntent().hasExtra(WahterConstants.IS_OTP_VERIFICATION_SUCCESSFUL))
        if(getIntent().getBooleanExtra(WahterConstants.IS_OTP_VERIFICATION_SUCCESSFUL, false)){
            phoneNumberText.setText(getIntent().getStringExtra(WahterConstants.PHONE_NUMBER));
            phoneNumberText.setEnabled(false);
            getIntent().removeExtra(WahterConstants.IS_OTP_VERIFICATION_SUCCESSFUL);

            UserMobileNumbers userMobileNumbers = new UserMobileNumbers();
            userMobileNumbers.setPhoneNumber(phoneNumberText.getText().toString().trim());
            userMobileNumbers.setNumberVerified(true);
            apiInterface = APIClient.getClient().create(APIInterface.class);
            Call<Response> authenticateService = apiInterface.userMobileNumber(userMobileNumbers);
            authenticateService.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                    if (response.isSuccessful()) {
                        WahterUtility.showToast(getApplicationContext(), response.body().getMessage());
                        phoneNumberText.setEnabled(true);
                    } else {
                        Converter<ResponseBody, Response> converter
                                = APIClient.getClient().responseBodyConverter(Response.class, (Annotation[]) new Annotation[0]);
                        Response errorResponse = null;
                        try {
                            errorResponse = converter.convert(response.errorBody());
                            String textReceived = errorResponse.getMessage();
                            WahterUtility.showToast(getApplicationContext(), textReceived);
                            phoneNumberText.setEnabled(true);

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    //Logging goes here
                    Toast.makeText(getApplicationContext(), "Unable to connect to server", Toast.LENGTH_LONG).show();
                    phoneNumberText.setEnabled(true);
                    t.printStackTrace();
                }
            });

        }

        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {


                boolean isValidationSuccessful = true;
                final String phoneNumber = phoneNumberText.getText().toString();
                if (!isValidPhone(phoneNumber)) {
                    isValidationSuccessful = false;
                    phoneNumberText.setError("Invalid Phone number");
                }
                if (isValidationSuccessful) {
                    phoneNumberText.setEnabled(false);
                    apiInterface = APIClient.getClient().create(APIInterface.class);

                    Call<Response> authenticateService = apiInterface.validatePhoneNumberForSignUp(phoneNumber);
                    authenticateService.enqueue(new Callback<Response>() {
                        @Override
                        public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                            if (response.isSuccessful()) {
                                processResponse(response.body());
                            } else {
                                Converter<ResponseBody, Response> converter
                                        = APIClient.getClient().responseBodyConverter(Response.class, (Annotation[]) new Annotation[0]);
                                Response errorResponse = null;
                                try {
                                    errorResponse = converter.convert(response.errorBody());
                                    String textReceived = errorResponse.getMessage();
                                    Toast toast = Toast.makeText(getApplicationContext(), textReceived, Toast.LENGTH_LONG);
                                    toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 250);
                                    toast.show();
                                    phoneNumberText.setEnabled(true);

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }

                        private void processResponse(Response serviceResponse) {
                            if(serviceResponse.getResponseCode().equalsIgnoreCase("001")){
                                 Intent indexActivity = new Intent(MobileSignupActivity.this, RegisterActivity.class);
                                startActivity(indexActivity);
                            } else if(serviceResponse.getResponseCode().equalsIgnoreCase("002")) {
                                //todo include authentication for registration
                                Intent indexActivity = new Intent(MobileSignupActivity.this, OTPVerification.class);
                                indexActivity.putExtra(WahterConstants.NEXT_ACTIVITY, WahterConstants.MOBILE_SIGNUP_ACTIVITY);
                                indexActivity.putExtra(WahterConstants.PHONE_NUMBER, phoneNumber);
                                startActivity(indexActivity);
                            }

                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {
                            //Logging goes here
                            Toast.makeText(getApplicationContext(), "Unable to connect to server", Toast.LENGTH_LONG).show();
                            phoneNumberText.setEnabled(true);
                            t.printStackTrace();
                        }
                    });
                    // Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                    // startActivity(i);
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Code here executes on main thread after user presses button
                Intent previous = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(previous);
            }
        });

    }

    private boolean isValidPhone(String phoneNumber) {
        if (phoneNumber != null && phoneNumber.length() == 10) {
            return true;
        }
        return false;
    }

}

