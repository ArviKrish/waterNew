package com.practice.aravind.wahter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class ForgotPasswordOTP extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_otp);
    }

    public void onForgotPasswordOTP(View v) {
        Intent i = new Intent(getApplicationContext(), ForgotPasswordNewAndConfirmActivity.class);
        startActivity(i);
    }
}
