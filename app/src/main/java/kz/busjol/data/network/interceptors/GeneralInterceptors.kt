package kz.busjol.data.network.interceptors

import kz.busjol.BuildConfig
import kz.busjol.preferences.UserPreferences
import okhttp3.Interceptor
import okhttp3.Response

class GeneralInterceptors(
    val userPreferences: UserPreferences
): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("X-Language", userPreferences.getAppLanguage.toString())
            .addHeader("X-App-Version", BuildConfig.VERSION_NAME)
            .addHeader("Client-Type", "ANDROID")
            .addHeader("Content-Type", "application/json")

        return chain.proceed(builder.build())
    }

}
