package com.practice.aravind.wahter.util;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

import com.practice.aravind.wahter.api.APIClient;
import com.practice.aravind.wahter.documents.Response;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class WahterUtility {

    private static final Converter<ResponseBody, Response> converter = APIClient.getClient().responseBodyConverter(Response.class, (Annotation[]) new Annotation[0]);


    public static void showToast(Context applicationContext, String textReceived) {
        Toast toast = Toast.makeText(applicationContext, textReceived, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER_HORIZONTAL, 0, 250);
        toast.show();
    }

    public static Response extractError(retrofit2.Response<Response> response) {
        Response errorResponse  = null;

        try {
            errorResponse = converter.convert(response.errorBody());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return errorResponse;
    }

    public static boolean isValidPhone(String phoneNumber) {
        if (!phoneNumber.isEmpty() && phoneNumber.length() == 10)
            return true;
        return false;
    }

    public static boolean isValidPassword(String pass) {
        if (!pass.isEmpty() && pass.length() >= 6)
            return true;
        return false;
    }


}
