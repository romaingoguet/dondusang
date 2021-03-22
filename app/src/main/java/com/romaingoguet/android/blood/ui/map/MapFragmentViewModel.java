package com.romaingoguet.android.blood.ui.map;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class MapFragmentViewModel extends AndroidViewModel {

    private MutableLiveData<Boolean> isCenterDetailsOpened = new MutableLiveData<>();
    private MutableLiveData<Boolean> isMapVisible = new MutableLiveData<>();
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public MapFragmentViewModel(Application application) {
        super(application);
        this.isCenterDetailsOpened.setValue(false);
        this.isMapVisible.setValue(true);
    }

    public LiveData<Boolean> getIsMapVisible() {
        return isMapVisible;
    }

    public LiveData<Boolean> getIsCenterDetailsOpened() {
        return isCenterDetailsOpened;
    }

    public void setIsMapVisible(Boolean isMapVisible) {
        this.isMapVisible.setValue(isMapVisible);
    }

    public void setIsCenterDetailsOpened(Boolean isCenterDetailsOpened) {
        this.isCenterDetailsOpened.setValue(isCenterDetailsOpened);
    }

    public void changeView() {
        if (isMapVisible.getValue()) {
            isMapVisible.setValue(false);
        } else {
            isMapVisible.setValue(true);
        }
    }

    public MutableLiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void setIsLoading(Boolean isLoading) {
        Log.d("lol", "setIsLoading: " + isLoading);
        this.isLoading.setValue(isLoading);
    }
}
