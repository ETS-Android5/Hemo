package com.dev334.blood.util.retrofit;

import com.dev334.blood.model.ApiResponse;
import com.dev334.blood.model.Blood;
import com.dev334.blood.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    //@Headers("auth_token: eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MWI3YTNkOGViMjYxYjdjMjJkNGYzNjAiLCJpYXQiOjE2Mzk0MjUxMDF9.iRKfrKeuH26HFyqVHnPSmLgQlFH2KAbSTFm5a2yP4g8")
    @GET("/api/data/users")
    Call<List<User>> getUsers();


    @POST("/api/user/register")
    Call<ApiResponse> registerUser(@Body User user);

    @POST("/api/user/login")
    Call<User> loginUser(@Body User user);

    @POST("/api/user/create")
    Call<ApiResponse> createUser(@Body User user);

    @GET("api/user/users/{id}")
    Call<User> getUser(@Path("id") String id);

    @GET("api/blood/req")
    Call<List<Blood>> getBloodReq(@Query("location") String location, @Query("blood") String blood);

    @POST("/api/blood/req")
    Call<ApiResponse> reqBlood(@Body Blood blood);
 }
