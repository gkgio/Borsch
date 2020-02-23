package com.gkgio.borsch.main

import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.ViewCompat
import androidx.core.view.forEach
import com.gkgio.borsch.R
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.closeKeyboard
import com.gkgio.borsch.ext.createViewModel
import com.gkgio.borsch.ext.observeValue
import com.gkgio.borsch.navigation.Navigator
import com.gkgio.borsch.utils.ThemeUtils.applyTheme
import kotlinx.android.synthetic.main.activity_launch.*
import ru.terrakok.cicerone.NavigatorHolder
import javax.inject.Inject

class LaunchActivity : AppCompatActivity() {

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val viewModel by lazy { createViewModel { AppInjector.appComponent.launchViewModel } }

    private val navigator = Navigator(this, R.id.containerRoot)

    override fun onCreate(savedInstanceState: Bundle?) {
        AppInjector.appComponent.inject(this)
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_launch)

        ViewCompat.setOnApplyWindowInsetsListener(containerRoot) { view, insets ->
            var consumed = false
            (view as ViewGroup).forEach { child ->
                val childResult = ViewCompat.dispatchApplyWindowInsets(child, insets)
                if (childResult.isConsumed) {
                    consumed = true
                }
            }
            if (consumed) insets.consumeSystemWindowInsets() else insets
        }

        if (savedInstanceState == null) {
            viewModel.onNewStart()
        }
    }

    override fun onResumeFragments() {
        super.onResumeFragments()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            (currentFocus as? EditText)?.let { editText ->
                if (editText.tag == null || editText.tag !is String) {
                    currentFocus?.let { focus ->
                        val outR = Rect()
                        editText.getGlobalVisibleRect(outR)
                        val isKeyboardOpen = !outR.contains(event.rawX.toInt(), event.rawY.toInt())
                        if (isKeyboardOpen) {
                            closeKeyboard()
                            focus.clearFocus()
                        }
                    }
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}
