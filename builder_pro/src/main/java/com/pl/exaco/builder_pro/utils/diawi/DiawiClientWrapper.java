package com.pl.exaco.builder_pro.utils.diawi;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DiawiClientWrapper {

    private static final String BASE_URL = "https://upload.diawi.com";

    public static DiawiApiService getRetrofitDiawiBuilder(){
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(chain -> {
            Request request = chain.request().newBuilder().addHeader("Content-Type", "multipart/form-data").build();
            return chain.proceed(request);
        });

        Retrofit retrofitBuilder = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();

        return retrofitBuilder.create(DiawiApiService.class);
    }
}
