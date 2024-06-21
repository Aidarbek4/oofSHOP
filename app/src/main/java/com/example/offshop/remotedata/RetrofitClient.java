package com.example.offshop.remotedata;

import com.example.offshop.constants.ConstantAPI;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static RetrofitClient instance = null;
    private Api api;

    private RetrofitClient() {
        RetrofitBuilder();
    }

    private void RetrofitBuilder() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(new OkHttpClient.Builder()
                        .connectTimeout(15, TimeUnit.SECONDS)
                        .readTimeout(15, TimeUnit.SECONDS)
                        .writeTimeout(15, TimeUnit.SECONDS)
                        .build())
                .build();

        api = retrofit.create(Api.class);
    }

    public static synchronized RetrofitClient getInstance(){
        if(instance == null){
            instance = new RetrofitClient();
        }
        return instance;
    }

    public Api getApi(){
        return api;
    }
}
