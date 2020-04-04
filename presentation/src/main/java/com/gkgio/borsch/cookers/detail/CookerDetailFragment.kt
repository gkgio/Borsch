package com.gkgio.borsch.cookers.detail

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gkgio.borsch.R
import com.gkgio.borsch.base.BaseFragment
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.*
import com.gkgio.borsch.profile.SettingsViewModel
import com.gkgio.borsch.utils.FragmentArgumentDelegate
import kotlinx.android.synthetic.main.fragment_cooker_detail.*

class CookerDetailFragment : BaseFragment<CookerDetailViewModel>() {

    companion object {
        fun newInstance(cookerId: String) = CookerDetailFragment().apply {
            this.cookerId = cookerId
        }
    }

    private var cookerId: String by FragmentArgumentDelegate()

    private lateinit var pagerAdapter: CookerDetailPagerAdapter

    override fun getLayoutId(): Int = R.layout.fragment_cooker_detail

    override fun provideViewModel() = createViewModel {
        AppInjector.appComponent.cookerDetailViewModel
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init(cookerId)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.setLeftIconClickListener {
            viewModel.onBackClick()
        }

        viewModel.state.observeValue(this) { state ->
            state.cookerDetail?.let { cooker ->
                toolbar.setTitle(cooker.name)
                initViewPager(cooker)

                Glide.with(cookerAvatarIv)
                    .load(cooker.avatarUrl)
                    .withFade()
                    .withCenterCropOval()
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

            }
        }
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