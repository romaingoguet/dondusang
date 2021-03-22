package com.romaingoguet.android.blood.data.models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.romaingoguet.android.blood.utils.Utils;

public class Post {

    @SerializedName("results")
    @Expose
    private List<Result> results;
    @SerializedName("num_results")
    @Expose
    private Integer numResults;

    public List<Result> getResults() {
        return results;
    }

    public Integer getNumResults() {
        return numResults;
    }

    public class Quand {

        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("day")
        @Expose
        private String day;
        @SerializedName("begin")
        @Expose
        private String begin;
        @SerializedName("end")
        @Expose
        private String end;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public String getBegin() {
            return begin;
        }

        public void setBegin(String begin) {
            this.begin = begin;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }


        public String quandToString() {
            return date + " de " + begin + " à " + end;
        }

    }

    public class LpCom {

        @SerializedName("#markup")
        @Expose
        private String markup;

        public String getMarkup() {
            return Utils.clean(markup);
        }
    }



}