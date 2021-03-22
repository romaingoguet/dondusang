package com.romaingoguet.android.blood.utils;

import com.google.android.gms.maps.model.LatLng;

public class MapUtils {

    public static final double MAP_RADIUS = 20; // km
    public static final double DEG_TO_KM = 112; // km
    public static final LatLng PARIS_LOC = new LatLng(48.8534, 2.3488);
    public static final LatLng FRANCE_MIDDLE = new LatLng(46.52863469527167,2.43896484375);
    public static final float CAMERA_ZOOM_DEFAULT = 12;
    public static final float CAMERA_ZOOM_COUNTRY = 5;



    /**
     * @param mapcenter
     * @return
     */
    public static LatLng getTopRightCoodinate(LatLng mapcenter) {
        return new LatLng(mapcenter.latitude + MAP_RADIUS / DEG_TO_KM, mapcenter.longitude + MAP_RADIUS / DEG_TO_KM);
    }

    /**
     * @param mapcenter
     * @return
     */
    public static LatLng getBottomLeftCoordinate(LatLng mapcenter) {
        return new LatLng(mapcenter.latitude - MAP_RADIUS / DEG_TO_KM, mapcenter.longitude - MAP_RADIUS / DEG_TO_KM);
    }
}
