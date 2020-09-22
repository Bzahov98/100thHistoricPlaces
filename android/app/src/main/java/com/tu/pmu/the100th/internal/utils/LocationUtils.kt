package com.tu.pmu.the100th.internal.utils

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.tu.pmu.the100th.data.provider.interfaces.LocationProvider
import java.io.IOException
import java.util.*

class LocationUtils {
    companion object {
        private val TAG = "LocationUtils"

        fun getAdressNameFromLatLng(latLng: LatLng, context: Context): String {
            // 1
            val geocoder = Geocoder(context)
            val addresses: List<Address>?
            val address: Address?
            var addressText = ""

            try {
                // 2
                addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
                // 3
                // REWORK not working as expected!!!
                if (null != addresses && addresses.isNotEmpty()) {
                    address = addresses[0]
                    for (i in 0 until address.maxAddressLineIndex) {
                        addressText += if (i == 0) address.getAddressLine(i) else "\n" + address.getAddressLine(
                            i
                        )
                    }
                }
            } catch (e: IOException) {
                Log.e(TAG, e?.localizedMessage)

            }
            if(addressText == ""){
                addressText = String.format(
                    Locale.getDefault(),
                    "Lat: %1$.5f, Long: %2$.5f",
                    latLng.latitude,
                    latLng.longitude
                )
            }
            return addressText
        }

        fun putMarkerOnMap(map: GoogleMap, latLng: LatLng, title : String, snippet :String) : Marker {
             return map.addMarker(
                MarkerOptions()
                    .position(latLng)
                    .title(title)
                    .snippet(snippet)
            )
        }
    }
}