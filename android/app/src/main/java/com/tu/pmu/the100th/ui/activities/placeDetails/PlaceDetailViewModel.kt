package com.tu.pmu.the100th.ui.activities.placeDetails

import androidx.databinding.Bindable
import androidx.lifecycle.MutableLiveData
import com.tu.pmu.the100th.data.network.responces.getAllPlaces.PlaceDTO
import com.tu.pmu.the100th.data.provider.PreferenceProvider
import com.tu.pmu.the100th.data.repo.interfaces.AllPlacesRepository
import com.tu.pmu.the100th.internal.utils.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PlaceDetailViewModel(
    val preferenceProvider: PreferenceProvider,
    val placesRepository: AllPlacesRepository
) : BaseViewModel() {


    var place: PlaceDTO? = null

    val errorEvent : MutableLiveData<String> = MutableLiveData()

    init {
        placesRepository.placeByIdEvent.observeForever {
            place = it
            notifyChange()
        }

        placesRepository.errorEvent.observeForever {
            errorEvent.postValue(it)
        }
    }

    fun getById(id: String) {
        GlobalScope.launch(Dispatchers.IO) {
            placesRepository.getById(id, preferenceProvider.getCurrentPosition())
        }
    }


    val name: String
        @Bindable get() = "" + (place?.name ?: "")


    val description: String
        @Bindable get() ="" + (place?.description ?: "")

    val checked: String
        @Bindable get() = if ((place?.checked == true)
        ) "You already checked here" else "You can check here"

    val distance: String
        @Bindable get() = "" + (place?.distance ?: 0.0) + " km"

    val points: String
        @Bindable get() = "" + (place?.points ?: 0.0)

    val enabled: Boolean
        @Bindable get() = !(place?.checked ?: true)

    fun check() {
        GlobalScope.launch(Dispatchers.IO) {
            place?.id?.let {
                placesRepository.check(it, preferenceProvider.getCurrentPosition())
                getById(it)
            }
        }
    }


}