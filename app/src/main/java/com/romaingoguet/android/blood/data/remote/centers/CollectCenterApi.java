package com.romaingoguet.android.blood.data.remote.centers;

import com.romaingoguet.android.blood.data.models.Post;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CollectCenterApi {

    @GET("get-collects-ajax")
    Call<Post> getPosts(@Query("neLon") double neLon, @Query("neLat") double neLat, @Query("swLon") double swLon, @Query("swLat") double swLat);

}
