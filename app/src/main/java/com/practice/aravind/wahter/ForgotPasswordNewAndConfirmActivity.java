package com.practice.aravind.wahter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.practice.aravind.wahter.documents.Users;

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
    private APIInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_new_and_confirm);
        phoneNumber = getIntent().getStringExtra("phoneNumber");
    }

    public void onChangePassword(View v) {

        newPasswordTxt = (EditText) findViewById(R.id.newPasswordTxt);
        confirmPasswordTxt = (EditText) findViewById(R.id.confirmPasswordTxt);

        boolean isValidationSuccessful = true;
        final String newPassword = newPasswordTxt.getText().toString().trim();
        final String confirmPassword = confirmPasswordTxt.getText().toString().trim();
        if (!isValidPassword(newPassword, confirmPassword)) {
            isValidationSuccessful = false;
            confirmPasswordTxt.setError("Password not same");
            confirmPasswordTxt.requestFocus();
            return;
        }

        newPasswordTxt.setEnabled(false);
        confirmPasswordTxt.setEnabled(false);
        apiInterface = APIClient.getClient().create(APIInterface.class);

        Users users = new Users();
        users.setPhoneNumber(phoneNumber);
        users.setPassword(newPassword);
        Call<Response> authenticateService = apiInterface.updateuser(users);
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
                        newPasswordTxt.setEnabled(true);
                        confirmPasswordTxt.setEnabled(true);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            private void processResponse(Response serviceResponse) {
                Toast toast = Toast.makeText(getApplicationContext(), serviceResponse.getMessage(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 250);
                toast.show();
                Intent indexActivity = new Intent(ForgotPasswordNewAndConfirmActivity.this, LoginActivity.class);
                startActivity(indexActivity);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                //Logging goes here
                Toast.makeText(getApplicationContext(), "Unable to connect to server"+t.getMessage(), Toast.LENGTH_LONG).show();
                newPasswordTxt.setEnabled(true);
                confirmPasswordTxt.setEnabled(true);
                t.printStackTrace();
            }
        });

        

    }

    private boolean isValidPassword(String newPassword, String confirmPassword) {
        if (newPassword.equalsIgnoreCase(confirmPassword))
            return true;
        return false;
    }


}
