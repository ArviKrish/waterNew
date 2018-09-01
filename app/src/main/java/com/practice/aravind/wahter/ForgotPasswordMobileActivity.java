package com.practice.aravind.wahter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ForgotPasswordMobileActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_mobile);
    }

    public void onForgotPasswordNext(View v) {
        Intent i = new Intent(getApplicationContext(), ForgotPasswordOTP.class);
        startActivity(i);
    }
}
