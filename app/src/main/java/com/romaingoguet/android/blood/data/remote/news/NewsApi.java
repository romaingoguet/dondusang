package com.romaingoguet.android.blood.data.remote.news;

import com.romaingoguet.android.blood.data.models.News;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;


public interface NewsApi {


    @GET("/Actus.json")
    Call<List<News>> getNews();
}
