package com.practice.aravind.wahter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class UserInfoActivity extends AppCompatActivity {

    String companyName;
    String companyType;
    String companyOption;
    String companyEmail;
    String mobileNumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_info);
        final Bundle signUpBundle = getIntent().getExtras();

   /*     companyName = signUpBundle.getString("companyName");
        companyType = signUpBundle.getString("companyType");
        companyOption = signUpBundle.getString("companyOption");
        companyEmail = signUpBundle.getString("companyEmail");
        mobileNumber = signUpBundle.getString("mobileNumber");*/

        Button previousBtn = findViewById(R.id.previousBtn);
        Button nextBtn = findViewById(R.id.nextBtn);

        previousBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent i = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(i);

            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(getApplicationContext(), BillingInfoActivity.class);
                EditText name = (EditText) findViewById(R.id.userName);
                String userName = name.getText().toString();
                signUpBundle.putString("userName", userName);
                EditText designation = (EditText) findViewById(R.id.userDesignation);
                String userDesignation = designation.getText().toString();
                signUpBundle.putString("userDesignation", userDesignation);
                EditText phone = (EditText) findViewById(R.id.userPhoneNumber);
                String userPhoneNumber = phone.getText().toString();
                signUpBundle.putString("userPhoneNumber", userPhoneNumber);
                EditText altPhone = (EditText) findViewById(R.id.UserAltNumber);
                String UserAltnumber = altPhone.getText().toString();
                signUpBundle.putString("UserAltnumber", UserAltnumber);
                EditText email = (EditText) findViewById(R.id.userEmail);
                String userEmail = email.getText().toString();

                signUpBundle.putString("userEmail", userEmail);
                /*signUpBundle.putString("companyName",companyName);
                signUpBundle.putString("companyType",companyType);
                signUpBundle.putString("companyOption",companyOption);
                signUpBundle.putString("companyEmailAddress",companyEmail);
                signUpBundle.putString("mobileNumber",mobileNumber);*/
                intent.putExtras(signUpBundle);
                startActivity(intent);
            }
        });

    }
}
