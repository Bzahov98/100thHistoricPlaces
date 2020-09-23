package com.tu.pmu.the100th.ui.activities.placeDetails

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.databinding.ActivityPlaceDetailBinding
import com.tu.pmu.the100th.ui.fragments.allPlaces.AllPlacesMapFragment
import kotlinx.android.synthetic.main.activity_place_detail.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class PlaceDetailActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()

    private lateinit var viewModel: PlaceDetailViewModel
    private val factory: PlaceDetailViewModelFactory by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityPlaceDetailBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_place_detail)
        viewModel = ViewModelProvider(this, factory).get(PlaceDetailViewModel::class.java)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this
        viewModel.getById(intent.getStringExtra(AllPlacesMapFragment.PLACE_ID))

        viewModel.errorEvent.observe(this, Observer {
            Toast.makeText(this@PlaceDetailActivity, it, Toast.LENGTH_SHORT).show()
        })
        txv_map_button.setOnClickListener{
            val place = viewModel.place ?: return@setOnClickListener

            val gmmIntentUri = Uri.parse("geo:${place.latLng.latitude},${place.latLng.longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            mapIntent.resolveActivity(packageManager)?.let {
                startActivity(mapIntent)
            }

        }

    }
}