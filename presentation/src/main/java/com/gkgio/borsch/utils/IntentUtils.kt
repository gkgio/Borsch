package com.gkgio.borsch.utils

import android.content.Intent
import android.net.Uri
import com.gkgio.domain.location.Coordinates

object IntentUtils {

    fun createPhoneIntent(phone: String) = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("tel:$phone")
    )

    fun createEmailIntent(email: String) = Intent(
        Intent.ACTION_SENDTO
    ).apply {
        data = Uri.parse("mailto:$email")
        putExtra(Intent.EXTRA_EMAIL, email)
    }

    fun createMarketIntent(appPackageName: String) =
        Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName"))

    fun createRouteIntent(coordinates: Coordinates): Intent = Intent(
        Intent.ACTION_VIEW,
        Uri.parse("geo:${coordinates.latitude},${coordinates.longitude}?q=${Uri.encode("${coordinates.latitude},${coordinates.longitude}")}")
    )

    fun createWebUrlIntent(url: String) =
        Intent(Intent.ACTION_VIEW, Uri.parse(url))
}
