package com.practice.aravind.wahter;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;


interface APIInterface {

   /* @GET("/api/unknown")
    Call<MultipleResource> doGetListResources();*/

    /*@POST("/api/users")
    Call<User> createUser(@Body User user);*/

    @GET("/users/authenticateUser?")
    Call<Response> authenticateUser(@Query("phoneNumber") String phoneNumber, @Query("password") String password);

    /*@FormUrlEncoded
    @POST("/api/users?")
    Call<UserList> doCreateUserWithField(@Field("name") String name, @Field("job") String job);*/
}
