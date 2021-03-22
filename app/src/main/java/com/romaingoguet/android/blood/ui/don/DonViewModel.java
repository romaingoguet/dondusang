package com.romaingoguet.android.blood.ui.don;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.annotation.NonNull;

import android.util.Log;

import com.romaingoguet.android.blood.data.repo.DonRepository;
import com.romaingoguet.android.blood.data.local.don.Don;
import com.romaingoguet.android.blood.utils.DonUtils;
import com.romaingoguet.android.blood.utils.Utils;

import java.util.Calendar;
import java.util.List;

public class DonViewModel extends AndroidViewModel {

    private DonRepository repository;

    private MutableLiveData<String> date = new MutableLiveData<>();
    private MutableLiveData<String> name = new MutableLiveData<>();
    private MutableLiveData<String> type = new MutableLiveData<>();
    private MutableLiveData<Don> DetailledDon = new MutableLiveData<>();
    private MutableLiveData<Boolean> isChanged = new MutableLiveData<>();
    private MutableLiveData<Boolean> closeDialog = new MutableLiveData<>();
    private MutableLiveData<String> toastMessage = new MutableLiveData<>();
    private MutableLiveData<String> addDonTitle = new MutableLiveData<>();
    private MutableLiveData<Boolean> isNewDon = new MutableLiveData<>();
    private MutableLiveData<Boolean> error_name = new MutableLiveData<>();
    private MutableLiveData<Boolean> error_type = new MutableLiveData<>();
    private MutableLiveData<Boolean> error_date = new MutableLiveData<>();
    private MutableLiveData<String> error_message_name = new MutableLiveData<>();
    private MutableLiveData<String> error_message_date = new MutableLiveData<>();


    public DonViewModel(@NonNull Application application) {
        super(application);
        repository = new DonRepository(application);
        this.isChanged.setValue(false);
        error_name.setValue(false);
        error_type.setValue(false);
        error_date.setValue(false);
    }

    public void insert(Don don) {
        repository.insert(don);
    }

    public void update(Don don) {
        repository.update(don);
    }

    public void delete(Don don) {
        repository.delete(don);
    }

    public LiveData<List<Don>> getAllDons() {
        return repository.getAllDons();
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
        Log.d("lol", "openEmptyAddDialog: ");
        changeDateAsToday();
        setDate("12/12/2018");
        setName("flklm");
        setType("Sang");
    }

    public void newDon(Don don) {
        setDetailledDon(don);
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

    public MutableLiveData<Don> getDetailledDon() {
        return DetailledDon;
    }

    public void setDetailledDon(Don detailledDon) {
        this.DetailledDon.setValue(detailledDon);
    }

    public void saveDon() {
        if (getDate().getValue() == null || getDate().getValue().equals("")) {
            setError_message_date("La date ne doit pas être vide");
            setError_date(true);
        } else {
            setError_message_date(null);
            setError_date(false);
        }
        if (getName().getValue() == null || getName().getValue().equals("")) {
            setError_message_name("Le nom ne doit pas être nul");
            setError_name(true);
        } else {
            setError_message_name(null);
            setError_name(false);
        }
        if (getType().getValue() == null || getType().getValue().equals("")) {
            setError_type(true);
        } else {
            setError_type(false);
        }
        if (this.error_date.getValue() || this.error_name.getValue() || this.error_type.getValue()) {
            // afficher toast message
        } else {
            Don don = new Don(Utils.stringDateToTs(getDate().getValue()), getName().getValue(), getType().getValue());
            insert(don);
            setToastMessage("Don enregistré");
            setCloseDialog(true);
        }
    }

    public MutableLiveData<Boolean> getCloseDialog() {
        return closeDialog;
    }

    public void setCloseDialog(Boolean closeDialog) {
        this.closeDialog.setValue(closeDialog);
    }

    public MutableLiveData<String> getToastMessage() {
        return toastMessage;
    }

    public void setToastMessage(String toastMessage) {
        this.toastMessage.setValue(toastMessage);
    }

    public void setPlaquetteType() {
        setType(DonUtils.PLAQUETTES);
    }

    public void setPlasmaType() {
        setType(DonUtils.PLASMA);
    }

    public void setSangType() {
        setType(DonUtils.SANG);
    }

    public MutableLiveData<String> getAddDonTitle() {
        return addDonTitle;
    }

    public void setAddDonTitle(String addDonTitle) {
        this.addDonTitle.setValue(addDonTitle);
    }

    public MutableLiveData<Boolean> getIsNewDon() {
        return isNewDon;
    }

    public void setIsNewDon(Boolean isNewDon) {
        this.isNewDon.setValue(isNewDon);
    }

    public MutableLiveData<Boolean> getError_name() {
        return error_name;
    }

    public void setError_name(Boolean error_name) {
        this.error_name.setValue(error_name);
    }

    public MutableLiveData<Boolean> getError_type() {
        return error_type;
    }

    public void setError_type(Boolean error_type) {
        this.error_type.setValue(error_type);
    }

    public MutableLiveData<Boolean> getError_date() {
        return error_date;
    }

    public void setError_date(Boolean error_date) {
        this.error_date.setValue(error_date);
    }

    public MutableLiveData<String> getError_message_name() {
        return error_message_name;
    }

    public void setError_message_name(String error_message_name) {
        this.error_message_name.setValue(error_message_name);
    }

    public MutableLiveData<String> getError_message_date() {
        return error_message_date;
    }

    public void setError_message_date(String error_message_date) {
        this.error_message_date.setValue(error_message_date);
    }
}
