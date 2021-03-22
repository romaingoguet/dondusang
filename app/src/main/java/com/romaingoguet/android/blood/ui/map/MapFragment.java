package com.romaingoguet.android.blood.ui.map;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.romaingoguet.android.blood.R;
import com.romaingoguet.android.blood.data.models.Post;
import com.romaingoguet.android.blood.data.models.ResponseWrapper;
import com.romaingoguet.android.blood.data.models.Result;
import com.romaingoguet.android.blood.databinding.FragmentMapBinding;
import com.romaingoguet.android.blood.ui.center.CenterActivity;
import com.romaingoguet.android.blood.ui.common.ListAdapter;
import com.romaingoguet.android.blood.ui.main.MenuActivityViewModel;
import com.romaingoguet.android.blood.utils.MapUtils;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends Fragment {

    public static final String TAG = "lol";

    private View rootView;
    private Context mContext;
    private GoogleMap mMap;
    private ListAdapter mListAdapter;
    private LatLng topRight;
    private LatLng bottomLeft;
    private LatLng mapcenter;
    private CollectCentersViewModel collectCentersViewModel;
    private FusedLocationProviderClient fusedLocationClient;
    private LocationRequest locationRequest;
    private LocationCallback locationCallback;
    private LatLng latlng;
    private Location lastLocation;
    private final static int REQUEST_FINE_LOCATION = 123;
    private SupportMapFragment mapFragment;
    private FragmentTransaction fragmentTransaction;
    private FragmentManager fragmentManager;
    private FragmentMapBinding binding;
    private MapFragmentViewModel viewModel;
    private EventHandlers eventHandlers;
    private Button mButtonSeeMap;
    private Button mButtonSeeList;
    private MenuActivityViewModel menuActivityViewModel;
    private boolean locationPermission;
    private Activity activity;

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        // need this to use activity custom marker
        MapsInitializer.initialize(getActivity().getApplicationContext());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: + mapfragment");

        mContext = getActivity();

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false);

        rootView = binding.getRoot();

        locationPermission = true;

        mButtonSeeMap = rootView.findViewById(R.id.button_see_map);
        mButtonSeeList = rootView.findViewById(R.id.list_button);
        mButtonSeeMap.setVisibility(View.GONE);
        mButtonSeeList.setVisibility(View.GONE);

        menuActivityViewModel = ViewModelProviders.of(getActivity()).get(MenuActivityViewModel.class);
        viewModel = ViewModelProviders.of(this).get(MapFragmentViewModel.class);
        collectCentersViewModel = ViewModelProviders.of(this).get(CollectCentersViewModel.class);

        eventHandlers = new EventHandlers();

        binding.setEventHandler(eventHandlers);
        binding.setViewmodel(viewModel);
        binding.setCollectViewModel(collectCentersViewModel);

        binding.setLifecycleOwner(this);

        collectCentersViewModel.getCenter().observe(this, result -> Log.d("lol", "onChanged: changeddddddddddddddddddddd"));

        // write this to enable the settings button in the toolbar
        setHasOptionsMenu(true);

        //set the title of the screen
        getActivity().setTitle("Rechercher un centre");

        menuActivityViewModel.setIsLocationPermissionGranted(checkPermissions(false));

        //check if the location permission changed
        menuActivityViewModel.getIsLocationPermissionGranted().observe(getActivity(), aBoolean -> {
            if (aBoolean) {
                Log.d(TAG, "onChanged: permission granted");
                startLocationUpdates();
            } else {
                Log.d(TAG, "onChanged: permission non granted");
                showmap();
            }
        });

        // Start the location
        if (checkPermissions(true)) {
            Log.d(TAG, "onCreateView: checkpermission true");
            startLocationUpdates();
        } else {
            Log.d(TAG, "onCreateView: checkpermission true");
            showmap();
        }

        // Inflate the layout for this fragment
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroyView() {
        Log.d(TAG, "onDestroyView: map ");
        stopLocationUpdates();
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    protected void startLocationUpdates() {
        fusedLocationClient = new FusedLocationProviderClient(activity);
        locationRequest = new LocationRequest().setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                lastLocation = null;
                for (Location location : locationResult.getLocations()) {
                    Log.d("gps", "onLocationResult: " + location.getProvider());
                    Log.d("gps", "onLocationResult: " + location);
                    if (lastLocation != null) {
                        if (location.getTime() < lastLocation.getTime()) {
                            lastLocation = location;
                        }
                    } else {
                        lastLocation = location;
                    }
                }
                if (lastLocation != null) {
                    latlng = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
                    stopLocationUpdates();
                }
                // show the map
                showmap();
            }
        };
        try {
            fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
        } catch (SecurityException e) {
            // TODO : voir ce que l'on peut retourner à l'utilisateur ici.
            e.printStackTrace();
        }

    }

    @SuppressLint("MissingPermission")
    public void showmap() {
        if (!isAdded()) return;

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_fragment);
        fragmentManager = getFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        mapFragment = SupportMapFragment.newInstance();
        fragmentTransaction.replace(R.id.map_fragment, mapFragment).commitAllowingStateLoss();

        mapFragment.getMapAsync(googleMap -> {
            mMap = googleMap;
            if (latlng == null || latlng == MapUtils.FRANCE_MIDDLE) {
                locationPermission = false;
                latlng = MapUtils.FRANCE_MIDDLE;
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, MapUtils.CAMERA_ZOOM_COUNTRY));
            } else {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, MapUtils.CAMERA_ZOOM_DEFAULT));
            }
            mMap.setOnCameraIdleListener(() -> {
                Log.d(TAG, "showmap: " + mMap.getCameraPosition().zoom);
                LatLngBounds currentscreen = mMap.getProjection().getVisibleRegion().latLngBounds;
                if (isNetworkAvailable()) {
                    if (!viewModel.getIsCenterDetailsOpened().getValue()) {
                        viewModel.setIsLoading(true);
                        mapcenter = mMap.getCameraPosition().target;
                        topRight = MapUtils.getTopRightCoodinate(mapcenter);
                        bottomLeft = MapUtils.getBottomLeftCoordinate(mapcenter);
                        Log.d("lol", "onCameraIdle: " + bottomLeft + " " + topRight + " " + mapcenter);

                        if (collectCentersViewModel.getCenters() != null) {
                            Log.d(TAG, "onCameraIdle: mapcenter already populate " + collectCentersViewModel.getCenters());
                            hydrateMapAndList(collectCentersViewModel.getCenters().getResponse().body().getResults());
                        } else {
                            Log.d(TAG, "onCameraIdle: mapcenter not populate");
                        }

                        collectCentersViewModel.getallCenters(topRight, bottomLeft).observe(getActivity(), new Observer<ResponseWrapper<Post>>() {
                            @Override
                            public void onChanged(ResponseWrapper<Post> response) {
                                if (!response.isError()) {
                                    mMap.clear();
                                    MapFragment.this.hydrateMapAndList(response.getResponse().body().getResults());
                                    viewModel.setIsLoading(false);
                                } else {
                                    // show message
                                    Toast.makeText(mContext, "Problème de réseau, merci de réessayer plus tard", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                        if (checkPermissions(false)) {
                            mMap.setMyLocationEnabled(true);
                        }
                    } else {
                        // check if the selected marker is still in the screen and reload if it's not the case
                        Result center = collectCentersViewModel.getCenter().getValue();
                        LatLng centerMarker = new LatLng(Double.valueOf(center.getLat()), Double.valueOf(center.getLon()));
                        if (!currentscreen.contains(centerMarker)) {
                            mMap.clear();
                            // this trigger the cameraIdleListener
                            mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(currentscreen, 0));
                            viewModel.setIsCenterDetailsOpened(false);
                        }
                    }
                } else {
                    Toast.makeText(mContext, "Problème de réseau, merci de réessayer plus tard", Toast.LENGTH_SHORT).show();
                }

            });

            // detailscenter gone when the marker is unfocus
            mMap.setOnMapClickListener(latLng -> viewModel.setIsCenterDetailsOpened(false));

            mMap.setOnMarkerClickListener(marker -> {
                Result center = (Result) marker.getTag();

                collectCentersViewModel.setCenter(center);
                viewModel.setIsCenterDetailsOpened(true);
                return false;
            });

            mMap.setOnInfoWindowClickListener(marker -> {
                openCenterDetailActivity(getContext());
            });
            // without animation
            // with animation
            //mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latlng, 12));

            mMap.setOnMapLoadedCallback(() -> {
                // show the buttons
                showSwitchButtons();
            });
        });
    }

    private void hydrateMapAndList(List<Result> results) {
        for (Result result : results) {
            BitmapDescriptor icon = BitmapDescriptorFactory.fromAsset("marker_final.png");
            LatLng latlng = new LatLng(Double.valueOf(result.getLat()), Double.valueOf(result.getLon()));
            Marker marker = mMap.addMarker(new MarkerOptions().position(latlng)
                    .title(result.getName())
            );
            marker.setIcon(icon);
            marker.setTag(result);
        }

        Log.d(TAG, "hydrateMapAndList: ");
        mListAdapter = new ListAdapter(results);
        binding.setAdapter(mListAdapter);
    }

    protected void stopLocationUpdates() {
        Log.d("gps", "stopLocationUpdates: stoplocation");
        if (fusedLocationClient != null) {
            fusedLocationClient.removeLocationUpdates(locationCallback);
        }
        fusedLocationClient = null;
        locationRequest = null;
        locationCallback = null;
    }

    private boolean checkPermissions(boolean requestPermission) {
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            if (requestPermission) {
                requestPermissions();
            }
            return false;
        }
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(getActivity(),
                new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                REQUEST_FINE_LOCATION);
    }

    private void openCenterDetailActivity(Context context) {
        AppCompatActivity activity = (AppCompatActivity) context;
        Intent intent = new Intent(activity, CenterActivity.class);
        intent.putExtra("CollectCenter", collectCentersViewModel.getCenter().getValue());
        activity.startActivity(intent);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putDouble("lat", latlng.latitude);
        outState.putDouble("lon", latlng.longitude);
    }

    public class EventHandlers {

        public void changeview(View view) {
            viewModel.changeView();
        }

        public void onClickCenterDetailled(View view) {
            openCenterDetailActivity(view.getContext());
        }
    }

    public void showSwitchButtons() {
        mButtonSeeMap.setVisibility(View.VISIBLE);
        mButtonSeeList.setVisibility(View.VISIBLE);
    }
}
