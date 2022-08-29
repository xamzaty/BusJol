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
import kz.busjol.ext.formatDate
import kz.jysan.business.core.ui.utils.state.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class SearchJourneyFragment :
    BaseFragment<FragmentSearchTripBinding>(FragmentSearchTripBinding::inflate) {

    private val viewModel: SearchJourneyViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        backStackFromJourneyScreen()
    }

    private fun backStackFromJourneyScreen() {
        onBackStackEntry<JourneyData>("journeyFragmentData") {
            viewModel.onAction(SearchJourneyAction.OnJourneyBackDataInit(it))
        }
    }

    private fun setupObservers() {
        viewModel.apply {
            viewState.observe(viewLifecycleOwner) {
                handleViewStateChanges(it)
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
            is SearchJourneyViewState.JourneyDataInit -> initData(state.journeyData)
            is SearchJourneyViewState.NavigateToJourneyScreen -> navigateToJourneyScreen(state.journeyData)
        }
    }

    private fun initData(journeyData: JourneyData) {
        backStackDataInit(journeyData)
        setupButtons(journeyData)
        setupTextFields(journeyData)
    }

    private fun backStackDataInit(journeyData: JourneyData) {
        onBackStackEntry<City>("from") {
            viewModel.onAction(SearchJourneyAction.CityValueInit(journeyData, it, true))
        }

        onBackStackEntry<City>("to") {
            viewModel.onAction(SearchJourneyAction.CityValueInit(journeyData, it, false))
        }

        onBackStackEntry<String>("quantity") {
            binding.passengerNumberLayout.setText(it)
        }

        onBackStackEntry<JourneyData>("data") {
            viewModel.onAction(SearchJourneyAction.PassPassengersData(it))
        }
    }

    private fun setupTextFields(journeyData: JourneyData) {
        val allPassengersData = journeyData.passengerData?.allPassengersQuantity()
        val allPassengersText = "$allPassengersData ${getString(R.string.one_person)}"

        binding.apply {
            fromCityEt.setText(journeyData.fromCity?.name ?: "")
            toCityEt.setText(journeyData.toCity?.name ?: "")
            passengerNumberLayout.setText(allPassengersText)
        }
    }

    private fun setupButtons(journeyData: JourneyData) {
        binding.apply {

            openFromCitiesDialog.setOnClickListener {
                openCityPickerDialog("from", journeyData)
            }

            openToCitiesDialog.setOnClickListener {
                openCityPickerDialog("to", journeyData)
            }

            swapCitiesButton.setOnClickListener {
                viewModel.onAction(SearchJourneyAction.OnSwapCities(journeyData))
            }

            dateLayout.apply {
                setTitle(R.string.date)
                setHint(R.string.date_hint)
                enableCalendarView(activity, true)
            }

            passengerNumberLayout.apply {
                setTitle(R.string.passengers)
                enableClickableView(imageId = R.drawable.ic_user) {
                    openPassengersQuantityDialog(journeyData)
                }
            }

            searchTripButton.apply {
                setTitle(R.string.search_button)
                onSetClickListener {
                    onJourneySearchClickListener(journeyData)
                }
            }
        }
    }

    // Переход в диалог по выбору города
    private fun openCityPickerDialog(arg: String, journeyData: JourneyData) {
        val action = SearchJourneyFragmentDirections.actionNavigationSearchToCityPickerDialog(arg, journeyData)
        findNavController().navigate(action)
    }

    // Переход в диалог по выбору количества пассажиров
    private fun openPassengersQuantityDialog(journeyData: JourneyData) {
        val action = SearchJourneyFragmentDirections.actionNavigationSearchToNavigationQuantityDialog(journeyData)
        findNavController().navigate(action)
    }

    private fun onJourneySearchClickListener(journeyData: JourneyData) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val todayDate = "${sdf.format(Date())}T00:00:00"

        val inputFormat = "dd.MM.yyyy"
        val outputFormat = "yyyy-MM-dd"
        val convertedDate = formatDate(binding.dateLayout.getText(), inputFormat, outputFormat)
        val toDate = "${convertedDate}T00:00:00"


        val postJourneyData = JourneyPost(
            cityFrom = journeyData.fromCity!!.id,
            cityTo = journeyData.toCity!!.id,
            dateFrom = todayDate,
            dateTo = toDate
        )

        viewModel.onAction(SearchJourneyAction.OnSearchJourneyButtonClicked(postJourneyData, journeyData))
    }

    // Переход к экрану со списком билетов
    private fun navigateToJourneyScreen(journeyData: JourneyData) {
        val action = SearchJourneyFragmentDirections.actionNavigationSearchToNavBuyTickets(journeyData)

        if (journeyData.fromCity != journeyData.toCity) findNavController().navigate(action)
        else viewModel.onAction(SearchJourneyAction.SimilarCities(journeyData))
    }
}