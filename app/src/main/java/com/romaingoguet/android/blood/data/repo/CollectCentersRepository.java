package com.romaingoguet.android.blood.data.repo;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.romaingoguet.android.blood.data.models.Post;
import com.romaingoguet.android.blood.data.models.ResponseWrapper;
import com.romaingoguet.android.blood.data.models.Result;
import com.romaingoguet.android.blood.data.remote.centers.CollectCenterApi;
import com.romaingoguet.android.blood.utils.Utils;

import java.net.SocketTimeoutException;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CollectCentersRepository {

    private static String TAG = "lol";

    private static final String BASE_URL = "https://dondesang.efs.sante.fr/";

    public LiveData<ResponseWrapper<Post>> getCenters(LatLng topRight, LatLng bottomLeft) {
        final MutableLiveData<ResponseWrapper<Post>> centers = new MutableLiveData<>();


        GsonBuilder gsonBuilder = new GsonBuilder();
        JsonDeserializer<Result> jsonDeserializer = (json, typeOfT, context) -> {
            JsonObject jsonObject = json.getAsJsonObject();


            String date = (jsonObject.has("quand")) ? jsonObject.getAsJsonArray("quand").get(0).getAsJsonObject().get("date").getAsString() : Utils.CLIC_HERE;
            String start = (jsonObject.has("quand")) ? jsonObject.getAsJsonArray("quand").get(0).getAsJsonObject().get("begin").getAsString().trim() : null;
            String end = (jsonObject.has("quand")) ? jsonObject.getAsJsonArray("quand").get(0).getAsJsonObject().get("end").getAsString().trim() : null;
            String tram = jsonObject.get("tram").isJsonNull() ? null : jsonObject.get("tram").getAsString();
            String bus = jsonObject.get("bus").isJsonNull() ? null : jsonObject.get("bus").getAsString();
            String metro = jsonObject.get("metro").isJsonNull() ? null : jsonObject.get("metro").getAsString();
            String adresse = jsonObject.get("lp_ad2").isJsonNull() ? null : jsonObject.get("lp_ad2").getAsString();
            String ville = jsonObject.get("ville").isJsonNull() ? null : jsonObject.get("ville").getAsString();
            //String name = (jsonObject.has("lp_libconv")) ? jsonObject.get("lp_libconv").toString() : jsonObject.get("lp_ad1").getAsString();
            String name = "";
            if (jsonObject.has("lp_libconv")) {
                if (!jsonObject.get("lp_libconv").isJsonNull()) {
                    name = jsonObject.get("lp_libconv").getAsString();
                } else if (ville != null) {
                    name = ville;
                }
            } else {
                if (!jsonObject.get("lp_ad1").isJsonNull()) {
                    name = jsonObject.get("lp_ad1").getAsString();
                } else if (ville != null) {
                    name = ville;
                }
            }
            String lat = jsonObject.get("lat").getAsString();
            String lon = jsonObject.get("lon").getAsString();
            String icon = jsonObject.get("icon").getAsString();
            String gr_code = jsonObject.get("gr_code").getAsString();
            boolean sang = jsonObject.get("sang").getAsString().equals("1");
            boolean plaquettes = jsonObject.get("plaquettes").getAsString().equals("1");
            boolean plasma = jsonObject.get("plasma").toString().equals("1");
            String tel = jsonObject.get("num_tel").isJsonNull() ? null : jsonObject.get("num_tel").getAsString();
            String distance = jsonObject.get("distance").getAsString();
            String message = null;
            if (jsonObject.has("lp_com")) {
                String markup = jsonObject.get("lp_com").getAsJsonObject().get("#markup").getAsString();
                message = Utils.clean(markup);
            }
            String email = jsonObject.get("adr_mail").isJsonNull() ? null : jsonObject.get("adr_mail").getAsString();
            if (name.equals("null")) {
                name = ville;
            }

            Result center = new Result(name, date, start, end, tram, bus, metro, adresse, ville, lat, lon, icon, gr_code, sang, plaquettes, plasma, tel, distance, message, email);

            return center;
        };

        gsonBuilder
                .registerTypeAdapter(Result.class, jsonDeserializer)
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CollectCentersRepository.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gsonBuilder.create()))
                .build();

        CollectCenterApi collectCenterApi = retrofit.create(CollectCenterApi.class);

        retrofit2.Call<Post> call = collectCenterApi.getPosts(topRight.longitude, topRight.latitude, bottomLeft.longitude, bottomLeft.latitude);
        call.enqueue(new retrofit2.Callback<Post>() {
            @Override
            public void onResponse(retrofit2.Call<Post> call, retrofit2.Response<Post> response) {
                Log.d(TAG, "onResponse: " + response);
                if (!response.isSuccessful()) {
                    centers.setValue(new ResponseWrapper<>(response));
                }
                //Post posts = response.body();
                //List<Result> results = posts.getResults();
                centers.setValue(new ResponseWrapper<>(response));
            }

            @Override
            public void onFailure(retrofit2.Call<Post> call, Throwable t) {
                Log.d(TAG, "onFailure: " + t);
                t.printStackTrace();
                if (t instanceof SocketTimeoutException) {
                    Log.d(TAG, "onFailure: timeout exception caught");
                }
                centers.setValue(new ResponseWrapper<>(t));
            }
        });

        return centers;
    }
}
