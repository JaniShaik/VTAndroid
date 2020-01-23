package com.valuetext1android.retrofit;

import android.content.Context;

import com.valuetext1android.networkInterceptor.ConnectivityInterceptor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by JANI SHAIK on 14/01/2020
 */

public class ApiClient {

    private static Retrofit retrofit;

    public static ApiInterface getApiClient(Context mContext, String url) {

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new ConnectivityInterceptor(mContext))
                .build();

        retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(ApiInterface.class);
    }

}
