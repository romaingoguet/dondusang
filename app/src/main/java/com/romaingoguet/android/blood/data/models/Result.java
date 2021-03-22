package com.romaingoguet.android.blood.data.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Result implements Parcelable {

    private String name;
    private String date;
    private String start;
    private String end;
    private String tram;
    private String bus;
    private String metro;
    private String adresse;
    private String ville;
    private String lat;
    private String lon;
    private String icon;
    private String gr_code;
    private boolean sang;
    private boolean plaquettes;
    private boolean plasma;
    private String tel;
    private String distance;
    private String message;
    private String email;

    public Result(String name, String date, String start, String end, String tram, String bus, String metro, String adresse, String ville, String lat, String lon, String icon, String gr_code, boolean sang, boolean plaquettes, boolean plasma, String tel, String distance, String message, String email) {
        this.name = name;
        this.date = date;
        this.start = start;
        this.end = end;
        this.tram = tram;
        this.bus = bus;
        this.metro = metro;
        this.adresse = adresse;
        this.ville = ville;
        this.lat = lat;
        this.lon = lon;
        this.icon = icon;
        this.gr_code = gr_code;
        this.sang = sang;
        this.plaquettes = plaquettes;
        this.plasma = plasma;
        this.tel = tel;
        this.distance = distance;
        this.message = message;
        this.email = email;
    }

    protected Result(Parcel in) {
        name = in.readString();
        date = in.readString();
        start = in.readString();
        end = in.readString();
        tram = in.readString();
        bus = in.readString();
        metro = in.readString();
        adresse = in.readString();
        ville = in.readString();
        lat = in.readString();
        lon = in.readString();
        icon = in.readString();
        gr_code = in.readString();
        sang = in.readByte() != 0;
        plaquettes = in.readByte() != 0;
        plasma = in.readByte() != 0;
        tel = in.readString();
        distance = in.readString();
        message = in.readString();
        email = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(date);
        dest.writeString(start);
        dest.writeString(end);
        dest.writeString(tram);
        dest.writeString(bus);
        dest.writeString(metro);
        dest.writeString(adresse);
        dest.writeString(ville);
        dest.writeString(lat);
        dest.writeString(lon);
        dest.writeString(icon);
        dest.writeString(gr_code);
        dest.writeByte((byte) (sang ? 1 : 0));
        dest.writeByte((byte) (plaquettes ? 1 : 0));
        dest.writeByte((byte) (plasma ? 1 : 0));
        dest.writeString(tel);
        dest.writeString(distance);
        dest.writeString(message);
        dest.writeString(email);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        @Override
        public Result createFromParcel(Parcel in) {
            return new Result(in);
        }

        @Override
        public Result[] newArray(int size) {
            return new Result[size];
        }
    };

    public String getName() {
        return name;
    }

    public String getDate() {
        return date;
    }

    public String getStart() {
        return start;
    }

    public String getEnd() {
        return end;
    }

    public String getTram() {
        return tram;
    }

    public String getBus() {
        return bus;
    }

    public String getMetro() {
        return metro;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getVille() {
        return ville.toUpperCase();
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getGr_code() {
        return gr_code;
    }

    public String getIcon() {
        return icon;
    }

    public boolean isSang() {
        return sang;
    }

    public boolean isPlaquettes() {
        return plaquettes;
    }

    public boolean isPlasma() {
        return plasma;
    }

    public String getTel() {
        return tel;
    }

    public String getDistance() {
        return distance;
    }

    public String getMessage() {
        return message;
    }

    public String getEmail() {
        return email;
    }
}
