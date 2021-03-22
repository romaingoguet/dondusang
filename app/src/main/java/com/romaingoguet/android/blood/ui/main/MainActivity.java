package com.romaingoguet.android.blood.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.romaingoguet.android.blood.R;
import com.romaingoguet.android.blood.service.NotificationWorker;
import com.romaingoguet.android.blood.ui.don.DonsFragment;
import com.romaingoguet.android.blood.ui.home.ProfileFragment;
import com.romaingoguet.android.blood.ui.map.MapFragment;
import com.romaingoguet.android.blood.ui.quiz.QuizActivity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProviders;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView navigation;
    private MenuActivityViewModel menuActivityViewModel;
    private String from;
    private Fragment profileFragment;
    private Fragment mapFragment;
    private Fragment donFragment;
    private Fragment active;
    private final FragmentManager fm = getSupportFragmentManager();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        menuActivityViewModel = ViewModelProviders.of(this).get(MenuActivityViewModel.class);

        try {
            from = getIntent().getExtras().getString("from");
        } catch (NullPointerException e) {

        }

        if (from != null && from.equals(NotificationWorker.FROM_NOTIFICATION)) {
            showMapFragment();
        } else {
            showProfileFragment();
        }

        navigation.setOnNavigationItemSelectedListener(menuItem -> {
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    showProfileFragment();
                    return true;
                case R.id.navigation_map:
                    showMapFragment();
                    return true;
                case R.id.navigation_donation:
                    showDonFragment();
                    return true;
            }
            return false;
        });
    }

    @Override
    public void onBackPressed() {
        if (active == profileFragment) {
            this.finish();
        } else {
            showProfileFragment();
        }
    }

    public class ClickHandler {

        private AppCompatActivity activity;

        public ClickHandler(AppCompatActivity activity) {
            this.activity = activity;
        }

        public void onSearchCenterClick() {
            Log.d("lol", "onSearchCenterClick: ");
            showMapFragment();
        }

        public void onQuizActivityClick() {
            Intent intent = new Intent(activity, QuizActivity.class);
            activity.startActivity(intent);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        Log.d("lol", "onRequestPermissionsResult: " + grantResults[0] + " requestCode" + requestCode + " permissions" + permissions[0]);
        if (grantResults[0] == 0) {
            menuActivityViewModel.setIsLocationPermissionGranted(true);
        } else {
            menuActivityViewModel.setIsLocationPermissionGranted(false);
        }
    }

    private void showProfileFragment() {
        navigation.getMenu().getItem(0).setChecked(true);
        if (profileFragment == null) {
            profileFragment = new ProfileFragment();
            fm.beginTransaction().add(R.id.central_fragment, profileFragment).commit();
        }
        if (active == null) {
            fm.beginTransaction().show(profileFragment).commit();
        } else {
            fm.beginTransaction().hide(active).show(profileFragment).commit();
        }
        active = profileFragment;
    }

    private void showMapFragment() {
        navigation.getMenu().getItem(1).setChecked(true);
        if (mapFragment == null) {
            mapFragment = new MapFragment();
            fm.beginTransaction().add(R.id.central_fragment, mapFragment).commit();
        }
        if (active == null) {
            fm.beginTransaction().show(mapFragment).commit();
        } else {
            fm.beginTransaction().hide(active).show(mapFragment).commit();
        }
        active = mapFragment;
    }

    private void showDonFragment() {
        navigation.getMenu().getItem(2).setChecked(true);
        if (donFragment == null) {
            donFragment = new DonsFragment();
            fm.beginTransaction().add(R.id.central_fragment, donFragment).commit();
        }
        if (active == null) {
            fm.beginTransaction().show(donFragment).commit();
        } else {
            fm.beginTransaction().hide(active).show(donFragment).commit();
        }
        active = donFragment;
    }
}
