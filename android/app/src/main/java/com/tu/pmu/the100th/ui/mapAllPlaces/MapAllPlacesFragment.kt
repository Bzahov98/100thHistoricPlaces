package com.tu.pmu.the100th.ui.mapAllPlaces

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider

import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.ui.base.ScopedFragment
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.kodein
import org.kodein.di.generic.instance

class MapAllPlacesFragment : ScopedFragment(), KodeinAware {
    override val kodein by kodein()
    private val viewModelFactory: MapAllPlacesViewModelFactory by instance<MapAllPlacesViewModelFactory>()
    private lateinit var viewModel: MapAllPlacesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.map_all_places_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this,viewModelFactory).get(MapAllPlacesViewModel::class.java)
        // TODO: Use the ViewModel
        //bindUI()
        //attachMap()
        
    }

}
