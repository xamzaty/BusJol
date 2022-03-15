package kz.busjol.module

import kz.busjol.MainViewModel
import kz.busjol.ui.search_trip.CityPickerViewModel
import kz.busjol.ui.search_trip.SearchTripViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(userPreferences = get()) }
    viewModel { SearchTripViewModel(repository = get(), preferences = get()) }
    viewModel { CityPickerViewModel() }
}