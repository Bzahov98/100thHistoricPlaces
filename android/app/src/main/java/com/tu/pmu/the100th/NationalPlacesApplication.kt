package com.tu.pmu.the100th

import android.app.Application
import android.content.res.Resources
import androidx.preference.PreferenceManager
import com.tu.pmu.the100th.ui.login.ui.login.LoginViewModelFactory
import com.tu.pmu.the100th.ui.mapAllPlaces.MapAllPlacesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

class NationalPlacesApplication : Application(), KodeinAware{
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@NationalPlacesApplication))

        // bind data sources for api service


        // bind app repository


        // bind all providers


        //bind all fragment's view models
        bind() from provider { MapAllPlacesViewModelFactory()}
        //bind() from provider { LoginViewModelFactory()}

    }

    override fun onCreate() {
        super.onCreate()
       // PreferenceManager.setDefaultValues(this,R.xml.preferences,false)
        resourcesNew = resources

    }
    companion object{
        var resourcesNew: Resources? = null
        fun getAppResources(): Resources? {
            return resourcesNew
        }

        fun getAppString(id: Int): String {
            return resourcesNew!!.getString(id)
        }
    }
}