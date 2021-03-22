package com.romaingoguet.android.blood.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MenuActivityViewModel extends ViewModel {

    private MutableLiveData<Boolean> isLocationPermissionGranted = new MutableLiveData<>();

    public MutableLiveData<Boolean> getIsLocationPermissionGranted() {
        return isLocationPermissionGranted;
    }

    public void setIsLocationPermissionGranted(Boolean isLocationPermissionGranted) {
        this.isLocationPermissionGranted.setValue(isLocationPermissionGranted);
    }
}
