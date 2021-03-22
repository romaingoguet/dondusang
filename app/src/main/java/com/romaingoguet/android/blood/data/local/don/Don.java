package com.romaingoguet.android.blood.data.local.don;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "don_table")
public class Don {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private long date;

    private String place;

    private String type;

//    private String add_date;


    public Don(long date, String place, String type) {
        this.date = date;
        this.place = place;
        this.type = type;
        // TODO: set le add_date à ajd
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public long getDate() {
        return date;
    }

    public String getPlace() {
        return place;
    }

    public String getType() {
        return type;
    }

//    public String getAdd_date() {
//        return add_date;
//    }

    @Override
    public String toString() {
        return id + " " + date + " " + place + " " + type;
    }
}
