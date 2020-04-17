package com.gkgio.borsch.support

import androidx.lifecycle.MutableLiveData
import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.ext.isNonInitialized
import javax.inject.Inject

class SupportViewModel  @Inject constructor(
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator) {

    val state = MutableLiveData<State>()

    init {
        if (state.isNonInitialized()) {
            state.value = State(false)
        }
    }

    data class State(
        val isLoading: Boolean,
        val isInitialError: Boolean = false
    )
}