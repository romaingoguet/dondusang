package com.romaingoguet.android.blood.data.local.don;

import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import android.content.Context;
import android.os.AsyncTask;
import androidx.annotation.NonNull;

@Database(entities = {Don.class}, version = 1, exportSchema = false)
public abstract class DonDatabase extends RoomDatabase {

    private static DonDatabase instance;

    public abstract DonDao donDao();

    public static synchronized DonDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    DonDatabase.class, "don_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private DonDao donDao;

        private PopulateDbAsyncTask(DonDatabase db) {
            donDao = db.donDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            // pre-filled the Db
//            favoriteDao.insert(new Don(1553890883, "Ici", "Sang"));
//            favoriteDao.insert(new Don(1550000000, "La", "Plasma"));
//            favoriteDao.insert(new Don(1545689433, "Pas la", "Plaquettes"));
            return null;
        }
    }


}
