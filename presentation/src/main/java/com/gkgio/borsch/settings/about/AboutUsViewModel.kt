package com.gkgio.borsch.settings.about

import com.gkgio.borsch.base.BaseViewModel
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class AboutUsViewModel @Inject constructor(
    private val router: Router
) : BaseViewModel() {
    fun onBackClick() {
        router.exit()
    }
}