package com.practice.aravind.wahter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.practice.aravind.wahter.api.APIClient;
import com.practice.aravind.wahter.api.APIInterface;
import com.practice.aravind.wahter.documents.Response;
import com.practice.aravind.wahter.util.WahterConstants;
import com.practice.aravind.wahter.util.WahterUtility;

import retrofit2.Call;
import retrofit2.Callback;

public class ForgotPasswordMobileActivity extends Activity {

    private EditText phoneNumberTxt;
    private APIInterface apiInterface = APIClient.getClient().create(APIInterface.class);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password_mobile);
    }

    public void onForgotPasswordNext(View v) {

        phoneNumberTxt = findViewById(R.id.phoneNumberTxt);
        final String phoneNumber = phoneNumberTxt.getText().toString().trim();

        if (!WahterUtility.isValidPhone(phoneNumber)) {
            phoneNumberTxt.setError(WahterConstants.INVALID_PHONE_NUMBER);
            phoneNumberTxt.requestFocus();
            return;
        }

        phoneNumberTxt.setEnabled(false);
        Call<Response> validateService = apiInterface.validatePhoneNumber(phoneNumber);
        validateService.enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    processResponse(response.body());
                } else {
                    Intent indexActivity = new Intent(ForgotPasswordMobileActivity.this, OTPVerification.class);
                    indexActivity.putExtra(WahterConstants.PHONE_NUMBER, phoneNumber);
                    indexActivity.putExtra(WahterConstants.NEXT_ACTIVITY, WahterConstants.FORGOT_PASSWORD_ACTIVITY);
                    startActivity(indexActivity);
                }
            }

            private void processResponse(Response serviceResponse) {
                String textReceived = serviceResponse.getMessage();
                WahterUtility.showToast(getApplicationContext(),textReceived);
                phoneNumberTxt.setEnabled(true);
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                WahterUtility.showToast(getApplicationContext(),WahterConstants.CONNECTION_ERROR);
                phoneNumberTxt.setEnabled(true);
                t.printStackTrace();
            }
        });
    }

    //todo //FirebaseAuth.getInstance().signOut();

    //todo logout from Firebase
    /*@Override
    protected void onStart() {
        super.onStart();

        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            Intent intent = new Intent(this, ProfileActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
        }*/

}
