package com.tu.pmu.the100th.ui.activities.authActivities.signUp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.data.db.entities.auth.SignupUserJsonBody
import com.tu.pmu.the100th.databinding.ActivitySignupBinding
import com.tu.pmu.the100th.internal.utils.LongDateUtils
import com.tu.pmu.the100th.internal.utils.hideSoftKeyboard
import com.tu.pmu.the100th.internal.utils.snackbar
import com.tu.pmu.the100th.ui.activities.authActivities.AuthViewModel
import com.tu.pmu.the100th.ui.activities.authActivities.AuthViewModelFactory
import com.tu.pmu.the100th.internal.utils.intentUtils.startLoginActivity
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.activity_signup.login100places
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.util.*

class SignupActivity : AppCompatActivity(), KodeinAware {
    private val TAG = "SignupActivity"
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
        // Debug:
        login100places.setOnClickListener {
            setViewSignUpValues()
        }



        signupDateButton.setOnClickListener {
            val constraintsBuilder = CalendarConstraints.Builder()  // 1
            val calendar = Calendar.getInstance()
            constraintsBuilder.setEnd(calendar.timeInMillis)   // 4

            val builder = MaterialDatePicker.Builder.datePicker()
            builder.setTitleText("Please add your birth date")
            //builder.setSelection(currentTimeInMillis)
            builder.setCalendarConstraints(constraintsBuilder.build())
            val picker = builder.build()

            picker.show(supportFragmentManager, picker.toString())
            picker.addOnPositiveButtonClickListener {
                val date = it
                if ((LongDateUtils.isLongDateBeforeToday(it))) {
                    signupBirthDate.setText(LongDateUtils.convertLongToTime(date))
                }else{
                    signupActivityRoot.snackbar(getString(R.string.error_choose_date_before_today))
                }
            }
        }

        signupActivityRoot.setOnTouchListener { v, event ->
            if (currentFocus != null) {
                hideSoftKeyboard(this)
                v.performClick()
            }
            false
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
        val name = signupFirstName.text.toString().trim()
        val lastName = signupLastName.text.toString().trim()
        val email = signupEmail.text.toString().trim()
        val password = signupPassword.text.toString().trim()
        val password1 = signupPasswordConfirm.text.toString().trim()
        val birthDate = signupBirthDate.text.toString().trim()
        val nationality = "BG"//signupNationality

        //@todo add input validations

        if (!areFieldsValid(name, lastName, email, password, password1, birthDate)) {
            return
        }
        val newUser = SignupUserJsonBody(birthDate, email, name, lastName, nationality, password)

        GlobalScope.launch(Dispatchers.Main) {

            viewModel.userSignup(newUser)
            val await = viewModel.userLastSignupResponse.await()
            await.observe(this@SignupActivity, Observer { response ->
                if (response == null || response.email != newUser.email) {
                    signupActivityRoot.snackbar("User registration failed\n User already exist")
                    return@Observer
                }
                signupActivityRoot.snackbar("Welcome ${response.firstName} ${response.lastName}")
                startLoginActivity(this@SignupActivity, email)
                // finish()
            })
//            val userData = viewModel.userData.await()
//            userData.observe(this@SignupActivity, Observer { user ->
//                if (user == null || user.userStatus == UserStatusEnum.LoggedOut) {
//                    loginActivityRoot.snackbar("Wrong email or password")
//                    clearViewSignUpValues()
//                    return@Observer
//                }
//                Log.d(TAG, "Update user with that data: $user")
//                startLoginActivity(this@SignupActivity)
//                finish()
//            })
        }
    }


    private fun areFieldsValid(
        name: String,
        lastName: String,
        email: String,
        password: String,
        password1: String,
        birthDate: String
    ): Boolean {
        val isValid = true

        return isValid // TODO
    }

    //    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
//        if (currentFocus != null) {
//            val imm =  (Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
//        }
//        return super.dispatchTouchEvent(ev)
//    }
    private fun setViewSignUpValues() {
        signupEmail.setText(getString(R.string.debug_test_user_email_new))
        signupPassword.setText(getString(R.string.debug_test_user_pass))
        signupFirstName.setText(getString(R.string.debug_test_user_first_name))
        signupLastName.setText(getString(R.string.debug_test_user_last_name))
        signupBirthDate.setText(getString(R.string.debug_test_user_birth_date))
    }

    private fun clearViewSignUpValues() {
        signupEmail.setText("")
        signupPassword.setText("")
        signupFirstName.setText("")
        signupLastName.setText("")
        signupBirthDate.setText("")
    }
}
