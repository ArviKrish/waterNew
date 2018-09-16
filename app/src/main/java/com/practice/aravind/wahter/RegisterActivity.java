package com.practice.aravind.wahter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageButton;

public class RegisterActivity extends Activity {
    String mobileNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final Bundle signUpBundle = getIntent().getExtras();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ImageButton previousBtn = findViewById(R.id.companyPreviousBtn);
        ImageButton nextBtn = findViewById(R.id.companyNextBtn);
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
                //Bundle signUpBundle = new Bundle();

                EditText name = (EditText) findViewById(R.id.companyName);
                String companyName = name.getText().toString();
                signUpBundle.putString("companyName", companyName);

                EditText type = (EditText) findViewById(R.id.companyType);
                String companyType = type.getText().toString();
                signUpBundle.putString("companyType", companyType);

                EditText option = (EditText) findViewById(R.id.companyOption);
                String companyOption = option.getText().toString();
                signUpBundle.putString("companyOption", companyOption);

                EditText email = (EditText) findViewById(R.id.companyEmail);
                String companyEmail = email.getText().toString();
                signUpBundle.putString("companyEmail", companyEmail);

                intent.putExtras(signUpBundle);
                startActivity(intent);
            }
        });
    }
}
