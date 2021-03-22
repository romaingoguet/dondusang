package com.romaingoguet.android.blood.data.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.romaingoguet.android.blood.data.models.News;
import com.romaingoguet.android.blood.data.models.ResponseWrapper;
import com.romaingoguet.android.blood.data.remote.news.NewsApi;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class NewsRepository {

    private NewsApi newsApi;
    private String TAG = "lol";

    /**
     * @return the news from EFS site
     */
    public LiveData<ResponseWrapper<List<News>>> getNews() {
        final MutableLiveData<ResponseWrapper<List<News>>> news = new MutableLiveData<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://donneurs.efs.sante.fr/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        newsApi = retrofit.create(NewsApi.class);

        retrofit2.Call<List<News>> call = newsApi.getNews();
        call.enqueue(new retrofit2.Callback<List<News>>() {

            @Override
            public void onResponse(Call<List<News>> call, Response<List<News>> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "onResponse: appel non succesfull" + response);
                    news.setValue(new ResponseWrapper<List<News>>(response));

                }
                //List<News> newslist = response.body();
                Log.d(TAG, "onResponse: ok news " + response);
                news.setValue(new ResponseWrapper<List<News>>(response));
            }

            @Override
            public void onFailure(Call<List<News>> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
                news.setValue(new ResponseWrapper<List<News>>(t));
            }
        });
        return news;


    }


}
