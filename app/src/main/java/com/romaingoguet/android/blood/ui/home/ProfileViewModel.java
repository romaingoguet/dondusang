package com.romaingoguet.android.blood.ui.home;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.romaingoguet.android.blood.data.local.don.Don;
import com.romaingoguet.android.blood.data.repo.DonRepository;

import java.util.List;

public class ProfileViewModel extends AndroidViewModel {

    private DonRepository donRepository;
    private MutableLiveData<String[]> nextDons = new MutableLiveData<>();

    public ProfileViewModel(@NonNull Application application) {
        super(application);
        donRepository = new DonRepository(application);
    }

    public LiveData<List<Don>> getLastDonDone() {
        return donRepository.getLastDonDone();
    }

    public MutableLiveData<String[]> getNextDons() {
        return nextDons;
    }
}
