package com.gkgio.borsch.location.saved

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.gkgio.borsch.R
import com.gkgio.borsch.ext.setDebounceOnClickListener
import com.gkgio.borsch.view.SyntheticViewHolder
import kotlinx.android.synthetic.main.layout_saved_addresses_view_holder.view.*


class SavedAddressesRecyclerAdapter(
    val itemClick: (AddressUi) -> Unit
) : RecyclerView.Adapter<SyntheticViewHolder>() {

    private val addressesList = mutableListOf<AddressUi>()

    fun setAddressesList(addressesList: List<AddressUi>) {
        this.addressesList.clear()
        this.addressesList.addAll(addressesList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyntheticViewHolder {
        return SyntheticViewHolder.inflateFrom(parent, R.layout.layout_saved_addresses_view_holder)
    }

    override fun onBindViewHolder(holder: SyntheticViewHolder, position: Int) =
        with(holder.itemView) {
            val address = addressesList[position]

            tvAddress.text =
                String.format(
                    "%s, %s, %s",
                    address.address.city,
                    address.address.street,
                    address.address.house
                )
            icCheck.setBackgroundResource(if (address.isSelected) R.drawable.ic_checked else R.drawable.ic_unchecked)

            setDebounceOnClickListener {
                itemClick(address)
            }

        }

    override fun getItemCount(): Int {
        return addressesList.size
    }
}