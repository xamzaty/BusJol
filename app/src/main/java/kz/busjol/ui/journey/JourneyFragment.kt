package kz.busjol.ui.journey

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_journey.*
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentJourneyBinding
import kz.busjol.domain.model.Journey
import kz.busjol.domain.model.JourneyData
import kz.busjol.utils.state.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class JourneyFragment :
    BaseFragment<FragmentJourneyBinding>(FragmentJourneyBinding::inflate), JourneyListAdapter.OnItemClickListener {

    private val viewModel: JourneyViewModel by viewModel {
        parametersOf(args)
    }
    private val tripAdapter = JourneyListAdapter(this)
    private val args: JourneyFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setAdapterValues()
        backWithData(args.journeyData)
    }

    override fun onCityClicked(journeyResponse: Journey) {
        val journeyData = JourneyData(
            passengerData = args.journeyData.passengerData,
            passengerListData = args.journeyData.passengerListData,
            fromCity = args.journeyData.fromCity,
            toCity = args.journeyData.toCity,
            journey =  journeyResponse,
            journeyList = null
        )

        val action = JourneyFragmentDirections
            .actionNavigationJourneyFragmentToNavigationBusPlan(journeyData)

        findNavController().navigate(action)
    }

    private fun setAdapterValues() {
        tripAdapter.fromCityValue(args.journeyData.fromCity?.name)
        tripAdapter.toCityValue(args.journeyData.toCity?.name)
    }

    private fun backWithData(journeyData: JourneyData) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set("journeyFragmentData", journeyData)
    }

    private fun setupObservers() {
        viewModel.apply {
            viewState.observe(viewLifecycleOwner) {
                handleViewStateChanges(it)
            }
        }
    }

    private fun handleViewStateChanges(state: ViewState<JourneyViewState>) {
        when (state) {
            is ViewState.Data -> handleViewState(state.data)
            is ViewState.Error -> onError()
            is ViewState.Loading -> Unit
        }
    }

    private fun handleViewState(state: JourneyViewState) {
        when (state) {
            is JourneyViewState.JourneyDataInit -> initData(state.journeyData)
        }
    }

    private fun initData(journeyData: JourneyData) {
        loadData()
        setTitle(journeyData)
        tripAdapter.submitList(journeyData.journeyList)
        setupButtons()
    }

    private fun loadData() {
        binding.apply {
            rv_journey.apply {
                adapter = tripAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                addItemDecoration(JourneyItemDecoration())
            }
        }
    }

    private fun setTitle(journeyData: JourneyData) {
        val fromCity = journeyData.fromCity?.name
        val toCity = journeyData.toCity?.name
        binding.titleTrip.text = "$fromCity - $toCity"
    }

    private fun setupButtons() {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            viewModel.apply {
                radioGroup.setOnCheckedChangeListener { _, i ->
                    if (i == R.id.all_button) {
                        viewModel.onAction(JourneyAction.FilterListByTypeButtonClicked(true))
                    }

                    if (i == R.id.lying_place_button) {
                        viewModel.onAction(JourneyAction.FilterListByTypeButtonClicked(false, getString(R.string.lying_seats)))
                    }

                    if (i == R.id.seat_place_button) {
                        viewModel.onAction(JourneyAction.FilterListByTypeButtonClicked(false, getString(R.string.seating_seats)))
                    }
                }
            }
        }
    }
}