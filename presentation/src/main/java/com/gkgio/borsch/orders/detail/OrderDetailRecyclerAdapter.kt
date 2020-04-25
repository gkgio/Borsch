package com.gkgio.borsch.orders.detail

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gkgio.borsch.R
import com.gkgio.borsch.ext.placeholderByDrawable
import com.gkgio.borsch.ext.setTextOrHide
import com.gkgio.borsch.ext.withCenterCropRoundedCorners
import com.gkgio.borsch.orders.OrderDataUi
import com.gkgio.borsch.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_order_detail_food_view_holder.view.*

class OrderDetailRecyclerAdapter() : RecyclerView.Adapter<SyntheticViewHolder>() {

    private val ordersList = mutableListOf<OrderFoodUi>()

    fun setOrdersList(ordersList: List<OrderFoodUi>) {
        this.ordersList.clear()
        this.ordersList.addAll(ordersList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        return SyntheticViewHolder.inflateFrom(
            parent,
            R.layout.layout_order_detail_food_view_holder
        )
    }

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) {
        with(holder.itemView) {
            val orderDetailFood = ordersList[position]

            Glide.with(mealIv)
                .load(orderDetailFood.imageUrl)
                .placeholderByDrawable(R.drawable.ic_dish_place_holder)
                .withCenterCropRoundedCorners(context, 18)
                .into(mealIv)

            nameTv.text = orderDetailFood.name
            priceTv.text = orderDetailFood.price
            weightTv.setTextOrHide(orderDetailFood.weight)

        }

    }

    override fun getItemCount(): Int {
        return ordersList.size
    }
}