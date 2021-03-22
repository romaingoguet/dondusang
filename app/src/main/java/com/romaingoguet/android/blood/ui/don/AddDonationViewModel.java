package com.romaingoguet.android.blood.ui.don;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import com.romaingoguet.android.blood.data.local.don.Don;
import com.romaingoguet.android.blood.utils.DonUtils;
import com.romaingoguet.android.blood.utils.Utils;

import java.util.Calendar;
import java.util.Objects;

public class AddDonationViewModel extends AndroidViewModel {

    private MutableLiveData<String> date = new MutableLiveData<>();
    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<String> type = new MutableLiveData<>();
    private MutableLiveData<Don> DetailledDon = new MutableLiveData<>();
    private MutableLiveData<Boolean> isChanged = new MutableLiveData<>();


    public AddDonationViewModel(@NonNull Application application) {
        super(application);
        this.isChanged.setValue(false);
    }


    public MutableLiveData<String> getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date.setValue(date);
    }

    public MutableLiveData<Boolean> getIsChanged() {
        return isChanged;
    }

    public void setIsChanged(MutableLiveData<Boolean> isChanged) {
        this.isChanged = isChanged;
    }

    public void changeDate() {
        if (!isChanged.getValue()) {
            isChanged.setValue(true);
        }
    }

    public void changeDateAsToday() {
        Calendar now = Calendar.getInstance();
        this.date.setValue(Utils.calendarDateToString(now));
    }

    public void openEmptyAddDialog() {
        changeDateAsToday();
        setName("");
        setType("Sang");
    }

    public boolean isSang() {
        return Objects.equals(getType().getValue(), DonUtils.SANG);
    }

    public boolean isPlaquettes() {
        return Objects.equals(getType().getValue(), DonUtils.PLAQUETTES);
    }

    public boolean isPlasma() {
        return Objects.equals(getType().getValue(), DonUtils.PLASMA);
    }

    public MutableLiveData<String> getName() {
        return name;
    }

    public void setName(String name) {
        this.name.setValue(name);
    }

    public MutableLiveData<String> getType() {
        return type;
    }

    public void setType(String type) {
        this.type.setValue(type);
    }

    public void openNewDon(Don don) {
        DetailledDon.setValue(don);
    }

    public MutableLiveData<Don> getDetailledDon() {
        return DetailledDon;
    }

    public void setDetailledDon(MutableLiveData<Don> detailledDon) {
        DetailledDon = detailledDon;
    }
}
