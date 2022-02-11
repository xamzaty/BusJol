package kz.busjol.module

import kz.busjol.preferences.DriverPreferences
import kz.busjol.preferences.PassengerPreferences
import kz.busjol.preferences.UserPreferences
import org.koin.dsl.module

val dataModule = module {
    single { PassengerPreferences(context = get()) }
    single { UserPreferences(context = get()) }
    single { DriverPreferences(context = get()) }
}