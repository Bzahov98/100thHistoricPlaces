package com.tu.pmu.the100th.ui.MainActivity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.navigation.ui.NavigationUI
import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.data.db.entities.auth.User
import kotlinx.android.synthetic.main.activity_main.*
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein

class MainActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    var currentUser: User? = null

    companion object {
        const val LOGIN_ACTIVITY_REQUEST = 1
        const val LOGIN_ACTIVITY_SUCCESS = 2
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)

        val navController = Navigation.findNavController(this, R.id.fragment)
        NavigationUI.setupWithNavController(nav_view, navController)
        NavigationUI.setupActionBarWithNavController(this, navController, drawer_layout)
    }


    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(
            Navigation.findNavController(this, R.id.fragment),
            drawer_layout
        )
    }
//    private fun startLoginActivity() {
//        val intent = Intent(this, LoginActivity::class.java)
//        startActivityForResult(intent, LOGIN_ACTIVITY_REQUEST)
//    }
//
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == LOGIN_ACTIVITY_REQUEST) {
//            if (resultCode == LOGIN_ACTIVITY_SUCCESS) {
//                if (data == null) {
//                    Toast.makeText(this, "Data is null", Toast.LENGTH_SHORT).show()
//                    startLoginActivity()
//                    return
//                }
//                val username = data.getStringExtra("username")
//                val email = data.getStringExtra("email")
//                Toast.makeText(this, "Logged user is: $username $email", Toast.LENGTH_SHORT).show()
//                currentUser = User(0,username,email)
//            }
//        }
//    }
}
