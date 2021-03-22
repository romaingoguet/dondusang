package com.romaingoguet.android.blood.ui.center;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.romaingoguet.android.blood.data.local.favorite.Favorite;
import com.romaingoguet.android.blood.data.repo.FavoriteRepository;

import java.util.List;

public class CenterViewModel extends AndroidViewModel {

    FavoriteRepository favoriteRepository;

    public CenterViewModel(@NonNull Application application) {
        super(application);
        favoriteRepository = new FavoriteRepository(application);
    }

    public void insert(Favorite favorite) {
        favoriteRepository.insert(favorite);
    }

    public void update(Favorite favorite) {
        favoriteRepository.update(favorite);
    }

    public void delete(Favorite favorite) {
        Log.d("lol", "view model delete: ");
        favoriteRepository.delete(favorite);
    }

    public LiveData<List<Favorite>> getAllFavorites() {
        return favoriteRepository.getAllFavorites();
    }
}
