package kz.busjol.module

import kz.busjol.MainViewModel
import kz.busjol.ui.bus_plan.BusPlanViewModel
import kz.busjol.ui.change_language.ChangeLanguageViewModel
import kz.busjol.ui.enter_user.LoginUserViewModel
import kz.busjol.ui.search_trip.CityPickerViewModel
import kz.busjol.ui.search_trip.SearchTripViewModel
import kz.busjol.ui.tickets.TicketsViewModel
import kz.busjol.ui.user.UserViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(userPreferences = get()) }
    viewModel { SearchTripViewModel(repository = get(), preferences = get()) }
    viewModel { CityPickerViewModel() }
    viewModel { BusPlanViewModel() }
    viewModel { UserViewModel(userPreferences = get()) }
    viewModel { LoginUserViewModel(userPreferences = get()) }
    viewModel { TicketsViewModel(userPreferences = get()) }
    viewModel { ChangeLanguageViewModel(userPreferences = get()) }
}