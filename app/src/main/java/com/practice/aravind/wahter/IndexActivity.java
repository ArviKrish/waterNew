package com.practice.aravind.wahter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

/**
 * A login screen that offers login via email/password.
 */
public class IndexActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);


        Button button1 = findViewById(R.id.button5);
        Button button2 = findViewById(R.id.button);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
               /* Intent i = new Intent(getApplicationContext(), BillingInfoActivity.class);
                startActivity(i);*/

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);


            }
        });

    }

    public void onRegister(View v) {
        // Switching to Register screen
        Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(i);
    }

    public void onLogin(View v) {
        // Switching to Login screen
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(i);
    }

}




