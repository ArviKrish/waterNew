package com.practice.aravind.wahter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LoginActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Button button1 = findViewById(R.id.login);
        Button button2 = findViewById(R.id.signup);

        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
               /* Intent i = new Intent(getApplicationContext(), BillingInfoActivity.class);
                startActivity(i);*/
                Intent i = new Intent(getApplicationContext(), MobileSignupActivity.class);
                startActivity(i);
            }
        });
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent i = new Intent(getApplicationContext(), MobileSignupActivity.class);
                startActivity(i);
            }
        });

    }

    //todo validation for login -- phone number and password

}

