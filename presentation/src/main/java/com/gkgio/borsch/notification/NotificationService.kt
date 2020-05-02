package com.gkgio.borsch.notification

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Intent
import com.gkgio.borsch.di.AppComponent
import com.gkgio.borsch.di.AppInjector
import com.gkgio.borsch.ext.applySchedulers
import com.gkgio.borsch.main.LaunchActivity
import com.gkgio.domain.auth.AuthUseCase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import timber.log.Timber
import javax.inject.Inject

class NotificationService : FirebaseMessagingService() {

    @Inject
    lateinit var authUseCase: AuthUseCase

    override fun onCreate() {
        super.onCreate()
        AppInjector.appComponent.inject(this)
    }

    @SuppressLint("CheckResult")
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        authUseCase.savePushToken(token)
            .applySchedulers()
            .subscribe({

            }, {
                Timber.e(it)
            })
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        showNotification(
            remoteMessage.notification?.title,
            remoteMessage.notification?.body,
            remoteMessage.data
        )
    }

    private fun showNotification(title: String?, message: String?, dataMap: Map<String, String>?) {
        val notificationId = NotificationID.id
        val intent = Intent(this, LaunchActivity::class.java)

        dataMap?.let {
            intent.putExtra("order_id", it["order_id"])
            intent.putExtra("type", it["type"])
        }

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            notificationId /* Request code */,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )
        val notificationUtils = NotificationUtils(this)
        notificationUtils.buildNotification(title, message, pendingIntent, notificationId)
    }
}