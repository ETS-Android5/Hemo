package com.dev334.blood.Retrofit;

import com.dev334.blood.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitAPI {
    @Headers("auth_token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MWI3YTNkOGViMjYxYjdjMjJkNGYzNjAiLCJpYXQiOjE2Mzk0MjUxMDF9.iRKfrKeuH26HFyqVHnPSmLgQlFH2KAbSTFm5a2yP4g8")
    @GET("/api/data/users")
    Call<List<User>> getUsers();


    @POST("/api/user/register")
    Call<User> registerUser(@Body User user);

    @POST("/api/user/login")
    Call<User> loginUser(@Body User user);

}
