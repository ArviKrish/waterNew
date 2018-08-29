package com.practice.aravind.wahter;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.os.Handler;
import android.app.ProgressDialog;

import com.practice.aravind.wahter.LoginActivity;
import com.practice.aravind.wahter.R;
import com.practice.aravind.wahter.RegisterActivity;

/**
 * A login screen that offers login via email/password.
 */
public class IndexActivity extends AppCompatActivity {
    private int progressStatus = 0;
    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);


        Button login = findViewById(R.id.loginBtn);
        Button signup = findViewById(R.id.signUpBtn);

        login.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {


                // Initialize a new instance of progress dialog
                final ProgressDialog pd = new ProgressDialog(IndexActivity.this);

                // Set progress dialog style spinner
                pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

                // Set the progress dialog title and message
/*
                pd.setTitle("Title of progress dialog.");
*/
                pd.setMessage("Loading.........");

                // Set the progress dialog background color
                pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));

                pd.setIndeterminate(false);

                // Finally, show the progress dialog
                pd.show();

                // Set the progress status zero on each button click
                progressStatus = 0;

                // Start the lengthy operation in a background thread
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        while (progressStatus < 100) {
                            // Update the progress status
                            progressStatus += 1;

                            // Try to sleep the thread for 20 milliseconds
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            // Update the progress bar
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    // Update the progress status
                                    pd.setProgress(progressStatus);
                                    // If task execution completed
                                    if (progressStatus == 100) {
                                        // Dismiss/hide the progress dialog
                                        pd.dismiss();
                                        Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Login failed. Please try again", Snackbar.LENGTH_LONG);
                                        snackbar.show();
                                    }
                                }
                            });
                        }
                    }
                }).start(); // Start the operation
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Code here executes on main thread after user presses button
                Intent i = new Intent(getApplicationContext(), MobileSignupActivity.class);
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

    public void loadingSpinner() {
        // Initialize a new instance of progress dialog
        final ProgressDialog pd = new ProgressDialog(IndexActivity.this);

        // Set progress dialog style spinner
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        // Set the progress dialog title and message
/*
                pd.setTitle("Title of progress dialog.");
*/
        pd.setMessage("Loading.........");

        // Set the progress dialog background color
        //pd.getWindow().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFD4D9D0")));

        pd.setIndeterminate(false);

        // Finally, show the progress dialog
        pd.show();

        // Set the progress status zero on each button click
        progressStatus = 0;

        // Start the lengthy operation in a background thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (progressStatus < 100) {
                    // Update the progress status
                    progressStatus += 1;

                    // Try to sleep the thread for 20 milliseconds
                    try {
                        Thread.sleep(20);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    // Update the progress bar
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            // Update the progress status
                            pd.setProgress(progressStatus);
                            // If task execution completed
                            if (progressStatus == 100) {
                                // Dismiss/hide the progress dialog
                                pd.dismiss();
                                Snackbar snackbar = Snackbar.make(findViewById(android.R.id.content), "Login failed. Please try again", Snackbar.LENGTH_LONG);
                                snackbar.show();
                            }
                        }
                    });
                }
            }
        }).start(); // Start the operation

    }
}