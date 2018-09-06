package com.practice.aravind.wahter;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.practice.aravind.wahter.api.APIClient;
import com.practice.aravind.wahter.api.APIInterface;
import com.practice.aravind.wahter.documents.Response;
import com.practice.aravind.wahter.documents.Users;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Converter;

public class DeliveryInfoActivity extends AppCompatActivity {


    private APIInterface apiInterface;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        final Bundle signUpBundle = getIntent().getExtras();
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
                Users user = new Users();
                String phoneNumber = signUpBundle.getString("userPhoneNumber");
                String altNumber = signUpBundle.getString("userAltNumber");
                String password = signUpBundle.getString("companyType");
                String orgName = signUpBundle.getString("companyName");
                String emailId = signUpBundle.getString("userEmail");
                user.setPhoneNumber(phoneNumber);
                user.setPassword(password);
                user.setOrganizationName(orgName);
                user.setEmailId(emailId);

                apiInterface = APIClient.getClient().create(APIInterface.class);

                Call<Response> authenticateService = apiInterface.createpotentialuser(user);

                authenticateService.enqueue(new Callback<Response>() {
                    @Override
                    public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                        if (response.isSuccessful()) {
                            Response serviceResponse = response.body();
                            String textReceived = serviceResponse.getMessage();
                            Toast.makeText(getApplicationContext(), textReceived, Toast.LENGTH_LONG).show();
                        } else {
                            Converter<ResponseBody, Response> converter
                                    = APIClient.getClient().responseBodyConverter(Response.class, (Annotation[]) new Annotation[0]);
                            Response errorResponse = null;
                            try {
                                errorResponse = converter.convert(response.errorBody());
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }

                    }

                    @Override
                    public void onFailure(Call<Response> call, Throwable t) {
                        //Logging goes here
                        Toast.makeText(getApplicationContext(), "Unable to connect to server", Toast.LENGTH_LONG).show();
                        t.printStackTrace();
                    }
                });
            }
        });
    }
}
