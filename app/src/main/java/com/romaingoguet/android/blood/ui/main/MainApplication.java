package com.romaingoguet.android.blood.ui.main;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.work.Constraints;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.NetworkType;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.romaingoguet.android.blood.service.NotificationWorker;

import java.util.concurrent.TimeUnit;

public class MainApplication extends Application {

    public static final String CHANNEL_1_ID = "channel1";
    public static final String WORKTAGv01 = "workv1";

    @Override
    public void onCreate() {
        super.onCreate();
//        if (LeakCanary.isInAnalyzerProcess(this)) {
//            // This process is dedicated to LeakCanary for heap analysis.
//            // You should not init your app in this process.
//            return;
//        }
//        LeakCanary.install(this);
//        // Normal app init code..
        createNotificationChannels();

        long installedtime = 0;
        try {
            installedtime = getApplicationContext()
                    .getPackageManager()
                    .getPackageInfo(getApplicationContext().getPackageName(), 0)
                    .firstInstallTime;
            Log.d("notification", "onCreate: installationdate" + installedtime);
        } catch (PackageManager.NameNotFoundException e) {

        }

        final WorkManager mWorkManager = WorkManager.getInstance(this);
        Constraints mConstraints = new Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build();
        final PeriodicWorkRequest mRequest = new PeriodicWorkRequest.Builder(NotificationWorker.class, 10, TimeUnit.DAYS, 1, TimeUnit.DAYS).setConstraints(mConstraints).build();
        mWorkManager.enqueueUniquePeriodicWork(WORKTAGv01, ExistingPeriodicWorkPolicy.KEEP, mRequest);
//        mWorkManager.cancelAllWork();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(
                    CHANNEL_1_ID,
                    "Channel 1",
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription("This is the reminder notification channel");
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }

}
