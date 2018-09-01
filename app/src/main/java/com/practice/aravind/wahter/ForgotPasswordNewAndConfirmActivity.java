package com.practice.aravind.wahter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ForgotPasswordNewAndConfirmActivity extends AppCompatActivity {

    private EditText newPasswordTxt;
    private EditText confirmPasswordTxt;
    private String phoneNumber;

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

        

    }

    private boolean isValidPassword(String newPassword, String confirmPassword) {
        if (newPassword.equalsIgnoreCase(confirmPassword))
            return true;
        return false;
    }


}
