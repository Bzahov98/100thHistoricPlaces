package com.tu.pmu.the100th.ui.fragments.allPlacesList

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.internal.utils.PermissionUtils
import com.tu.pmu.the100th.internal.utils.intentUtils.startPlaceDetailActivity
import com.tu.pmu.the100th.ui.fragments.base.ScopedFragment
import com.tu.pmu.the100th.ui.fragments.allPlaces.AllPlacesMapFragment
import com.tu.pmu.the100th.ui.fragments.allPlacesList.recyclerview.PlacesListItem
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.fragment_all_places_list.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance
import java.util.function.Consumer

class AllPlacesListFragment : ScopedFragment(), KodeinAware {
    private val TAG = "AllPlacesListFragment"
    override val kodein by kodein()
    private val factory: AllPlacesListFragmentViewModelFactory by instance<AllPlacesListFragmentViewModelFactory>()
    private var permissionDenied = false
    private lateinit var viewModel: AllPlacesListFragmentViewModel
    private lateinit var groupAdapter: GroupAdapter<ViewHolder>
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, factory).get(AllPlacesListFragmentViewModel::class.java)
        checkPermission()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_places_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        //updateRecyclerViewData()
        //getAndShowAllPlaces()

        placesSearchEditText.setOnEditorActionListener { v, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                clearUpdateRecyclerViewData(null)
                getAndShowAllPlacesBySearchName()
                true
            } else {
                false
            }
        }
    }

    private fun getAndShowAllPlaces() {
//        searchFolAllPlaces()
//        updateReceivedData()
    }

    private fun getAndShowAllPlacesBySearchName() {
        searchFolAllPlacesBySearch()
        updateReceivedData()
    }

    private fun updateReceivedData() {

        val listItem = HashSet<PlacesListItem>()
        viewModel.placesEvent.observe(
            viewLifecycleOwner,
            Observer { t ->
                run {
                    listItem.clear()
                    t.content.forEach(Consumer { place ->
                        listItem.add(PlacesListItem(place))
                    })
                    clearUpdateRecyclerViewData(listItem)
                }
            })
    }

//    private fun searchFolAllPlaces() {
//        clearUpdateRecyclerViewData(null)
//        GlobalScope.launch(Dispatchers.Main) {
//            viewModel.getAllPlacesRequest(
//                viewModel.getLastLocationLatLng()
//            )
//        }
//    }

    private fun searchFolAllPlacesBySearch() {
        clearUpdateRecyclerViewData(null)
        placesSearchEditText.isEnabled = false

        val placeName = placesSearchEditText.text.trim().toString()
        GlobalScope.launch(Dispatchers.Main) {
            viewModel.getAllPlacesRequestByName(
                placeName,
                viewModel.getLastLocationLatLng()
            )
        }.invokeOnCompletion {
            placesSearchEditText.isEnabled = true
        }
    }

    override fun onResume() {
        super.onResume()
        clearUpdateRecyclerViewData(null)
        if (permissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError()
            permissionDenied = false
        }
    }

    private fun initRecyclerView() {
        groupAdapter = GroupAdapter<ViewHolder>()
        recyclerView = placesListRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@AllPlacesListFragment.context)
            adapter = groupAdapter
        }

        groupAdapter.setOnItemClickListener { item, view ->
            val itemDetail = (item as PlacesListItem).data
            Toast.makeText(
                this@AllPlacesListFragment.context,
                "Clicked: ${itemDetail.name}",
                Toast.LENGTH_SHORT
            ).show()
            startPlaceDetailActivity(requireContext(), itemDetail.id)

            Log.e(TAG, "\n\nGroupAdapter.setOnItemClickListener ${itemDetail}\n")
            //showWeatherDetail(itemDetail.dtTxt, view)
        }
    }

    private fun clearUpdateRecyclerViewData(items: Set<PlacesListItem>?) { // null only to clear data
        groupAdapter.clear()
        if (!items.isNullOrEmpty()) {
            groupAdapter.apply { addAll(items) }
        }
        groupAdapter.notifyDataSetChanged()
    }

    private fun checkPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d(TAG, "checkPermission: have permission")

        } else {
            // Permission to access the location is missing. Show rationale and request permission
            PermissionUtils.requestPermission(
                requireActivity(), AllPlacesMapFragment.LOCATION_PERMISSION_REQUEST_CODE,
                Manifest.permission.ACCESS_FINE_LOCATION, true
            )
        }
        // [END maps_check_location_permission]
    }

    private fun showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog.newInstance(true)
            .show(childFragmentManager, "dialog")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode != AllPlacesMapFragment.LOCATION_PERMISSION_REQUEST_CODE) {
            return
        }
        if (PermissionUtils.isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        ) { // TODO permission is granted
            //checkPermission()
        } else {
            // Permission was denied. Display an error message
            // Display the missing permission error dialog when the fragments resume.
            permissionDenied = true
        }
    }

}
