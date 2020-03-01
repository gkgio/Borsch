package com.gkgio.borsch.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.gkgio.borsch.R
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.observeValue
import com.gkgio.borsch.utils.DialogUtils
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment<VM : BaseViewModel> : Fragment() {

    protected lateinit var viewModel: VM
    protected var compositeDisposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        @Suppress("UNCHECKED_CAST")
        AppInjector.appComponent.inject(this as BaseFragment<BaseViewModel>)

        viewModel = provideViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutId(), container, false)
    }

    protected fun showDialog(dialog: DialogFragment, tag: String) {
        if (isDetached || childFragmentManager.findFragmentByTag(tag)?.isAdded == true) {
            return
        }

        dialog.show(childFragmentManager, tag)
    }

    @LayoutRes
    abstract fun getLayoutId(): Int

    abstract fun provideViewModel(): VM

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }

    private fun subscribeErrorEvents() {
        viewModel.errorEvent.observeValue(this) {
            showError(it ?: getString(R.string.default_error))
        }

        viewModel.noInternetErrorEvent.observeValue(this) {
            showNetworkError(it ?: getString(R.string.default_error))
        }


        viewModel.unsupportedVersionErrorEvent.observeValue(this) {
            showUnsupportedVersionError(it)
        }

        viewModel.googleGmsApiNotConnectErrorEvent.observeValue(this) {
            showError(getString(R.string.api_not_connect_error))
        }
    }

    protected open fun showError(message: String) {
        DialogUtils.showError(
            view,
            message
        )
    }

    protected open fun showNetworkError(message: String) {
        DialogUtils.showError(
            view,
            message
        )
    }

    protected fun showUnsupportedVersionError(message: String) {
        DialogUtils.showError(
            view,
            message,
            getString(R.string.title_redirect),
            View.OnClickListener {
                activity?.packageName?.let { packageName ->
                    viewModel.onMarketClick(packageName)
                }
            }
        )
    }

    open fun onBackClick() {
        viewModel.onBackClick()
    }
}