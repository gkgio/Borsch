package com.gkgio.data

import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import com.gkgio.domain.auth.AuthRepository
import okhttp3.Interceptor
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeviceInterceptor @Inject constructor(
    private val context: Context,
    private val authRepository: AuthRepository
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Фикс некорректной кодировки ретрофит и окхттп https://github.com/square/retrofit/issues/1199
        val newUrl = originalRequest.url.toString()
            .replace("%26", "&")
            .replace("%3D", "=")

        val requestBuilder = originalRequest.newBuilder()
            .url(newUrl)
            .addHeader("Accept", "application/json")
            .addHeader("Content-Type", "application/json")
            .addHeader("User-Agent", getUserAgent())
            .method(originalRequest.method, originalRequest.body)

        val authToken = authRepository.getAuthToken()
        if (!authToken.isNullOrEmpty()) {
            requestBuilder.addHeader("authorization", authToken)
        }

        return chain.proceed(requestBuilder.build())
    }

    private fun getUserAgent(): String {
        return "Borsch-client" +
                getVersionName() +
                " (Android ${Build.VERSION.RELEASE}" +
                "; Scale/${context.resources.displayMetrics.density})"
    }

    private fun getVersionName(): String {
        try {
            val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
            return packageInfo.versionName
        } catch (e: PackageManager.NameNotFoundException) {
            Timber.e(e, "Error get version name")
        }
        return "1.0"
    }
}