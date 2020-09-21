package com.tu.pmu.the100th.internal.utils.intentUtils

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import com.tu.pmu.the100th.ui.authActivities.login.LoginActivity
import com.tu.pmu.the100th.ui.mainActivity.MainActivity

fun startMainActivity(context: Context) {
    Intent(context, MainActivity::class.java).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(context,it,it.extras)
        //finish()
    }
}

fun startLoginActivity(context: Context, emailHint : String = "") {
    Intent(context, LoginActivity::class.java).also {
        val a = it.putExtra("email",emailHint)
        //it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(context,it,a.extras)
        //finish()
    }
}
