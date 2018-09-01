package com.practice.aravind.wahter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;

public class ForgotPasswordMobileActivity extends Activity {

    private EditText phoneNumberTxt;
    APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_mobile);
    }

    public void onForgotPasswordNext(View v) {

        phoneNumberTxt = findViewById(R.id.phoneNumberTxt);
        final String phoneNumber = phoneNumberTxt.getText().toString().trim();

        if (phoneNumber.isEmpty() || phoneNumber.length() < 10) {
            phoneNumberTxt.setError("Valid number is required");
            phoneNumberTxt.requestFocus();
            return;
        }

        phoneNumberTxt.setEnabled(false);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        Call<Response> authenticateService = apiInterface.validatePhoneNumber(phoneNumber);
        authenticateService.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                if (response.isSuccessful()) {
                    processResponse(response.body());
                } else {

                    Intent indexActivity = new Intent(ForgotPasswordMobileActivity.this, ForgotPasswordOTP.class);
                    indexActivity.putExtra("phoneNumber", phoneNumber);
                    startActivity(indexActivity);

                }

            }

            private void processResponse(Response serviceResponse) {
                String textReceived = serviceResponse.getMessage();
                Toast toast = Toast.makeText(getApplicationContext(), textReceived, Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 250);
                toast.show();
                phoneNumberTxt.setEnabled(true);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                //Logging goes here
                Toast.makeText(getApplicationContext(), "Unable to connect to server", Toast.LENGTH_LONG).show();
                phoneNumberTxt.setEnabled(true);
                t.printStackTrace();
            }
        });
    }

    /*@Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }*/

}
