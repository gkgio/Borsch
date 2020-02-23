package com.gkgio.borsch.utils

import android.content.Context
import android.text.format.DateFormat
import android.view.View
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.TranslateAnimation
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import com.gkgio.borsch.R
import org.joda.time.DateTime
import java.text.SimpleDateFormat
import java.util.*

fun Fragment?.closeKeyboard() {
    val view = this?.activity?.currentFocus
    if (view != null) {
        val imm =
            this?.activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
        imm?.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
    }
}

/**
 * @return dd month yyyy / dd month / СЕГОДНЯ
 */
fun getDateInStandardFormat(
    context: Context,
    date: Date,
    isClassicFormat: Boolean = true,
    withTime: Boolean = false
): String {
    val currentDate = DateTime.now().withTimeAtStartOfDay()

    val currentYear = SimpleDateFormat("yyyy", Locale("ru"))
        .format(currentDate.toDate())
    val checkingYear = DateFormat.format("yyyy", date) as String

    return if (date == currentDate.toDate() && !isClassicFormat) {
        "СЕГОДНЯ"
    } else if (currentYear == checkingYear && !isClassicFormat) {
        if (withTime) {
            dateToUIStringMonthWordAndTime(context, date)
        } else {
            dateToUIStringMonthWord(context, date)
        }
    } else {
        if (withTime) {
            dateToUIStringMonthWordAndYearAndTime(context, date)
        } else {
            dateToUIStringMonthWordAndYear(context, date)
        }
    }
}

/**
 * @return dd month
 */
fun dateToUIStringMonthWord(context: Context, date: Date): String {
    return String.format(
        "%s %s",
        DateFormat.format("dd", date),
        getMonthNameByMonthNumber(context, DateFormat.format("MM", date) as String)
    )
}

/**
 * @return dd month - HH:mm
 */
fun dateToUIStringMonthWordAndTime(context: Context, date: Date): String {
    return String.format(
        "%s %s - %s",
        DateFormat.format("dd", date),
        getMonthNameByMonthNumber(context, DateFormat.format("MM", date) as String),
        DateFormat.format("HH:mm", date)
    )
}

/**
 * @return dd month yyyy
 */
fun dateToUIStringMonthWordAndYear(context: Context, date: Date): String {
    return String.format(
        "%s %s %s",
        DateFormat.format("dd", date),
        getMonthNameByMonthNumber(context, DateFormat.format("MM", date) as String),
        DateFormat.format("yyyy", date)
    )
}

/**
 * @return dd month yyyy - HH:mm
 */
fun dateToUIStringMonthWordAndYearAndTime(context: Context, date: Date): String {
    return String.format(
        "%s %s %s - %s",
        DateFormat.format("dd", date),
        getMonthNameByMonthNumber(context, DateFormat.format("MM", date) as String),
        DateFormat.format("yyyy", date),
        DateFormat.format("HH:mm", date)
    )
}

fun getMonthNameByMonthNumber(context: Context, monthNumber: String): String {
    val months = context.resources.getStringArray(R.array.months_s)
    return when (monthNumber) {
        "01" -> months[0].toUpperCase(Locale.getDefault())
        "02" -> months[1].toUpperCase(Locale.getDefault())
        "03" -> months[2].toUpperCase(Locale.getDefault())
        "04" -> months[3].toUpperCase(Locale.getDefault())
        "05" -> months[4].toUpperCase(Locale.getDefault())
        "06" -> months[5].toUpperCase(Locale.getDefault())
        "07" -> months[6].toUpperCase(Locale.getDefault())
        "08" -> months[7].toUpperCase(Locale.getDefault())
        "09" -> months[8].toUpperCase(Locale.getDefault())
        "10" -> months[9].toUpperCase(Locale.getDefault())
        "11" -> months[10].toUpperCase(Locale.getDefault())
        "12" -> months[11].toUpperCase(Locale.getDefault())
        else -> ""
    }
}

fun endlessJumpingViewAnimation(view: View) {
    val animation = TranslateAnimation(
        TranslateAnimation.ABSOLUTE, 0f,
        TranslateAnimation.ABSOLUTE, 0f,
        TranslateAnimation.RELATIVE_TO_PARENT, -0.02f,
        TranslateAnimation.RELATIVE_TO_PARENT, 0.02f
    )
    animation.duration = 3000
    animation.repeatCount = -1
    animation.repeatMode = Animation.REVERSE
    animation.interpolator = LinearInterpolator()
    view.startAnimation(animation)
}