package com.practice.aravind.wahter.util;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.Snackbar;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
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

    public static void showSnackBar(ConstraintLayout constraintLayout, String messageText, String actionText, View.OnClickListener listener) {
        Snackbar snackbar = Snackbar.make(constraintLayout, messageText, Snackbar.LENGTH_LONG);
        if(!actionText.isEmpty()) {
            snackbar.setAction(actionText, listener);
            snackbar.setActionTextColor(Color.RED);
        }
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    public static Class getClass(String className) {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
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

    public static boolean isValidOTP(String otp) {
        if (!otp.isEmpty() && otp.length() == 6)
            return true;
        return false;
    }

    public static boolean isValidPassword(String pass) {
        if (!pass.isEmpty() && pass.length() >= 6)
            return true;
        return false;
    }

    public static  boolean checkforSame(String first, String second) {
        if (first.equalsIgnoreCase(second))
            return true;
        return false;
    }


}
