package kz.busjol.module

import android.content.Context
import androidx.viewbinding.BuildConfig
import kz.busjol.BuildConfig.VERSION_NAME
import kz.busjol.Consts.Companion.NETWORK_TIMEOUT_IN_SECONDS
import kz.busjol.api.CitySelectorAPI
import kz.busjol.data.UserScope
import kz.busjol.preferences.UserPreferences
import kz.busjol.utils.DeviceUtils
import kz.busjol.utils.NetworkHelper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModule = module {
    single { GsonConverterFactory.create(get()) }
    single { provideRetrofit(get(), BASE_URL = "http://45.76.87.246/api/") }
    single { provideNetworkHelper(androidContext()) }
    single { provideInterceptor(get()) }
    single { provideHttpClientBuilder(interceptor = get()) }
    single { provideHttpClient(get()) }
    single { get<Retrofit>().create(CitySelectorAPI::class.java) }
}

private fun provideNetworkHelper(context: Context) = NetworkHelper(context)

fun provideInterceptor(userPreferences: UserPreferences): Interceptor {
    return Interceptor { chain ->
        val builder = chain.request().newBuilder()
        builder.addHeader("X-Language", userPreferences.getAppLanguage.toString())
            .addHeader("X-Device-Info", DeviceUtils.getDeviceDescription())
            .addHeader("Client-Type", "ANDROID")
            .addHeader("Content-Type", "application/json")

        chain.proceed(builder.build())
    }
}

fun provideHttpClient(httpClientBuilder: OkHttpClient.Builder): OkHttpClient {
    return httpClientBuilder
        .callTimeout(NETWORK_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .readTimeout(NETWORK_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .connectTimeout(NETWORK_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .build()
}

fun provideHttpClientBuilder(interceptor: Interceptor): OkHttpClient.Builder {
    val builder = OkHttpClient.Builder().addInterceptor(interceptor)

    if (BuildConfig.DEBUG) {
        builder.addInterceptor(HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        })
    }
    return builder
}

private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    BASE_URL: String
): Retrofit =
    Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()
