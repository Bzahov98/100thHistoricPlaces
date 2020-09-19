package com.tu.pmu.the100th.ui.AuthActivities.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import kotlinx.coroutines.launch
import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.databinding.ActivityLoginBinding
import com.tu.pmu.the100th.internal.glide.utils.ApiException
import com.tu.pmu.the100th.internal.glide.utils.NoInternetException
import com.tu.pmu.the100th.internal.glide.utils.snackbar
import com.tu.pmu.the100th.ui.AuthActivities.AuthViewModel
import com.tu.pmu.the100th.ui.AuthActivities.AuthViewModelFactory
import com.tu.pmu.the100th.ui.MainActivity.MainActivity
import com.tu.pmu.the100th.ui.AuthActivities.signUp.SignupActivity
import com.tu.pmu.the100th.ui.uiUtils.intentUtils.startMainActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class LoginActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    private lateinit var binding: ActivityLoginBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        binding.buttonSignIn.setOnClickListener {
            loginUser()
        }

        binding.textViewSignUp.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }
    }

    private fun observe() {
        viewModel.getLoggedInUser().observe(this, Observer { user ->
            if (user != null) {
//                startMainActivity()
            }
        })
    }


    private fun loginUser() {
        val name = binding.editTextEmail.text.toString().trim()
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()

        lifecycleScope.launch {
            try {
                val authResponse = viewModel.userLogin(email, password, name)
                if (authResponse.isSuccessful && authResponse.user != null) {
                    viewModel.saveLoggedInUser(authResponse.user)
                    startMainActivity(this@LoginActivity)
                    finish()
                    //observe()
                } else {
                    binding.rootLayout.snackbar(authResponse.message!!)
                }
            } catch (e: ApiException) {
                e.printStackTrace()
            } catch (e: NoInternetException) {
                e.printStackTrace()
            }
        }
    }
}