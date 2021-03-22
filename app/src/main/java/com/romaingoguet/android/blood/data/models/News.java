package com.romaingoguet.android.blood.data.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class News {

    @SerializedName("url")
    @Expose
    public String url;
    @SerializedName("tag")
    @Expose
    public String tag;
    @SerializedName("urlRoll")
    @Expose
    public String urlRoll;
    @SerializedName("link")
    @Expose
    public String link;

}
