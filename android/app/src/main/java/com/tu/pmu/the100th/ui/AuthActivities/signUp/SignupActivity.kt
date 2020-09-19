package com.tu.pmu.the100th.ui.AuthActivities.signUp

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.data.db.entities.auth.User
import com.tu.pmu.the100th.databinding.ActivitySignupBinding
import com.tu.pmu.the100th.internal.glide.utils.ApiException
import com.tu.pmu.the100th.internal.glide.utils.NoInternetException
import com.tu.pmu.the100th.internal.glide.utils.snackbar
import com.tu.pmu.the100th.ui.MainActivity.MainActivity
import com.tu.pmu.the100th.ui.AuthActivities.AuthViewModel
import com.tu.pmu.the100th.ui.AuthActivities.AuthViewModelFactory
import com.tu.pmu.the100th.ui.uiUtils.intentUtils.startMainActivity
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class SignupActivity : AppCompatActivity(), KodeinAware {

    override val kodein by kodein()
    private val factory: AuthViewModelFactory by instance()

    private lateinit var binding: ActivitySignupBinding
    private lateinit var viewModel: AuthViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_signup)
        viewModel = ViewModelProvider(this, factory).get(AuthViewModel::class.java)

        //observe()

        binding.buttonSignUp.setOnClickListener {
            userSignup()
        }
    }

//    private fun observe() {
//        viewModel.getLoggedInUser().observe(this, Observer { user ->
//            if (user != null) {
//                startMainActivity(user)
//            }
//        })
//    }

//    private fun startMainActivity(user: User = User()) {
//        Intent(this, MainActivity::class.java).also {
////            it.putExtra("email", user.email)
////            it.putExtra("name", user.email)
//            it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//            startActivity(it)
//        }
//    }

    private fun userSignup() {
        val name = binding.editTextFirstName.text.toString().trim()
        val lastName = binding.editTextLastName.text.toString().trim()
        val email = binding.editTextEmail.text.toString().trim()
        val password = binding.editTextPassword.text.toString().trim()
        val password1 = binding.editTextPassword.text.toString().trim()

        //@todo add input validations

        lifecycleScope.launch {
            try {
                val authResponse = viewModel.userSignup(name, email, password)
                if (authResponse.user != null) {
                    viewModel.saveLoggedInUser(authResponse.user)
                    //observe()
                    startMainActivity(this@SignupActivity)
                } else {
                    binding.root.snackbar(authResponse.message!!)
                }
            } catch (e: ApiException) {
                e.printStackTrace()
            } catch (e: NoInternetException) {
                e.printStackTrace()
            }
        }
    }
}
