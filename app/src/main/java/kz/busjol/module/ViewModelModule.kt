package kz.busjol.module

import kz.busjol.MainViewModel
import kz.busjol.ui.booking.BookingViewModel
import kz.busjol.ui.bus_plan.BusPlanFragmentArgs
import kz.busjol.ui.bus_plan.BusPlanViewModel
import kz.busjol.ui.change_language.ChangeLanguageViewModel
import kz.busjol.ui.login_user.LoginUserViewModel
import kz.busjol.ui.passenger_data.PassengerDataViewModel
import kz.busjol.ui.city_picker.CityPickerViewModel
import kz.busjol.ui.driver_scanner.DriverScannerViewModel
import kz.busjol.ui.journey.JourneyFragmentArgs
import kz.busjol.ui.journey.JourneyViewModel
import kz.busjol.ui.passenger_data.PassengerDataFragmentArgs
import kz.busjol.ui.passenger_quantity.PassengerQuantityDialogArgs
import kz.busjol.ui.passenger_quantity.PassengerQuantityViewModel
import kz.busjol.ui.payment_order_result.PaymentOrderResultViewModel
import kz.busjol.ui.search_journey.SearchJourneyViewModel
import kz.busjol.ui.tickets.TicketsViewModel
import kz.busjol.ui.user.UserViewModel
import kz.busjol.ui.user_my_data.UserDataViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel(userPreferences = get()) }
    viewModel { SearchJourneyViewModel(journeyRepository = get(), cityRepository = get()) }
    viewModel { (args: JourneyFragmentArgs) ->
        JourneyViewModel(args = args)
    }
    viewModel { CityPickerViewModel(repository = get()) }
    viewModel { (args: PassengerQuantityDialogArgs) ->
        PassengerQuantityViewModel(args = args)
    }
    viewModel { (args: BusPlanFragmentArgs) ->
        BusPlanViewModel(args = args)
    }
    viewModel { (args: PassengerDataFragmentArgs) ->
        PassengerDataViewModel(args)
    }
    viewModel { BookingViewModel() }
    viewModel { PaymentOrderResultViewModel() }
    viewModel { UserViewModel(userPreferences = get()) }
    viewModel { LoginUserViewModel(userPreferences = get()) }
    viewModel { TicketsViewModel(userPreferences = get()) }
    viewModel { ChangeLanguageViewModel(userPreferences = get()) }
    viewModel { DriverScannerViewModel() }
    viewModel { UserDataViewModel() }
}