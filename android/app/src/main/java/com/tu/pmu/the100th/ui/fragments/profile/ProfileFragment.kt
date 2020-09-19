package com.tu.pmu.the100th.ui.fragments.profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.data.db.entities.auth.UserStatusEnum
import com.tu.pmu.the100th.databinding.ProfileFragmentBinding
import com.tu.pmu.the100th.ui.uiUtils.intentUtils.startLoginActivity
import kotlinx.android.synthetic.main.profile_fragment.*
import org.kodein.di.android.x.kodein
import org.kodein.di.KodeinAware
import org.kodein.di.generic.instance

class ProfileFragment : Fragment(), KodeinAware {

    override val kodein by kodein()

    private lateinit var viewModel: ProfileViewModel
    private val factory: ProfileViewModelFactory by instance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ProfileFragmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.profile_fragment, container, false)
        viewModel = ViewModelProvider(this, factory).get(ProfileViewModel::class.java)
        binding.viewmodel = viewModel
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        profileLogoutButton.setOnClickListener {
            logoutCurrentUser()
        }
    }
    override fun onResume() {
        super.onResume()
        viewModel.getLoggedInUser().observe(viewLifecycleOwner, Observer { user ->
            if (user == null || user.userStatus == UserStatusEnum.LoggedOut) {
                logoutCurrentUser()
            }
        })
    }
    private fun logoutCurrentUser(){
        startLoginActivity(this.requireContext())
        requireActivity().finish()
        viewModel.logOut()

    }
}
