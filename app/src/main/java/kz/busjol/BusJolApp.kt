package kz.busjol

import android.app.Application
import kz.busjol.module.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber
import java.util.*

class BusJolApp : Application() {

    override fun onCreate() {
        super.onCreate()

        //Setting default to Almaty Timezone
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+6:00"))

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@BusJolApp)
            modules(
                appModule,
                repoModule,
                viewModelModule,
                dataModule,
                networkModule
            )
        }
    }
}