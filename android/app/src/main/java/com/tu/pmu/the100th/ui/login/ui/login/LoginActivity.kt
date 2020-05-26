package com.tu.pmu.the100th.ui.login.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.ui.MainActivity
import com.tu.pmu.the100th.ui.login.ui.login.utils.LoggedInUserView
import kotlinx.android.synthetic.main.activity_login.*
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class LoginActivity : AppCompatActivity() /*, KodeinAware*/{

    //override val kodein: Kodein by closestKodein()
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        loginViewModel = ViewModelProvider(this, LoginViewModelFactory())
            .get(LoginViewModel::class.java)

        bindUI()

        logInUsername.afterTextChanged {
            loginViewModel.loginDataChanged(
                logInUsername.text.toString(),
                logInPassword.text.toString()
            )
        }

        logInPassword.apply {
            afterTextChanged {
                loginViewModel.loginDataChanged(
                    logInUsername.text.toString(),
                    logInPassword.text.toString()
                )
            }

            setOnEditorActionListener { _, actionId, _ ->
                when (actionId) {
                    EditorInfo.IME_ACTION_DONE ->
                        loginViewModel.login(
                            logInUsername.text.toString(),
                            logInPassword.text.toString()
                        )
                }
                false
            }

            loginButton.setOnClickListener {
                loginLoading.visibility = View.VISIBLE
                loginViewModel.login(logInUsername.text.toString(), logInPassword.text.toString())
            }
        }
    }

    private fun bindUI(
    ) {
        loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
            val loginState = it ?: return@Observer

            // disable login button unless both logInUsername / logInPassword is valid
            loginButton.isEnabled = loginState.isDataValid

            if (loginState.usernameError != null) {
                logInUsername.error = getString(loginState.usernameError)
            }
            if (loginState.passwordError != null) {
                logInPassword.error = getString(loginState.passwordError)
            }
        })

        loginViewModel.loginResult.observe(this@LoginActivity, Observer {
            val loginResult = it ?: return@Observer

            loginLoading.visibility = View.GONE
            if (loginResult.error != null) {
                showLoginFailed(loginResult.error)
            }
            if (loginResult.success != null) {
                updateUiWithUser(loginResult.success)
            }

            val returnIntent = Intent()
            returnIntent.putExtra("username", loginResult.success?.displayName)
            setResult(MainActivity.LOGIN_ACTIVITY_SUCCESS,returnIntent)
            //Complete and destroy loginButton activity once successful
            finish()
        })
    }

    private fun updateUiWithUser(model: LoggedInUserView) {
        val welcome = getString(R.string.welcome)
        val displayName = model.displayName
        // TODO : initiate successful logged in experience
        Toast.makeText(
            applicationContext,
            "$welcome $displayName",
            Toast.LENGTH_LONG
        ).show()

    }

    private fun showLoginFailed(@StringRes errorString: Int) {
        Toast.makeText(applicationContext, errorString, Toast.LENGTH_SHORT).show()
    }

}

/**
 * Extension function to simplify setting an afterTextChanged action to EditText components.
 */
fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(editable: Editable?) {
            afterTextChanged.invoke(editable.toString())
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
    })
}
