package com.tu.pmu.the100th.ui.mainActivity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.databinding.ActivityPlaceDetailBinding
import com.tu.pmu.the100th.databinding.ProfileFragmentBinding
import com.tu.pmu.the100th.ui.fragments.profile.ProfileViewModel
import com.tu.pmu.the100th.ui.fragments.profile.ProfileViewModelFactory
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.android.x.kodein
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

    }
}