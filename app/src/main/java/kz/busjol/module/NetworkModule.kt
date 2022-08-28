package kz.busjol.module

import android.content.Context
import androidx.viewbinding.BuildConfig
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import kz.busjol.Consts
import kz.busjol.data.network.CityListDataSource
import kz.busjol.data.network.JourneyDataSource
import kz.busjol.data.network.interceptors.GeneralInterceptors
import kz.busjol.preferences.UserPreferences
import kz.busjol.utils.DeviceUtils
import kz.busjol.utils.NetworkHelper
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { GeneralInterceptors(userPreferences = get()) }
    single { GsonConverterFactory.create(get()) }
    single { provideNetworkHelper(androidContext()) }
    single { provideInterceptor(get()) }
    single { provideHttpClient(get()) }

    single {
        provideHttpClientBuilder(
            interceptor = listOf(
                get<GeneralInterceptors>(),
                get<ChuckerInterceptor>()
            )
        )
    }

    single { provideRetrofit(BASE_URL = "https://daneek.xyz/api/", okHttpClient = get()) }

    single { provideChuckerInterceptor(context = get()) }

    single { get<Retrofit>().create(CityListDataSource::class.java) }
    single { get<Retrofit>().create(JourneyDataSource::class.java) }
}


private fun provideNetworkHelper(context: Context) = NetworkHelper(context)

private fun provideChuckerInterceptor(context: Context) = ChuckerInterceptor.Builder(context)
    .collector(ChuckerCollector(context))
    .maxContentLength(250000L)
    .redactHeaders(emptySet())
    .alwaysReadResponseBody(false)
    .build()

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
        .callTimeout(Consts.NETWORK_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .readTimeout(Consts.NETWORK_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .connectTimeout(Consts.NETWORK_TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
        .build()
}

fun provideHttpClientBuilder(interceptor: List<Interceptor>): OkHttpClient.Builder {
    val builder = OkHttpClient.Builder()

    interceptor.forEach {
        builder.addInterceptor(it)
    }

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
): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()
