package com.romaingoguet.android.blood.data.local.favorite;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert
    void insert(Favorite favorite);

    @Update
    void update(Favorite favorite);

    @Query("SELECT * from favorite_table")
    LiveData<List<Favorite>> getAllFavorites();

    @Query("DELETE FROM favorite_table WHERE gr_code= :gr_code AND name=:name")
    int deleteFavorite(String gr_code, String name);

}
