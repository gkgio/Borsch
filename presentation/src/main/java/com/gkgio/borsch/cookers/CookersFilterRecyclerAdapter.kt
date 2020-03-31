package com.gkgio.borsch.cookers

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gkgio.borsch.R
import com.gkgio.borsch.ext.setDebounceOnClickListener
import com.gkgio.borsch.view.SyntheticViewHolder
import com.gkgio.domain.address.GeoSuggestion
import kotlinx.android.synthetic.main.layout_geo_suggestion_view_holder.view.*

class CookersFilterRecyclerAdapter(
    val itemClick: (GeoSuggestion) -> Unit
) : RecyclerView.Adapter<SyntheticViewHolder>() {

    private val geoSuggestionList = mutableListOf<GeoSuggestion>()

    fun setGeoSuggestionList(geoSuggestionList: List<GeoSuggestion>) {
        this.geoSuggestionList.clear()
        this.geoSuggestionList.addAll(geoSuggestionList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        return SyntheticViewHolder.inflateFrom(parent, R.layout.layout_geo_suggestion_view_holder)
    }

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) =
        with(holder.itemView) {
            val geoSuggestion = geoSuggestionList[position]

            addressTv.text = geoSuggestion.value

            setDebounceOnClickListener {
                itemClick(geoSuggestion)
            }

        }

    override fun getItemCount(): Int {
        return geoSuggestionList.size
    }
}