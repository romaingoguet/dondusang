package com.romaingoguet.android.blood.utils;

import com.romaingoguet.android.blood.data.local.don.Don;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DonUtils {

    private static final long WEEKS_TO_MILLISEC = 7 * 24 * 60 * 60 * 1000;
    public static final long SANG_SANG = 8 * WEEKS_TO_MILLISEC;
    public static final long SANG_PLAQUETTES = 4 * WEEKS_TO_MILLISEC;
    public static final long SANG_PLASMA = 2 * WEEKS_TO_MILLISEC;
    public static final long PLAQUETTES_SANG = 4 * WEEKS_TO_MILLISEC;
    public static final long PLAQUETTES_PLAQUETTES = 4 * WEEKS_TO_MILLISEC;
    public static final long PLAQUETTES_PLASMA = 2 * WEEKS_TO_MILLISEC;
    public static final long PLASMA_SANG = 2 * WEEKS_TO_MILLISEC;
    public static final long PLASMA_PLAQUETTES = 2 * WEEKS_TO_MILLISEC;
    public static final long PLASMA_PLASMA = 2 * WEEKS_TO_MILLISEC;

    public static final String SANG = "Sang";
    public static final String PLASMA = "Plasma";
    public static final String PLAQUETTES = "Plaquettes";

    public static final String NOW = "now";
    private static final String DONATE_NOW = "Vous pouvez faire un don";

    /**
     * @param dons
     * @return
     */
    public static String[] calculFuturDonationsFromList(List<Don> dons) {
        List<Long[]> futurDate = new ArrayList<>();
        for (Don don : dons) {
            if (don.getType().equals(SANG)) {
                futurDate.add(new Long[]{don.getDate() + SANG_SANG, don.getDate() + SANG_PLAQUETTES, don.getDate() + SANG_PLASMA});
            } else if (don.getType().equals(PLASMA)) {
                futurDate.add(new Long[]{don.getDate() + PLASMA_SANG, don.getDate() + PLASMA_PLAQUETTES, don.getDate() + PLASMA_PLASMA});
            } else if (don.getType().equals(PLAQUETTES)) {
                futurDate.add(new Long[]{don.getDate() + PLAQUETTES_SANG, don.getDate() + PLAQUETTES_PLAQUETTES, don.getDate() + PLAQUETTES_PLASMA});
            }
        }
        Long[] calculatedDates = futurDate.get(0);
        for (Long[] date : futurDate) {
            for (int i = 0; i < calculatedDates.length; i++) {
                if (date[i] > calculatedDates[i]) {
                    calculatedDates[i] = date[i];
                }
            }
        }
        String[] returnedMssage = new String[]{DONATE_NOW, DONATE_NOW, DONATE_NOW};
        long now = new Date().getTime();
        for (int i = 0; i < calculatedDates.length; i++) {
            if (calculatedDates[i] > now) {
                returnedMssage[i] = Utils.tsDateToString(calculatedDates[i]);
            }
        }
        return returnedMssage;
    }

    /**
     * @param don
     * @return boolean
     */
    public static boolean canDonate(Don don) {
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        long now = timestamp.getTime();
        switch (don.getType()) {
            case SANG:
                return now >= don.getDate() + SANG_SANG;
            case PLAQUETTES:
                return now >= don.getDate() + PLAQUETTES_SANG;
            case PLASMA:
                return now >= don.getDate() + PLASMA_SANG;
            default:
        }
        return false;
    }

    /**
     * @param date
     * @return string to show on the accueil page
     */
    public static String getNextDonDate(String date) {
        String ret = date;
        if (date.equals(NOW)) {
            ret = DONATE_NOW;
        } else {
            // TODO : returner "Vous pouvez faire un don" si la date est supérieur à aujourd'hui
        }
        return ret;
    }


}
