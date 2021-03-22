package com.romaingoguet.android.blood.ui.center;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.romaingoguet.android.blood.R;
import com.romaingoguet.android.blood.data.local.favorite.Favorite;
import com.romaingoguet.android.blood.databinding.ActivityCenterBinding;
import com.romaingoguet.android.blood.data.models.Result;
import com.romaingoguet.android.blood.utils.Utils;
import com.romaingoguet.android.blood.ui.map.CollectCentersViewModel;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

public class CenterActivity extends AppCompatActivity {

    private Result mResult;
    public static final int MY_PERMISSIONS_REQUEST_CALENDAR = 123;
    private SupportMapFragment mapFragment;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private GoogleMap mMap;
    private LatLng latlng;
    private CollectCentersViewModel collectCentersViewModel;
    private CenterViewModel centerViewModel;
    private Favorite favorite;
    private List<Favorite> allFavorite = new ArrayList<>();
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_center);

        // we get the data of the collect center from the adapter
        collectCentersViewModel = ViewModelProviders.of(this).get(CollectCentersViewModel.class);

        centerViewModel = ViewModelProviders.of(this).get(CenterViewModel.class);

        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_primary_24dp);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        try {
            mResult = getIntent().getExtras().getParcelable("CollectCenter");
        } catch (NullPointerException e) {
            Toast.makeText(this, getString(R.string.show_center_error_message), Toast.LENGTH_SHORT);
            // TODO: Afficher un toast d'erreur ?

        }

        // we create the binding and we do all the binding in the layout
        ActivityCenterBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_center);
        View view = binding.getRoot();

        // bind the mResult for center in xml view
        binding.setCenter(mResult);

        favorite = new Favorite(mResult.getGr_code(), mResult.getName(), mResult.getLat(), mResult.getLon());

        CenterActivityHandlers handlers = new CenterActivityHandlers();
        binding.setClickHandler(handlers);

        //change the title
        this.setTitle("");
//        this.setTitle(mResult.getName());

        latlng = new LatLng(Double.valueOf(mResult.getLat()), Double.valueOf(mResult.getLon()));
        showmap();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.clear();
        this.menu = menu;
        menu.add(0, 0, Menu.NONE, "Favoris").setIcon(R.drawable.ic_favorite_border_24dp).setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
        centerViewModel.getAllFavorites().observe(this, favorites -> {
            allFavorite = favorites;
            if (isFavorite()) {
                menu.getItem(0).setIcon(R.drawable.ic_favorite_24dp);
            }
        });
        getMenuInflater().inflate(R.menu.center_share_menu, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_CALENDAR: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // location-related task you need to do.
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.READ_CALENDAR)
                            == PackageManager.PERMISSION_GRANTED) {

                        Log.d("lol", "onRequestPermissionsResult: granted");
                        //Request location updates:
                    }
                } else {
                    Log.d("lol", "onRequestPermissionsResult: denied");

                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.

                }
                return;
            }

        }
    }

    /**
     * show the map on the top of the view with the place of the center
     */
    public void showmap() {

        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.mapcenter);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        mapFragment = SupportMapFragment.newInstance();
        fragmentTransaction.replace(R.id.mapcenter, mapFragment).commit();

        mapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;
            mMap.getUiSettings().setMapToolbarEnabled(false);
            mMap.getUiSettings().setAllGesturesEnabled(false);
            BitmapDescriptor icon = BitmapDescriptorFactory.fromAsset("marker_final.png");

            Marker marker = mMap.addMarker(new MarkerOptions().position(latlng).title(mResult.getName()));
            marker.setIcon(icon);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        } else if (item.getItemId() == R.id.action_share) {
            String message;
            if (mResult.getGr_code().equals("SITEFX")) {
                message = "Venez nombreux pour donner votre sang à " + mResult.getName();
            } else {
                message = "Venez nombreux pour donner votre sang à " + mResult.getVille() + " le " + mResult.getDate();
            }
            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Don de sang");
            shareIntent.putExtra(Intent.EXTRA_TEXT, message);
            shareIntent.setType("text/plain");
            startActivity(Intent.createChooser(shareIntent, "Partager"));
        } else if (item.getItemId() == 0) {
            if (item.getIcon().getConstantState().equals(
                    getResources().getDrawable(R.drawable.ic_favorite_24dp).getConstantState())) {
                item.setIcon(R.drawable.ic_favorite_border_24dp);
                // delete favorite from bdd
                centerViewModel.delete(favorite);
            } else if (item.getIcon().getConstantState().equals(
                    getResources().getDrawable(R.drawable.ic_favorite_border_24dp).getConstantState())) {
                item.setIcon(R.drawable.ic_favorite_24dp);
                // add favorite to bdd
                centerViewModel.insert(favorite);
                Toast.makeText(this, "Ajouté aux favoris", Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public class CenterActivityHandlers {

        /**
         * intent for the phone call
         *
         * @param center
         */
        public void onClickPhone(Result center) {
            String phone = center.getTel();
            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
            startActivity(intent);
        }

        /**
         * intent for the "goto" center
         *
         * @param center
         */
        public void onClickNavigation(Result center) {
            String packageName = "com.google.android.apps.maps";
            String query = "google.navigation:q=" + center.getLat() + "," + center.getLon();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(query));
            intent.setPackage(packageName);
            startActivity(intent);
        }

        /**
         * intent for adding the event to the google calendar
         *
         * @param center
         */
        public void onClickAddEventToCalendar(Result center) {
            Intent callIntent = new Intent(Intent.ACTION_INSERT);
            callIntent.setType("vnd.android.cursor.item/event");
            callIntent.putExtra(CalendarContract.Events.TITLE, "Don de sang à " + center.getVille());
            callIntent.putExtra(CalendarContract.Events.EVENT_LOCATION, center.getName() + " à " + center.getVille());
            callIntent.putExtra(CalendarContract.Events.DESCRIPTION, "Don de sang");


            if (center.getStart() == null) {
                Log.d("lol", "onClickAddEventToCalendar: center null");
                //set date as today and event all day
                callIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, true);
            } else {
                //set the date and hour range
                callIntent.putExtra(CalendarContract.EXTRA_EVENT_ALL_DAY, false);
                callIntent.putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME,
                        Utils.convertStringDatetoTS(center.getDate(), center.getStart()).getTime());
                callIntent.putExtra(CalendarContract.EXTRA_EVENT_END_TIME,
                        Utils.convertStringDatetoTS(center.getDate(), center.getEnd()).getTime());

            }
            startActivity(callIntent);
        }
    }

    public boolean isFavorite() {
        favorite = new Favorite(mResult.getGr_code(), mResult.getName(), mResult.getLat(), mResult.getLon());
        Log.d("lol", "isFavorite: " + favorite);
        for (Favorite fav : allFavorite) {
            Log.d("lol", "isFavorite: fav " + fav);
            if (fav.toString().equals(favorite.toString())) {
                return true;
            }
        }
        return false;
    }
}
