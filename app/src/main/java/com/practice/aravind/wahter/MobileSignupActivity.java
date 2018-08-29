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
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                phoneNumberText = (EditText) findViewById(R.id.phoneNumberTxt);
                boolean isValidationSuccessful = true;
                final String phoneNumber = phoneNumberText.getText().toString();
                if (!isValidPhone(phoneNumber)) {
                    isValidationSuccessful = false;
                    phoneNumberText.setError("Invalid Phone number");
                }
                if (isValidationSuccessful) {
                    phoneNumberText.setEnabled(false);
                    apiInterface = APIClient.getClient().create(APIInterface.class);

                    Call<Response> authenticateService = apiInterface.validatePhoneNumber(phoneNumber);
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
                            Intent indexActivity = new Intent(MobileSignupActivity.this, RegisterActivity.class);
                            startActivity(indexActivity);
                        }

                        @Override
                        public void onFailure(Call<Response> call, Throwable t) {
                            //Logging goes here
                            Toast.makeText(getApplicationContext(), "Unable to connect to server", Toast.LENGTH_LONG).show();
                            phoneNumberText.setEnabled(true);
                            spinner.setVisibility(View.GONE);
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

