package kz.busjol.module

import android.content.Context
import androidx.viewbinding.BuildConfig
import kz.busjol.Consts.Companion.NETWORK_TIMEOUT_IN_SECONDS
import kz.busjol.data.network.CityListDataSource
import kz.busjol.data.network.JourneyDataSource
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

val appModule = module {
}
