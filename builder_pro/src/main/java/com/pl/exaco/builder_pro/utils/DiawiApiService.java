package com.pl.exaco.builder_pro.utils;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.*;

public interface DiawiApiService {

    @Multipart
    @POST("/")
    Call<UploadResponse> uploadFile(
            @Part("token") RequestBody token,
            @Part MultipartBody.Part file);

    @GET("/status")
    Call<StatusResponse> checkStatus(
            @Query("token") String token,
            @Query("job") String job
    );
}
