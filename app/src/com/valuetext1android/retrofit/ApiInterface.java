package com.valuetext1android.retrofit;

import com.valuetext1android.Model.SingleMessage;
import com.valuetext1android.utils.Constants;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

/**
 * Created by JANI SHAIK on 14/01/2020
 */

public interface ApiInterface {

    @Headers({
            "apiKey:BNwv7qzI4",
            "secretKey:yAoqWy53"
    })
    @GET("rest")
    Call<SingleMessage> sendMessage(@Query("mobileNumber") String mobileNumber, @Query("sms") String sms, @Query("senderId") String senderId,
                                    @Query("clientId") String clientId);
}
