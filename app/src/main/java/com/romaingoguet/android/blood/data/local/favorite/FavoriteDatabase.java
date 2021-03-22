package com.romaingoguet.android.blood.data.local.favorite;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.romaingoguet.android.blood.data.local.don.DonDao;

@Database(entities = {Favorite.class}, version = 1, exportSchema = false)
public abstract class FavoriteDatabase extends RoomDatabase {

    private static FavoriteDatabase instance;

    public abstract FavoriteDao favoriteDao();

    public static synchronized FavoriteDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    FavoriteDatabase.class, "favorite_database")
                    .fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build();
        }
        return instance;
    }

    private static Callback roomCallback = new Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private FavoriteDao favoriteDao;

        private PopulateDbAsyncTask(FavoriteDatabase db) {
            favoriteDao = db.favoriteDao();
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
