package com.gkgio.borsch.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.gkgio.borsch.di.AppInjector
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
}