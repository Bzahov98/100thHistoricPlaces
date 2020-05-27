package com.pmu.util;

import com.pmu.data.model.places.LatLng;

public class DistanceUtil {

    public static double calculateDistance(LatLng first, LatLng second) {
        double lon1 = Math.toRadians(first.getLongitude());
        double lon2 = Math.toRadians(second.getLongitude());
        double lat1 = Math.toRadians(first.getLatitude());
        double lat2 = Math.toRadians(second.getLatitude());

        // Haversine formula
        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2)
                * Math.pow(Math.sin(dlon / 2), 2);

        double c = 2 * Math.asin(Math.sqrt(a));

        // Radius of earth in kilometers. Use 3956
        // for miles
        double r = 6371;

        // calculate the result
        return (c * r);
    }
}
