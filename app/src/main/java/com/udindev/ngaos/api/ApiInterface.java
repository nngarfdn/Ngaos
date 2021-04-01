package com.udindev.ngaos.api;


import com.udindev.ngaos.model.ResponsePrayerTime;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {
    //get Time
    @GET("day.json")
    Call<ResponsePrayerTime> getTimeFromFatimah(
            @Query("latitude") String latitude,
            @Query("longitude") String longitude,
            @Query("date") String date
    );
}
