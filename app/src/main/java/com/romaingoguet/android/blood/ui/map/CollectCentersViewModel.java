package com.romaingoguet.android.blood.ui.map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.romaingoguet.android.blood.data.models.Post;
import com.romaingoguet.android.blood.data.models.ResponseWrapper;
import com.romaingoguet.android.blood.data.models.Result;
import com.romaingoguet.android.blood.data.repo.CollectCentersRepository;

import java.util.List;

public class CollectCentersViewModel extends ViewModel {

    private CollectCentersRepository collectCentersRepository;
    private LiveData<ResponseWrapper<Post>> centers;
    private MutableLiveData<Result> center = new MutableLiveData<>();


    public CollectCentersViewModel() {
        collectCentersRepository = new CollectCentersRepository();
    }

    public LiveData<ResponseWrapper<Post>> getallCenters(LatLng x, LatLng y) {
        centers = collectCentersRepository.getCenters(x, y);
        return centers;
    }

    public ResponseWrapper<Post> getCenters() {
        if (centers != null) {
            return centers.getValue();
        } else {
            return null;
        }
    }

    public LiveData<Result> getCenter() {
        return center;
    }

    public void setCenter(Result result) {
        this.center.setValue(result);
        Log.d("lol", "setCenter: ");
    }
}
