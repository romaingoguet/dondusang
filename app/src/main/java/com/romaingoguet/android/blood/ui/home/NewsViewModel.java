package com.romaingoguet.android.blood.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.romaingoguet.android.blood.data.models.News;
import com.romaingoguet.android.blood.data.models.ResponseWrapper;
import com.romaingoguet.android.blood.data.repo.NewsRepository;

import java.util.List;

public class NewsViewModel extends ViewModel {

    private LiveData<ResponseWrapper<List<News>>> news = new MutableLiveData<>();
    private NewsRepository newsRepository;

    public NewsViewModel() {
        newsRepository = new NewsRepository();
    }

    public LiveData<ResponseWrapper<List<News>>> getNews() {
        return newsRepository.getNews();
    }
}
