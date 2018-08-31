package com.practice.aravind.wahter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class UserInfoActivity extends AppCompatActivity {

    String emailAddress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        Bundle bundle = getIntent().getExtras();
        emailAddress = bundle.getString("emailAddress");

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
                intent.putExtra("emailAddress", emailAddress);
                startActivity(intent);


            }
        });

    }
}
