package com.gkgio.borsch.cookers.detail

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.cookers.detail.food.FoodItemDialogListener
import com.gkgio.borsch.cookers.detail.food.FoodItemFragment
import com.gkgio.borsch.cookers.detail.meals.CookerMealClickListener
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.*
import com.gkgio.borsch.profile.SettingsViewModel
import com.gkgio.borsch.utils.FragmentArgumentDelegate
import com.gkgio.borsch.utils.FragmentNullableArgumentDelegate
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.android.synthetic.main.basket_view.*
import kotlinx.android.synthetic.main.empty_error_view.*
import kotlinx.android.synthetic.main.fragment_cooker_detail.*

class CookerDetailFragment : BaseFragment<CookerDetailViewModel>(), FoodItemDialogListener,
    CookerMealClickListener {

    companion object {
        private const val MAX_SCHEDULE_SHEET_ALPHA = 0.5f

        fun newInstance(
            cookerId: String,
            foodId: String?,
            type: Int?,
            cookerAddressUi: CookerAddressUi?
        ) = CookerDetailFragment().apply {
            this.cookerId = cookerId
            this.foodId = foodId
            this.type = type
            this.cookerAddressUi = cookerAddressUi
        }
    }

    private var cookerId: String by FragmentArgumentDelegate()
    private var foodId: String? by FragmentNullableArgumentDelegate()
    private var type: Int? by FragmentNullableArgumentDelegate()
    private var cookerAddressUi: CookerAddressUi? by FragmentNullableArgumentDelegate()

    private lateinit var pagerAdapter: CookerDetailPagerAdapter
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<*>

    override fun getLayoutId(): Int = R.layout.fragment_cooker_detail

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.cookerDetailViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(cookerId, foodId, type, cookerAddressUi)
    }

    override fun onResume() {
        super.onResume()
        setupAlphaOnRestoreState()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setLeftIconClickListener {
            viewModel.onBackClick()
        }

        viewModel.state.observeValue(this) { state ->
            progress.isVisible = state.isLoading
            emptyErrorView.isVisible = state.isInitialError

            state.cookerDetail?.let { cooker ->
                toolbar.setTitle(cooker.name)
                initViewPager(cooker)

                Glide.with(cookerAvatarIv)
                    .load(cooker.avatarUrl)
                    .withFade()
                    .withCenterCropOval()
                    .placeholder(R.drawable.ic_chef_place_holder)
                    .apply(RequestOptions.circleCropTransform())
                    .into(cookerAvatarIv)

                if (cooker.onDuty == true) {
                    isOnlineIv.setImageResource(R.drawable.ic_online)
                    isOnlineTv.text = getString(R.string.cooker_is_online)
                    isOnlineTv.setTextColor(requireContext().getColorCompat(R.color.green))
                } else {
                    isOnlineIv.setImageResource(R.drawable.ic_not_online)
                    isOnlineTv.text = getString(R.string.cooker_is_not_online)
                    isOnlineTv.setTextColor(requireContext().getColorCompat(R.color.accent))
                }

                ratingTv.text = cooker.rating
                deliveryContainer.isVisible = cooker.delivery
                cookerAddressContainer.isVisible = cooker.cookerAddress?.street != null
                cookerAddressTv.text =
                    String.format(
                        "%s, %s",
                        cooker.cookerAddress?.street,
                        cooker.cookerAddress?.house
                    )
            }

            basketView.isVisible = state.basketCountAndSum != null
            state.basketCountAndSum?.let {
                countTv.text = it.count.toString()
                sumTv.text = it.sum
            }
        }

        viewModel.openFoodItem.observeValue(this) {
            addFragmentToContainerBottomSheet(FoodItemFragment.newInstance(it))
        }

        updateEmptyBtn.setDebounceOnClickListener {
            viewModel.loadData(cookerId, foodId, type, cookerAddressUi)
        }

        bottomSheetBehavior = BottomSheetBehavior.from(view.findViewById<View>(R.id.foodSheet))
        bottomSheetBehavior.setBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(p0: View, slide: Float) {
                alphaView.alpha = slide / 2
            }

            override fun onStateChanged(p0: View, state: Int) {
                alphaView.isVisible = state != BottomSheetBehavior.STATE_COLLAPSED
            }
        })

        coordinatorCookerDetail.doOnPreDraw {
            foodSheet.layoutParams.height =
                coordinatorCookerDetail.height - (toolbar.height * 1.4).toInt()
        }

        setupLockListeners()
    }

    override fun onBackClick() {
        when {
            bottomSheetBehavior.state != BottomSheetBehavior.STATE_COLLAPSED -> {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            else -> {
                super.onBackClick()
            }
        }
    }

    override fun onCollapseClick() = onBackClick()

    override fun onMealClick(id: String, type: Int) {
        viewModel.onMealClick(id, type)
    }

    private fun setupAlphaOnRestoreState() {
        if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED
            && resources.configuration.orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        ) {
            alphaView.alpha = MAX_SCHEDULE_SHEET_ALPHA
            alphaView.isVisible = true
        }
    }

    private fun setupLockListeners() {

        alphaView.setOnTouchListener { _, _ ->
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            bottomSheetBehavior.state != BottomSheetBehavior.STATE_COLLAPSED
        }
    }

    private fun addFragmentToContainerBottomSheet(fragment: Fragment) {
        val fm = childFragmentManager.beginTransaction()
        with(fm) {
            replace(
                R.id.sheetContainer,
                fragment,
                fragment::class.java.name
            )
            commitNowAllowingStateLoss()
        }
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun initViewPager(cookerDetailUi: CookerDetailUi) {
        pagerAdapter = CookerDetailPagerAdapter(
            cookerDetailUi,
            requireContext(),
            childFragmentManager
        )
        cookerViewPager.adapter = pagerAdapter
        cookerTabs.setupWithViewPager(cookerViewPager)
    }
}