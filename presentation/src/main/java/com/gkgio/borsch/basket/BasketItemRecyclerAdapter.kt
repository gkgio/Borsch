package com.gkgio.borsch.basket

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gkgio.borsch.R
import com.gkgio.borsch.ext.placeholderByDrawable
import com.gkgio.borsch.ext.setDebounceOnClickListener
import com.gkgio.borsch.ext.withCenterCropRoundedCorners
import com.gkgio.borsch.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_basket_data_view_holder.view.*

class BasketItemRecyclerAdapter(
    private val plusClick: (BasketDataUi, Int) -> Unit,
    private val minusClick: (BasketDataUi, Int) -> Unit
) : RecyclerView.Adapter<SyntheticViewHolder>() {

    private val basketDataList = mutableListOf<BasketDataUi>()

    fun setBasketDataList(basketDataList: List<BasketDataUi>) {
        this.basketDataList.clear()
        this.basketDataList.addAll(basketDataList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        return SyntheticViewHolder.inflateFrom(parent, R.layout.layout_basket_data_view_holder)
    }

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) =
        with(holder.itemView) {
            val basketData = basketDataList[position]
            nameTv.text = basketData.name
            priceTv.text = basketData.price
            countTv.text = basketData.count.toString()

            removeBtn.setDebounceOnClickListener {
                minusClick.invoke(basketData, position)
            }

            Glide.with(ivDishBasket)
                .load(basketData.imageUrl)
                .placeholderByDrawable(R.drawable.ic_dish_place_holder)
                .withCenterCropRoundedCorners(context, 18)
                .into(ivDishBasket)

            addBtn.setDebounceOnClickListener {
                plusClick.invoke(basketData, position)
            }
        }

    override fun getItemCount(): Int {
        return basketDataList.size
    }

    fun updateItem(position: Int, basketDataUi: BasketDataUi) {
        basketDataList[position] = basketDataUi
        notifyItemChanged(position)
    }
}