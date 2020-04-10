package com.gkgio.borsch.main

import com.gkgio.borsch.base.BaseScreensNavigator
import com.gkgio.borsch.base.BaseViewModel
import com.gkgio.borsch.utils.SingleLiveEvent
import javax.inject.Inject

class MainViewModel @Inject constructor(
    baseScreensNavigator: BaseScreensNavigator
) : BaseViewModel(baseScreensNavigator){

    val openFirstTab = SingleLiveEvent<Unit>()

    init {
        openFirstTab.call()
    }
}