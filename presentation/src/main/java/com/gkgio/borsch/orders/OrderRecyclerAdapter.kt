package com.gkgio.borsch.orders

import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gkgio.borsch.R
import com.gkgio.borsch.ext.placeholderByDrawable
import com.gkgio.borsch.ext.setDebounceOnClickListener
import com.gkgio.borsch.ext.withCenterCropRoundedCorners
import com.gkgio.borsch.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_order_view_holder.view.*

class OrderRecyclerAdapter(
    private val openDetailClick: (String) -> Unit,
    private val openChatClick: (String) -> Unit
) : RecyclerView.Adapter<SyntheticViewHolder>() {

    private val ordersList = mutableListOf<OrderDataUi>()

    fun setOrdersList(ordersList: List<OrderDataUi>) {
        this.ordersList.clear()
        this.ordersList.addAll(ordersList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        return SyntheticViewHolder.inflateFrom(parent, R.layout.layout_order_view_holder)
    }

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) {
        with(holder.itemView) {
            val orderUi = ordersList[position]

            var mealUrl: String? = null
            var mealName: String? = null

            if (!orderUi.lunches.isNullOrEmpty()) {
                if (orderUi.lunches[0].meals.isNotEmpty()) {
                    mealUrl = orderUi.lunches[0].meals[0].imageUrl
                    mealName = orderUi.lunches[0].meals[0].name
                }
            } else if (!orderUi.meals.isNullOrEmpty()) {
                mealUrl = orderUi.meals[0].imageUrl
                mealName = orderUi.meals[0].name
            }

            if (mealUrl != null) {
                Glide.with(mealIv)
                    .load(mealUrl)
                    .placeholderByDrawable(R.drawable.ic_dish_place_holder)
                    .withCenterCropRoundedCorners(context, 18)
                    .into(mealIv)
            } else {
                mealIv.setImageResource(R.drawable.ic_chef_place_holder)
            }

            mealOrderIdTv.text = context.getString(R.string.order_number_format, orderUi.slug)
            mealNameTv.text = mealName
            mealPriceTv.text = context.getString(R.string.order_price_filter, orderUi.orderPrice)
            orderChatContainer.isVisible =
                orderUi.status == OrderStatus.ACCEPTED.type
                        || orderUi.status == OrderStatus.COOKING.type
                        || orderUi.status == OrderStatus.CREATED.type

            orderChatContainer.setDebounceOnClickListener {
                openChatClick(orderUi.orderId)
            }

            when (orderUi.status) {
                OrderStatus.ACCEPTED.type -> {
                    statusTv.setBackgroundResource(R.drawable.bg_status_green)
                    statusTv.text = OrderStatus.ACCEPTED.value
                }
                OrderStatus.COOKING.type -> {
                    statusTv.setBackgroundResource(R.drawable.bg_status_green)
                    statusTv.text = OrderStatus.COOKING.value
                }
                OrderStatus.CREATED.type -> {
                    statusTv.setBackgroundResource(R.drawable.bg_status_green)
                    statusTv.text = OrderStatus.CREATED.value
                }
                OrderStatus.REJECTED.type -> {
                    statusTv.setBackgroundResource(R.drawable.bg_status_red)
                    statusTv.text = OrderStatus.REJECTED.value
                }
                OrderStatus.CANCELED.type -> {
                    statusTv.setBackgroundResource(R.drawable.bg_status_red)
                    statusTv.text = OrderStatus.CANCELED.value
                }
                OrderStatus.COMPLETED.type -> {
                    statusTv.setBackgroundResource(R.drawable.bg_status_grey)
                    statusTv.text = OrderStatus.COMPLETED.value
                }
            }

            val mealOtherCount = (orderUi.lunches?.size ?: 0) + (orderUi.meals?.size ?: 0) - 1
            if (mealOtherCount > 0) {
                countOtherMealContainer.isInvisible = false
                countOtherMeal.text = String.format("+ %d", mealOtherCount)
            } else {
                countOtherMealContainer.isInvisible = true
            }

            detailOrderBtn.isVisible =
                orderUi.status != OrderStatus.REJECTED.type && orderUi.status != OrderStatus.CANCELED.type

            detailOrderBtn.setDebounceOnClickListener {
                openDetailClick(orderUi.orderId)
            }
        }
    }

    override fun getItemCount(): Int {
        return ordersList.size
    }
}