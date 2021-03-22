package com.romaingoguet.android.blood.data.repo;

import android.app.Application;

import androidx.lifecycle.LiveData;

import android.os.AsyncTask;

import com.romaingoguet.android.blood.data.local.don.Don;
import com.romaingoguet.android.blood.data.local.don.DonDao;
import com.romaingoguet.android.blood.data.local.don.DonDatabase;

import java.util.List;

public class DonRepository {
    private DonDao donDao;

    public DonRepository(Application application) {
        DonDatabase database = DonDatabase.getInstance(application);
        donDao = database.donDao();
    }

    public void insert(Don don) {
        new InsertDonAsyncTask(donDao).execute(don);
    }

    public void update(Don don) {
        new UpdateDonAsyncTask(donDao).execute(don);
    }

    public void delete(Don don) {
        new DeleteDonAsyncTask(donDao).execute(don);
    }

    public LiveData<List<Don>> getAllDons() {
        return donDao.getAllDons();
    }

    public LiveData<Don> getLastDon() {
        return donDao.getLastDon();
    }

    public LiveData<List<Don>> getLastDonDone() {
        return donDao.getLastDonDone();
    }

    private static class InsertDonAsyncTask extends AsyncTask<Don, Void, Void> {
        private DonDao donDao;

        private InsertDonAsyncTask(DonDao donDao) {
            this.donDao = donDao;
        }

        @Override
        protected Void doInBackground(Don... dons) {
            donDao.insert(dons[0]);
            return null;
        }
    }

    private static class UpdateDonAsyncTask extends AsyncTask<Don, Void, Void> {
        private DonDao donDao;

        private UpdateDonAsyncTask(DonDao donDao) {
            this.donDao = donDao;
        }

        @Override
        protected Void doInBackground(Don... dons) {
            donDao.update(dons[0]);
            return null;
        }
    }

    private static class DeleteDonAsyncTask extends AsyncTask<Don, Void, Void> {
        private DonDao donDao;

        private DeleteDonAsyncTask(DonDao donDao) {
            this.donDao = donDao;
        }

        @Override
        protected Void doInBackground(Don... dons) {
            donDao.delete(dons[0]);
            return null;
        }
    }
}
