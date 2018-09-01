package com.practice.aravind.wahter;

import com.practice.aravind.wahter.documents.Users;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Query;


interface APIInterface {

   /* @GET("/api/unknown")
    Call<MultipleResource> doGetListResources();*/

    @PATCH("/users/updateuser")
    Call<Response> updateuser(@Body Users users);

    @GET("/users/authenticateUser?")
    Call<Response> authenticateUser(@Query("phoneNumber") String phoneNumber, @Query("password") String password);

    @GET("/users/validatePhoneNumber?")
    Call<Response> validatePhoneNumber(@Query("phoneNumber") String phoneNumber);

    /*@FormUrlEncoded
    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);*/
}
