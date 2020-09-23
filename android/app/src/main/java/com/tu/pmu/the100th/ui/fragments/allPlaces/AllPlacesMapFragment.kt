package com.tu.pmu.the100th.ui.fragments.allPlaces

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.internal.utils.LocationUtils.Companion.getAdressNameFromLatLng
import com.tu.pmu.the100th.internal.utils.LocationUtils.Companion.putMarkerOnMap
import com.tu.pmu.the100th.internal.utils.PermissionUtils.PermissionDeniedDialog.Companion.newInstance
import com.tu.pmu.the100th.internal.utils.PermissionUtils.isPermissionGranted
import com.tu.pmu.the100th.internal.utils.PermissionUtils.requestPermission
import com.tu.pmu.the100th.internal.utils.intentUtils.startPlaceDetailActivity
import com.tu.pmu.the100th.ui.activities.placeDetails.PlaceDetailActivity
import kotlinx.android.synthetic.main.fragment_all_places_list.*
import kotlinx.android.synthetic.main.fragment_all_places_map.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.function.Consumer

class AllPlacesMapFragment : Fragment(), OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener,
    ActivityCompat.OnRequestPermissionsResultCallback, KodeinAware {
    private val TAG = "AllPlacesMapFragment"
    override val kodein by kodein()
    private val factory: AllPlacesMapFragmentViewModelFactory by instance<AllPlacesMapFragmentViewModelFactory>()
    private var permissionDenied = false
    private lateinit var map: GoogleMap
    private lateinit var viewModel: AllPlacesMapFragmentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(AllPlacesMapFragmentViewModel::class.java)
    }

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        map = googleMap ?: return@OnMapReadyCallback
        setupMap()
        enableMyLocation()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_places_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.allPlacesFragmentMap) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        GlobalScope.launch(Dispatchers.Main) {
            viewModel.getAllPlacesRequest(
                viewModel.getLastLocationLatLng()
            )
//            val bla = viewModel.allPlaces2
//            Log.e(TAG, "bla: ${bla.last().description}")
        }
        placesMapSearchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (!::map.isInitialized) return@setOnEditorActionListener false

//            val bla = viewModel.allPlaces2
//            Log.e(TAG, "bla: ${bla.last().description}")
                getAndShowAllPlacesBySearchName()
                true
            } else {
                false
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError()
            permissionDenied = false
        }
    }
    private fun getAndShowAllPlacesBySearchName() {
        searchFolAllPlacesBySearch()
        //updateReceivedData()
    }

    private fun searchFolAllPlacesBySearch() {
        //clearUpdateMapData(null)
        placesMapSearchEditText.isEnabled = false

        val placeName = placesMapSearchEditText.text.trim().toString()
        GlobalScope.launch(Dispatchers.Main) {
            viewModel.getAllPlacesRequestByName(
                placeName,
                viewModel.getLastLocationLatLng()
            )
        }.invokeOnCompletion {
            placesMapSearchEditText.isEnabled = true
        }

    }

    private fun setupMap() {
        map.setOnMyLocationButtonClickListener(this)
        map.setOnMyLocationClickListener(this)
        map.uiSettings.isZoomControlsEnabled = true
        map.uiSettings.isMapToolbarEnabled = true
        map.uiSettings.isCompassEnabled = true
        map.uiSettings.setAllGesturesEnabled(true)
        map.uiSettings.isTiltGesturesEnabled = true
        //       map.setMapStyle(MapStyleOptions())
        val bulgaria = LatLng(42.5850969, 25.0785703)
//        //googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Bulgaria"))
//        new MarkerOptions().icon(BitmapDescriptorFactory.fromResource(R.drawable.icon))
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(bulgaria, 6.5F))

        map.setOnPoiClickListener { poi ->
            val poiMarker = map.addMarker(
                MarkerOptions()
                    .position(poi.latLng)
                    .title(poi.name)
            )
            poiMarker.tag = "poi_${poiMarker.tag}"
            poiMarker.showInfoWindow()
        }

        map.setOnInfoWindowClickListener { marker ->
            if (marker.tag.toString().contains("poi_")) return@setOnInfoWindowClickListener
            Log.i("on click", marker.tag.toString())
            startPlaceDetailActivity(requireContext(), marker.tag.toString())
        }

        viewModel.placesEvent.observe(viewLifecycleOwner,
            Observer { t ->
                map.clear()
                t.content.forEach(Consumer { place ->
                    val marker = putMarkerOnMap(
                        map,
                        place.latLng,
                        place.name,
                        getAdressNameFromLatLng(place.latLng, requireContext())
                    )
                    marker.tag = place.id
                })
            })
    }

    private fun enableMyLocation() {
        if (!::map.isInitialized) return
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true

        } else {
            // Permission to access the location is missing. Show rationale and request permission
            requestPermission(
                requireActivity(), LOCATION_PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_FINE_LOCATION, true
            )
        }
        // [END maps_check_location_permission]
    }

    override fun onMyLocationButtonClick(): Boolean {
        Toast.makeText(requireContext(), "MyLocation button clicked", Toast.LENGTH_SHORT).show()
        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).
        return false
    }

    override fun onMyLocationClick(location: Location) {
        Toast.makeText(requireContext(), "Current location:\n$location", Toast.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return
        }
        if (isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation()
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private fun showMissingPermissionError() {
        newInstance(true).show(childFragmentManager, "dialog")
    }

    companion object {
        /**
         * Request code for location permission request.
         *
         *
         */
        const val LOCATION_PERMISSION_REQUEST_CODE = 1

        val PLACE_ID: String = "placeId"
    }

}
