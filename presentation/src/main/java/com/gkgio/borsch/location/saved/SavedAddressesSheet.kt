package com.gkgio.borsch.location.saved

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.gkgio.borsch.R
import com.gkgio.borsch.base.bottomsheet.BaseBottomSheetDialog
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.createViewModel
import com.gkgio.borsch.ext.observeValue
import com.gkgio.borsch.ext.setDebounceOnClickListener
import com.gkgio.borsch.view.RecyclerViewVerticalLineDivider
import kotlinx.android.synthetic.main.layout_saved_addresses_sheet.*
import kotlinx.android.synthetic.main.layout_saved_addresses_sheet.view.*

class SavedAddressesSheet : BaseBottomSheetDialog() {

    private var savedAddressesRecyclerAdapter: SavedAddressesRecyclerAdapter? = null

    private lateinit var viewModel: SavedAddressesViewModel

    override fun getLayoutId(): Int = R.layout.layout_saved_addresses_sheet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = createViewModel { AppInjector.appComponent.savedAddressesViewModel }

        viewModel.state.observeValue(this) { state ->
            progress.isVisible = state.isProgress

            state.addressesList?.let { it ->
                savedAddressesRecyclerAdapter?.setAddressesList(it)
            }
        }
        viewModel.dialogDismiss.observeValue(this) {
            dismiss()
        }
    }

    override fun setupView(view: View) = with(view) {
        super.setupView(view)

        initAddressesRv(view)

        addNewAddressTv.setDebounceOnClickListener {
            viewModel.onAddNewAddressClick()
        }

        btnConfirm.setDebounceOnClickListener {
            viewModel.onConfirmBtnClick()
        }
    }

    private fun initAddressesRv(view: View) {
        savedAddressesRecyclerAdapter = SavedAddressesRecyclerAdapter {
            viewModel.onAddressClick(it)
        }
        view.addressesRv.layoutManager = LinearLayoutManager(context)
        view.addressesRv.adapter = savedAddressesRecyclerAdapter
    }
}