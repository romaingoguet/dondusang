package com.romaingoguet.android.blood.data.repo;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import com.romaingoguet.android.blood.data.local.favorite.Favorite;
import com.romaingoguet.android.blood.data.local.favorite.FavoriteDao;
import com.romaingoguet.android.blood.data.local.favorite.FavoriteDatabase;

import java.util.List;

import androidx.lifecycle.LiveData;

public class FavoriteRepository {
    private FavoriteDao favoriteDao;
    private LiveData<List<Favorite>> allFavorites;
    private SingleLiveEvent<List<Favorite>> allFavoritesOnce;

    public FavoriteRepository(Application application) {
        FavoriteDatabase favoriteDatabase = FavoriteDatabase.getInstance(application);
        favoriteDao = favoriteDatabase.favoriteDao();
        allFavorites = favoriteDao.getAllFavorites();
    }

    public void insert(Favorite favorite) {
        new InsertFavoriteAsyncTask(favoriteDao).execute(favorite);
    }

    public void update(Favorite favorite) {
        new UpdateDonAsyncTask(favoriteDao).execute(favorite);
    }

    public void delete(Favorite favorite) {
        Log.d("lol", "repo delete: ");
        new DeleteFavoriteAsyncTask(favoriteDao).execute(favorite);
    }

    public LiveData<List<Favorite>> getAllFavorites() {
        return allFavorites;
    }

    public SingleLiveEvent<List<Favorite>> getAllFavoritesOnce() {
        return allFavoritesOnce;
    }

    public void setAllFavoritesOnce(SingleLiveEvent<List<Favorite>> allFavoritesOnce) {
        this.allFavoritesOnce = allFavoritesOnce;
    }


    private static class InsertFavoriteAsyncTask extends AsyncTask<Favorite, Void, Void> {
        private FavoriteDao favoriteDao;

        private InsertFavoriteAsyncTask(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            favoriteDao.insert(favorites[0]);
            return null;
        }
    }

    private static class UpdateDonAsyncTask extends AsyncTask<Favorite, Void, Void> {
        private FavoriteDao favoriteDao;

        private UpdateDonAsyncTask(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            favoriteDao.update(favorites[0]);
            return null;
        }
    }

    private static class DeleteFavoriteAsyncTask extends AsyncTask<Favorite, Void, Void> {
        private FavoriteDao favoriteDao;

        private DeleteFavoriteAsyncTask(FavoriteDao favoriteDao) {
            this.favoriteDao = favoriteDao;
        }

        @Override
        protected Void doInBackground(Favorite... favorites) {
            Log.d("lol", "doInBackground: " + favorites[0].toString());
            int a = favoriteDao.deleteFavorite(favorites[0].gr_code, favorites[0].name);
            Log.d("lol", "doInBackground: " + a);
            return null;
        }
    }
}
