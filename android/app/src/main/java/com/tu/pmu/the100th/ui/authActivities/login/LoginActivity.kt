package com.tu.pmu.the100th.ui.authActivities.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.data.db.entities.auth.UserStatusEnum
import com.tu.pmu.the100th.databinding.ActivityLoginBinding
import com.tu.pmu.the100th.internal.utils.ValidationUtils
import com.tu.pmu.the100th.internal.utils.hideSoftKeyboard
import com.tu.pmu.the100th.internal.utils.snackbar
import com.tu.pmu.the100th.ui.authActivities.AuthViewModel
import com.tu.pmu.the100th.ui.authActivities.AuthViewModelFactory
import com.tu.pmu.the100th.ui.authActivities.signUp.SignupActivity
import com.tu.pmu.the100th.internal.utils.intentUtils.startMainActivity
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance


class LoginActivity : AppCompatActivity(), KodeinAware {
    private val TAG = "LoginActivity"
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

        // Debug: ...............v
        login100places.setOnClickListener {
            loginEmail.setText(getString(R.string.debug_test_user_email))
            loginPassword.setText(getString(R.string.debug_test_user_pass))
        }

        loginActivityRoot.setOnTouchListener { v, event ->
            if (currentFocus != null) {
                hideSoftKeyboard(this)
                v.performClick()
            }
            false
        }

    }

    override fun onResume() {
        super.onResume()
        val extras = intent.extras
        val signedEmail: String?
        if (extras != null) {
            signedEmail = extras.getString("email")
            if (signedEmail != "") {
                loginEmail.setText(signedEmail)
            }
        }
    }

    private fun loginUser() {
        //val name = binding.editTextEmail.text.toString().trim()
        val email = binding.loginEmail.text.toString().trim()
        val password = binding.loginPassword.text.toString().trim()

        if (!areFieldsValid(email, password)) {
            return
        }
        GlobalScope.launch(Dispatchers.Main) {

            viewModel.userLogin(email, password)
            val userData = viewModel.userData.await()
            userData.observe(this@LoginActivity, Observer { user ->
                if (user == null || user.userStatus == UserStatusEnum.LoggedOut) {
                    loginActivityRoot.snackbar("Wrong email or password")
                    clearInputData()
                    return@Observer
                }
                Log.d(TAG, "Update user with that data: $user")
                startMainActivity(this@LoginActivity)
                finish()
            })
        }
    }

    private fun areFieldsValid(email: String, password: String): Boolean {
        if (!ValidationUtils.isValidEmail(email)) {
            loginActivityRoot.snackbar("Email address is invalid")
            loginEmail.error = "Email address is invalid"
            clearInputData()
            return false
        }
        if (!ValidationUtils.isValidPassword(password)) {
            loginActivityRoot.snackbar("Password is in invalid format")
            clearInputData()
            loginPassword.error = "Email address is invalid"
            return false
        }
        return true
    }

    private fun clearInputData() {
        loginEmail.setText("")
        loginPassword.setText("")
    }
//    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        if (currentFocus != null) {
//            val imm =  (Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
//        }
//        return super.dispatchTouchEvent(ev)
//    }


}