package com.romaingoguet.android.blood.data.local.don;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.romaingoguet.android.blood.ui.don.DonViewModel;

import java.util.List;

@Dao
public interface DonDao {

    @Insert
    void insert(Don don);

    @Update
    void update(Don don);

    @Delete
    void delete(Don don);

    @Query("SELECT * from don_table ORDER BY date DESC")
    LiveData<List<Don>> getAllDons();

    @Query("SELECT * from don_table ORDER BY DATE DESC LIMIT 1")
    LiveData<Don> getLastDon();

    @Query("SELECT * FROM don_table where TYPE = 'Sang' AND DATE = (SELECT MAX(DATE) FROM don_table WHERE TYPE = 'Sang') " +
            "UNION ALL SELECT * FROM don_table where TYPE = 'Plasma' AND DATE = (SELECT MAX(DATE) FROM don_table WHERE TYPE = 'Plasma') " +
            "UNION ALL SELECT * FROM don_table where TYPE = 'Plaquettes'  AND DATE = (SELECT MAX(DATE) FROM don_table WHERE TYPE = 'Plaquettes')")
    LiveData<List<Don>> getLastDonDone();
}
