package com.tu.pmu.the100th.ui.fragments.allPlacesList.recyclerview

import android.view.View
import com.tu.pmu.the100th.R
import com.tu.pmu.the100th.data.network.responces.getAllPlaces.PlaceDTO
import com.xwray.groupie.kotlinandroidextensions.Item
import com.xwray.groupie.kotlinandroidextensions.ViewHolder
import kotlinx.android.synthetic.main.item_all_places.view.*

class PlacesListItem(
    val data: PlaceDTO
) : Item() {
    override fun getLayout() = R.layout.item_all_places
    override fun bind(viewHolder: ViewHolder, position: Int) {
        viewHolder.apply {
            val view = viewHolder.itemView
            updateViewData(view)
        }
    }

    private fun updateViewData(view: View) {
        view.placesItemDescription.text = data.description
        view.placesItemDistanceToText.text = "${data.distance} km"
        view.placesItemIsVisitedText.text = if(data.checked) "Already visited place" else "Currently not visited"
        view.placesItemPlaceName.text = data.name
        view.placesItemPointsText.text = data.points.toString()
    }

    companion object{}
}
