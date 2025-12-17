package com.jkr.test7;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("/api/register")
    Call<ResponseBody> registerUser(@Body UserModel user);

    @POST("/api/login")
    Call<ResponseBody> loginUser(@Body UserModel user);
}
