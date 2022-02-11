package kz.busjol.module

import kz.busjol.ui.search.CityPickerViewModel
import kz.busjol.ui.search.PassengersQuantityViewModel
import kz.busjol.ui.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchViewModel(repository = get()) }
    viewModel { CityPickerViewModel() }
    viewModel { PassengersQuantityViewModel(preferences = get()) }
}