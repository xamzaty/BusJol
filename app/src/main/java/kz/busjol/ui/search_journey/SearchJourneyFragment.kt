package kz.busjol.ui.search_journey

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentSearchTripBinding
import kz.busjol.domain.model.City
import kz.busjol.domain.model.JourneyData
import kz.busjol.domain.model.JourneyPost
import kz.busjol.domain.model.PassengerData
import kz.busjol.ext.reformatDateToBackend
import kz.busjol.ext.setOnSafeClickListener
import kz.busjol.utils.state.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class SearchJourneyFragment :
    BaseFragment<FragmentSearchTripBinding>(FragmentSearchTripBinding::inflate) {

    private val viewModel: SearchJourneyViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initButtons()
        setupObservers()
        backStackDataInit()
        backStackFromJourneyScreen()
    }

    private fun initButtons() {
        binding.apply {
            dateLayout.apply {
                setTitle(R.string.date)
                setHint(R.string.date_hint)
                enableCalendarView(activity, true)
            }

            searchTripButton.apply {
                setTitle(R.string.search_button)
            }

            openFromCitiesDialog.setOnSafeClickListener {
                openCityPickerDialog("from")
            }

            openToCitiesDialog.setOnSafeClickListener {
                openCityPickerDialog("to")
            }

            swapCitiesButton.setOnClickListener {
                viewModel.onAction(SearchJourneyAction.OnSwapCities)
            }

            searchTripButton.apply {
                onSetClickListener {
                    onJourneySearchClickListener()
                }
            }

            fromCityEt.isFocusable = false
            toCityEt.isFocusable = false
        }
    }

    private fun backStackFromJourneyScreen() {
        onBackStackEntry<JourneyData>("journeyFragmentData") {
//            viewModel.onAction(SearchJourneyAction.OnJourneyBackDataInit(it))
        }
    }

    private fun setupObservers() {
        viewModel.apply {
            viewState.observe(viewLifecycleOwner) {
                handleViewStateChanges(it)
            }

            fromCity.observe(viewLifecycleOwner) {
                binding.fromCityEt.setText(it?.name ?: "")
            }

            toCity.observe(viewLifecycleOwner) {
                binding.toCityEt.setText(it?.name ?: "")
            }

            passengerData.observe(viewLifecycleOwner) {
                val allPassengersData = it.allPassengersQuantity()
                val allPassengersText = "$allPassengersData ${getString(R.string.one_person)}"

                binding.passengerNumberLayout.apply {
                    setText(allPassengersText)
                    setTitle(R.string.passengers)
                    enableClickableView(imageId = R.drawable.ic_user) {
                        openPassengersQuantityDialog(it)
                    }
                }
            }
        }
    }

    private fun handleViewStateChanges(state: ViewState<SearchJourneyViewState>) {
        when (state) {
            is ViewState.Data -> {
                handleViewState(state.data)
                binding.searchTripButton.onLoading(false)
            }
            is ViewState.Error -> {
                onError()
                binding.searchTripButton.onLoading(false)
            }
            is ViewState.Loading -> binding.searchTripButton.onLoading(true)
        }
    }

    private fun handleViewState(state: SearchJourneyViewState) {
        when (state) {
            is SearchJourneyViewState.NavigateToJourneyScreen -> navigateToJourneyScreen(state.journeyData)
        }
    }

    private fun backStackDataInit() {
        onBackStackEntry<City>("from") {
            viewModel.onAction(SearchJourneyAction.FromCityValueInit(it))
        }

        onBackStackEntry<City>("to") {
            viewModel.onAction(SearchJourneyAction.ToCityValueInit(it))
        }

        onBackStackEntry<String>("quantity") {
            binding.passengerNumberLayout.setText(it)
        }

        onBackStackEntry<PassengerData>("data") {
            viewModel.onAction(SearchJourneyAction.PassPassengersData(it))
        }
    }

    // Переход в диалог по выбору города
    private fun openCityPickerDialog(arg: String) {
        val action = SearchJourneyFragmentDirections.actionNavigationSearchToCityPickerDialog(arg)
        findNavController().navigate(action)
    }

    // Переход в диалог по выбору количества пассажиров
    private fun openPassengersQuantityDialog(passengerData: PassengerData?) {
        val action = SearchJourneyFragmentDirections.actionNavigationSearchToNavigationQuantityDialog(passengerData)
        findNavController().navigate(action)
    }

    private fun onJourneySearchClickListener() {
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val todayDate = sdf.format(Date()).reformatDateToBackend(false) ?: ""
        val nextDate = binding.dateLayout.getText().reformatDateToBackend(true) ?: ""

        val postJourneyData = JourneyPost(
            cityFrom = viewModel.fromCity.value!!.id,
            cityTo = viewModel.toCity.value!!.id,
            dateFrom = todayDate,
            dateTo = nextDate
        )

        viewModel.onAction(SearchJourneyAction.OnSearchJourneyButtonClicked(postJourneyData))
    }

    // Переход к экрану со списком билетов
    private fun navigateToJourneyScreen(journeyData: JourneyData) {
        val action = SearchJourneyFragmentDirections.actionNavigationSearchToNavBuyTickets(journeyData)

        if (journeyData.fromCity != journeyData.toCity) findNavController().navigate(action)
        else viewModel.onAction(SearchJourneyAction.SimilarCitiesInit)
    }
}