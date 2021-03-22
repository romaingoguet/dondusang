package com.romaingoguet.android.blood.data.local.favorite;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "favorite_table")
public class Favorite {

    @PrimaryKey(autoGenerate = true)
    public int id;

    public String gr_code;

    public String name;

    public String lat;

    public String lon;


    public Favorite(String gr_code, String name, String lat, String lon) {
        this.gr_code = gr_code;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getGr_code() {
        return gr_code;
    }

    public String getName() {
        return name;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    @NonNull
    @Override
    public String toString() {
        return gr_code + " " + name + " " + lat + " " + lon;
    }

    public boolean equal(Object object2) {
        return object2 instanceof Favorite && gr_code.equals(((Favorite) object2).gr_code) && name.equals(((Favorite) object2).name) && lat.equals(((Favorite) object2).lat) && lon.equals(((Favorite) object2).lon);
    }
}
