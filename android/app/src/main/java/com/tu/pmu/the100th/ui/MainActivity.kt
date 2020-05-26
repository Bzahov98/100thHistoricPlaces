package com.tu.pmu.the100th.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.ui.login.ui.login.LoginActivity
import org.kodein.di.KodeinAware
import org.kodein.di.android.closestKodein

class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein by closestKodein()
    lateinit var currentUser : String
    companion object{
        const val LOGIN_ACTIVITY_REQUEST = 1
        const val LOGIN_ACTIVITY_SUCCESS = 2
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


// To pass any data to next activity
        //intent.putExtra("keyIdentifier", value)
// start your next activity
        startLoginActivity()
    }

    private fun startLoginActivity() {
        val intent = Intent(this, LoginActivity::class.java)
        startActivityForResult(intent, LOGIN_ACTIVITY_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == LOGIN_ACTIVITY_REQUEST){
            if(resultCode == LOGIN_ACTIVITY_SUCCESS){
                if(data == null){
                    Toast.makeText(this,"Data is null", Toast.LENGTH_SHORT).show()
                    startLoginActivity()
                    return
                }
                val username = data.getStringExtra("username")
                Toast.makeText(this,"Logged user is: $username ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
