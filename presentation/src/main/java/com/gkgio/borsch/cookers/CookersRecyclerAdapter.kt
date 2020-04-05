package com.gkgio.borsch.cookers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gkgio.borsch.R
import com.gkgio.borsch.ext.dpToPx
import com.gkgio.borsch.ext.setDebounceOnClickListener
import com.gkgio.borsch.ext.withCenterCropOval
import com.gkgio.borsch.ext.withFade
import com.gkgio.borsch.location.saved.AddressUi
import com.gkgio.borsch.view.SyntheticViewHolder
import com.gkgio.domain.cookers.Cooker
import kotlinx.android.synthetic.main.layout_cooker_view_holder.view.*

class CookersRecyclerAdapter(
    val cookerClick: (String) -> Unit,
    val mealClick: (String, String, Int) -> Unit
) : RecyclerView.Adapter<SyntheticViewHolder>() {

    companion object {
        private const val COOKERS_TAG_MARGIN_DP = 8
    }

    private val cookersList = mutableListOf<Cooker>()

    fun setCookersList(cookersList: List<Cooker>) {
        this.cookersList.clear()
        this.cookersList.addAll(cookersList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        return SyntheticViewHolder.inflateFrom(parent, R.layout.layout_cooker_view_holder)
    }

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) =
        with(holder.itemView) {
            val cooker = cookersList[position]

            val layoutInflater = LayoutInflater.from(context)
            val tagMargins = context.dpToPx(COOKERS_TAG_MARGIN_DP)

            if (!cooker.countryTags.isNullOrEmpty()) {
                cooker.countryTags?.forEach { tag ->
                    tagsFlexBox.addView(
                        createTagView(
                            layoutInflater,
                            tag,
                            tagsFlexBox,
                            tagMargins,
                            tagMargins
                        )
                    )
                }
            }

            cookerNameTv.text = cooker.name
            cookerDescriptionTv.text = cooker.description
            rankingTv.text = cooker.rating
            deliveryContainer.isVisible = cooker.delivery

            Glide.with(cookerAvatarIv)
                .load(cooker.avatarUrl)
                .withFade()
                .withCenterCropOval()
                .placeholder(R.drawable.ic_chef_place_holder)
                .apply(RequestOptions.circleCropTransform())
                .into(cookerAvatarIv)

            val mealsRecyclerAdapter =
                MealsRecyclerAdapter(cooker.meals, cooker.lunches ?: listOf()) { id, type ->
                    mealClick(cooker.id, id, type)
                }
            mealsRv.adapter = mealsRecyclerAdapter
            mealsRv.layoutManager =
                LinearLayoutManager(
                    context,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )

            setDebounceOnClickListener {
                cookerClick(cooker.id)
            }

        }

    override fun getItemCount(): Int {
        return cookersList.size
    }

    private fun createTagView(
        layoutInflater: LayoutInflater,
        tagText: String,
        container: ViewGroup,
        marginTop: Int,
        marginRight: Int
    ): View {
        val view = layoutInflater.inflate(R.layout.layout_cookers_tag, container, false) as TextView
        return view.apply {
            text = tagText

            val lp = layoutParams as ViewGroup.MarginLayoutParams
            lp.topMargin = marginTop
            lp.rightMargin = marginRight
            layoutParams = lp
        }
    }
}