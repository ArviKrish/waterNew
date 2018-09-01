package com.practice.aravind.wahter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.Window;
import android.widget.EditText;

public class RegisterActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button previousBtn = findViewById(R.id.previousBtn);
        Button nextBtn = findViewById(R.id.nextBtn);
        previousBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent i = new Intent(getApplicationContext(), MobileSignupActivity.class);
                startActivity(i);

            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(getApplicationContext(), UserInfoActivity.class);
                EditText emailAddressTxt = (EditText) findViewById(R.id.emailAddressTxt);
                final String emailAddress = emailAddressTxt.getText().toString();
                intent.putExtra("emailAddress", emailAddress);
                startActivity(intent);
            }
        });


    }

    public void onNext(View v) {
        Intent i = new Intent(getApplicationContext(), UserInfoActivity.class);
        startActivity(i);
    }
}
