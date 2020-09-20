package com.tu.pmu.the100th

import android.app.Application
import android.content.res.Resources
import com.tu.pmu.the100th.data.db.PlacesDatabase
import com.tu.pmu.the100th.data.network.dataSource.AuthLoginNetworkDataSourceImpl
import com.tu.pmu.the100th.data.network.dataSource.AuthSignupNetworkDataSourceImpl
import com.tu.pmu.the100th.data.network.dataSource.interfaces.AuthLoginNetworkDataSource
import com.tu.pmu.the100th.data.network.dataSource.interfaces.AuthSignupNetworkDataSource
import com.tu.pmu.the100th.data.network.interceptors.AuthenticationInterceptor
import com.tu.pmu.the100th.data.network.interceptors.NetworkConnectionInterceptor
import com.tu.pmu.the100th.data.services.PlacesApiService
import com.tu.pmu.the100th.data.repo.UserRepositoryImpl
import com.tu.pmu.the100th.data.repo.interfaces.UserRepository
import com.tu.pmu.the100th.ui.authActivities.AuthViewModelFactory
import com.tu.pmu.the100th.ui.fragments.profile.ProfileViewModelFactory
import com.tu.pmu.the100th.ui.mapAllPlaces.MapAllPlacesViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class NationalPlacesApplication : Application(), KodeinAware {
    override val kodein: Kodein = Kodein.lazy {
        import(androidXModule(this@NationalPlacesApplication))
        // Database and Dao's
        bind() from singleton { instance<PlacesDatabase>().getUserDao() }
        bind() from singleton { PlacesDatabase(instance()) }

        // Network Interceptors
        bind() from singleton { NetworkConnectionInterceptor(instance()) }
        bind() from singleton { AuthenticationInterceptor() }

        // bind data sources for api service
        bind() from singleton { PlacesApiService(instance(),instance()) }
        // bind different data sources for each api service
        bind<AuthLoginNetworkDataSource>() with singleton {
            AuthLoginNetworkDataSourceImpl(
                instance()
            )
        }
        bind<AuthSignupNetworkDataSource>() with singleton {
            AuthSignupNetworkDataSourceImpl(
                instance()
            )
        }

        // bind app repository
        bind<UserRepository>() with singleton { UserRepositoryImpl(instance(),instance(),instance()) }


        // bind all providers
        // bind() from singleton { PreferenceProvider(instance()) }
        //bind() from singleton { QuotesRepository(instance(), instance(), instance()) }


        //bind all fragment's view models
        bind() from provider { MapAllPlacesViewModelFactory() }

        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        //bind() from provider { LoginViewModelFactory()}

    }

    override fun onCreate() {
        super.onCreate()
        // PreferenceManager.setDefaultValues(this,R.xml.preferences,false)
        resourcesNew = resources

    }

    companion object {
        var resourcesNew: Resources? = null
        fun getAppResources(): Resources? {
            return resourcesNew
        }

        fun getAppString(id: Int): String {
            return resourcesNew!!.getString(id)
        }
    }
}