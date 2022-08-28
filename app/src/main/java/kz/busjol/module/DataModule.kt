package kz.busjol.module

import kz.busjol.data.mapper.CityListMapper
import kz.busjol.data.mapper.JourneyListMapper
import kz.busjol.domain.use_case.SearchTripUseCase
import kz.busjol.preferences.DriverPreferences
import kz.busjol.preferences.UserPreferences
import org.koin.dsl.module

val dataModule = module {
    single { UserPreferences(context = get()) }
    single { DriverPreferences(context = get()) }

    factory { CityListMapper() }
    factory { JourneyListMapper() }
    factory { SearchTripUseCase(journeyRepository = get(), cityRepository = get()) }
}