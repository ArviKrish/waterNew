package com.practice.aravind.wahter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

public class BillingInfoActivity extends AppCompatActivity {

    /*String companyName;
    String companyType;
    String companyOption;
    String companyEmailAddress;
    String mobileNumber;
    String userName;
    String userDesignation;
    String userPhoneNumber;
    String userAltNumber;
    String userEmail;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_billing_info);
        final Bundle signUpBundle = getIntent().getExtras();

       /* companyName = signUpBundle.getString("companyName");
        companyType = signUpBundle.getString("companyType");
        companyOption = signUpBundle.getString("companyOption");
        companyEmailAddress = signUpBundle.getString("companyEmailAddress");
        mobileNumber = signUpBundle.getString("mobileNumber");
        userName = signUpBundle.getString("userName");
        userDesignation = signUpBundle.getString("userDesignation");
        userPhoneNumber = signUpBundle.getString("userPhoneNumber");
        userAltNumber = signUpBundle.getString("userAltNumber");
        userEmail = signUpBundle.getString("userEmail");
*/

        ImageButton previousBtn = findViewById(R.id.previousBtn);
        ImageButton nextBtn = findViewById(R.id.nextBtn);
        previousBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent i = new Intent(getApplicationContext(), UserInfoActivity.class);
                startActivity(i);

            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent intent = new Intent(getApplicationContext(), DeliveryInfoActivity.class);
                // TODO: 02/09/18 get billing info
                /*signUpBundle.putString("companyName",companyName);
                signUpBundle.putString("companyType",companyType);
                signUpBundle.putString("companyOption",companyOption);
                signUpBundle.putString("companyEmailAddress",companyEmailAddress);
                signUpBundle.putString("mobileNumber",mobileNumber);
                signUpBundle.putString("userName",userName);
                signUpBundle.putString("userDesignation",userDesignation);
                signUpBundle.putString("userPhoneNumber",userPhoneNumber);
                signUpBundle.putString("userAltNumber",userAltNumber);
                signUpBundle.putString("userEmail",userEmail);*/
                intent.putExtras(signUpBundle);
                startActivity(intent);
            }
        });

    }
}
