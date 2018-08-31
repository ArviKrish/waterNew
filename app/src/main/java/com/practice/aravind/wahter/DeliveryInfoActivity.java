package com.practice.aravind.wahter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;

public class DeliveryInfoActivity extends AppCompatActivity {
    String emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        emailAddress = bundle.getString("emailAddress");


        setContentView(R.layout.activity_delivery_info);
        Button submitBtn = findViewById(R.id.submitBtn);
        Button previousBtn = findViewById(R.id.previousBtn);
        previousBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent i = new Intent(getApplicationContext(), BillingInfoActivity.class);
                startActivity(i);

            }
        });



        submitBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                EditText  companyAddressTxt = (EditText) findViewById(R.id.companyAddressTxt);
                final String companyAddress = companyAddressTxt.getText().toString();
                System.out.println("XXX" + companyAddress);

                System.out.println("got" + emailAddress);
            }


        });

    }
}
