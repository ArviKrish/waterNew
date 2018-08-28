package com.practice.aravind.wahter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.view.Window;

public class RegisterActivity extends Activity{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

          Button button1 = findViewById(R.id.button2);
          Button button2 = findViewById(R.id.button3);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent i = new Intent(getApplicationContext(), MobileSignupActivity.class);
                startActivity(i);

            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent i = new Intent(getApplicationContext(), UserInfoActivity.class);
                startActivity(i);
            }
        });


    }

    public void onNext(View v){
        Intent i = new Intent(getApplicationContext(), UserInfoActivity.class);
        startActivity(i);
    }
}
