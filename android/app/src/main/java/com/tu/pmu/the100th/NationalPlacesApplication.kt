package com.tu.pmu.the100th

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.google.android.gms.location.LocationServices
import com.tu.pmu.the100th.data.db.PlacesDatabase
import com.tu.pmu.the100th.data.network.dataSource.AuthLoginNetworkDataSourceImpl
import com.tu.pmu.the100th.data.network.dataSource.AuthSignupNetworkDataSourceImpl
import com.tu.pmu.the100th.data.network.dataSource.GetAllPlacesNetworkDataSourceImpl
import com.tu.pmu.the100th.data.network.dataSource.interfaces.AuthLoginNetworkDataSource
import com.tu.pmu.the100th.data.network.dataSource.interfaces.AuthSignupNetworkDataSource
import com.tu.pmu.the100th.data.network.dataSource.interfaces.GetAllPlacesNetworkDataSource
import com.tu.pmu.the100th.data.network.interceptors.BasicAuthenticationInterceptor
import com.tu.pmu.the100th.data.network.interceptors.NetworkConnectionInterceptor
import com.tu.pmu.the100th.data.network.interceptors.TokenAuthenticationInterceptor
import com.tu.pmu.the100th.data.provider.interfaces.InternetProvider
import com.tu.pmu.the100th.data.provider.InternetProviderImpl
import com.tu.pmu.the100th.data.provider.interfaces.LocationProvider
import com.tu.pmu.the100th.data.provider.LocationProviderImpl
import com.tu.pmu.the100th.data.provider.PreferenceProvider
import com.tu.pmu.the100th.data.repo.AllPlacesRepositoryImpl
import com.tu.pmu.the100th.data.services.AuthApiService
import com.tu.pmu.the100th.data.repo.UserRepositoryImpl
import com.tu.pmu.the100th.data.repo.interfaces.AllPlacesRepository
import com.tu.pmu.the100th.data.repo.interfaces.UserRepository
import com.tu.pmu.the100th.data.services.PlacesApiService
import com.tu.pmu.the100th.ui.activities.authActivities.AuthViewModelFactory
import com.tu.pmu.the100th.ui.fragments.allPlaces.AllPlacesMapFragmentViewModelFactory
import com.tu.pmu.the100th.ui.fragments.allPlacesList.AllPlacesListFragmentViewModelFactory
import com.tu.pmu.the100th.ui.fragments.profile.ProfileViewModelFactory
import com.tu.pmu.the100th.ui.activities.placeDetails.PlaceDetailViewModelFactory
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
        bind() from singleton { BasicAuthenticationInterceptor() }
        bind() from singleton { TokenAuthenticationInterceptor(instance()) }

        // bind all api services
        bind() from singleton { AuthApiService(instance(), instance()) }
        bind() from singleton { PlacesApiService(instance(), instance()) }

        // bind different data sources for each api service
        bind<AuthLoginNetworkDataSource>() with singleton {
            AuthLoginNetworkDataSourceImpl(
                instance(),instance()
            )
        }
        bind<AuthSignupNetworkDataSource>() with singleton {
            AuthSignupNetworkDataSourceImpl(
                instance()
            )
        }
        bind<GetAllPlacesNetworkDataSource>() with singleton {
            GetAllPlacesNetworkDataSourceImpl(
                instance()
            )
        }

        // bind app repository
        bind<UserRepository>() with singleton {
            UserRepositoryImpl(
                instance(),
                instance(),
                instance(),
                instance(),
                instance()
            )
        }
        bind<AllPlacesRepository>() with singleton {
            AllPlacesRepositoryImpl(
                instance(),
                instance()/*,
                instance(),
                instance()*/
            )
        }


        // bind all providers
        bind() from singleton { PreferenceProvider(instance()) }
        bind<LocationProvider>() with singleton { LocationProviderImpl(instance(), instance()) }
        bind<InternetProvider>() with singleton { InternetProviderImpl(instance()) }
        //bind location providers
        bind() from provider { LocationServices.getFusedLocationProviderClient(instance<Context>()) }

        //bind all fragment's view models

        bind() from provider { AuthViewModelFactory(instance()) }
        bind() from provider { ProfileViewModelFactory(instance()) }
        bind() from provider { AllPlacesMapFragmentViewModelFactory(instance(),instance(),instance(),instance()) }
        bind() from provider { AllPlacesListFragmentViewModelFactory(instance(),instance(),instance(),instance()) }
        bind() from provider { PlaceDetailViewModelFactory(instance(),instance()) }
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