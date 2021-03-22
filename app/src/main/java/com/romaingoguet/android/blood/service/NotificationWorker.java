package com.romaingoguet.android.blood.service;

import android.Manifest;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.util.Log;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.Task;
import com.google.common.util.concurrent.ListenableFuture;
import com.romaingoguet.android.blood.R;
import com.romaingoguet.android.blood.data.local.don.Don;
import com.romaingoguet.android.blood.data.models.Post;
import com.romaingoguet.android.blood.data.models.ResponseWrapper;
import com.romaingoguet.android.blood.data.repo.CollectCentersRepository;
import com.romaingoguet.android.blood.data.repo.DonRepository;
import com.romaingoguet.android.blood.ui.main.MainActivity;
import com.romaingoguet.android.blood.ui.main.MainApplication;
import com.romaingoguet.android.blood.utils.DonUtils;
import com.romaingoguet.android.blood.utils.MapUtils;

import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.concurrent.futures.ResolvableFuture;
import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;
import androidx.lifecycle.Observer;
import androidx.work.ListenableWorker;
import androidx.work.WorkerParameters;


public class NotificationWorker extends ListenableWorker {

    private final static String TAG = "notification";
    private CollectCentersRepository collectCenterRepository;
    private DonRepository donRepository;
    private ResolvableFuture<Result> mFuture;
    private Location location;
    private Set<String> tag;
    public static final String FROM_NOTIFICATION = "notification";

    public NotificationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        tag = workerParams.getTags();
        donRepository = new DonRepository(((MainApplication) context.getApplicationContext()));
        collectCenterRepository = new CollectCentersRepository();
    }

    @NonNull
    @Override
    public ListenableFuture<Result> startWork() {
        Log.d(TAG, "startWork: " + tag.iterator().next());
        mFuture = ResolvableFuture.create();
        // check if we got the location permission
        if (getApplicationContext().checkCallingOrSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            FusedLocationProviderClient fusedLocationProviderClient = new FusedLocationProviderClient(getApplicationContext());
            getData(fusedLocationProviderClient.getLastLocation());
        } else {
            Log.d(TAG, "startWork: need location permission");
        }
        return mFuture;
    }

    private void showNotification(String title, String desc) {
        Log.d(TAG, "showNotification: ");
        NotificationManager manager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

        //create intent
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.putExtra("from", FROM_NOTIFICATION);

        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addNextIntentWithParentStack(intent);
        // Get the PendingIntent containing the entire back stack
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), MainApplication.CHANNEL_1_ID)
                .setContentTitle(title)
                .setContentText(desc)
                .setSmallIcon(R.drawable.ic_blood_drop)
                .setStyle(new NotificationCompat.BigTextStyle().bigText(desc))
                .setContentIntent(resultPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        manager.notify(1, builder.build());
    }

    private void getData(Task<Location> task) {
        mFuture.set(Result.success());
        donRepository.getLastDon().observeForever(new Observer<Don>() {
            @Override
            public void onChanged(Don don) {
                donRepository.getLastDon().removeObserver(this);
                if (don == null || DonUtils.canDonate(don)) {
                    task.addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            location = task1.getResult();
                            Log.d(TAG, "getData: " + location.getLatitude() + " " + location.getLongitude());
                            LatLng topRight = MapUtils.getTopRightCoodinate(new LatLng(location.getLatitude(), location.getLongitude()));
                            LatLng bottomLeft = MapUtils.getBottomLeftCoordinate(new LatLng(location.getLatitude(), location.getLongitude()));
                            collectCenterRepository.getCenters(topRight, bottomLeft).observeForever(new Observer<ResponseWrapper<Post>>() {
                                @Override
                                public void onChanged(ResponseWrapper<Post> postResponseWrapper) {
                                    collectCenterRepository.getCenters(topRight, bottomLeft).removeObserver(this);
                                    if (!postResponseWrapper.isError()) {
                                        List<com.romaingoguet.android.blood.data.models.Result> centers = postResponseWrapper.getResponse().body().getResults();
                                        if (centers.size() > 0) {
                                            showNotification("Don de sang", "Vous pouvez donner.\nDes collectes de sang ont lieu près de chez vous.");
                                        } else {
                                            showNotification("Don de sang", "Vous pouvez donner.\nRecherchez des centres près de chez vous.");
                                        }
                                        mFuture.set(Result.success());
                                    } else {
                                        // return failure
                                        mFuture.set(Result.failure());
                                    }
                                }
                            });
                        } else {
                            mFuture.setException(task1.getException());
                        }
                    });
                }
            }
        });
    }
}