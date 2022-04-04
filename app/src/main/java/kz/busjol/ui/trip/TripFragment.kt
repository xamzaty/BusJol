package kz.busjol.ui.trip

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_trip.*
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.data.Trip
import kz.busjol.databinding.FragmentTripBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class TripFragment : BaseFragment<FragmentTripBinding>(FragmentTripBinding::inflate), TripListAdapter.OnItemClickListener {

    private val viewModel: TripViewModel by viewModels()
    private val tripAdapter = TripListAdapter(this)
    private val args: TripFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle()
        setupButtons()
        loadData()
        setupObservers()
    }

    override fun onCityClicked(trip: Trip) {
        val action = TripFragmentDirections.actionNavigationTripFragmentToNavigationBusPlan(trip.type)
        findNavController().navigate(action)
    }

    private fun setTitle() {
        val fromCity = args.cities[0]
        val toCity = args.cities[1]
        binding.titleTrip.text = getString(R.string.title_trip, fromCity, toCity)
    }

    private fun setupObservers() {
        viewModel.apply {
            tripList.observe(viewLifecycleOwner) { tripList ->
                tripAdapter.submitList(tripList)
            }
        }
    }

    private fun setupButtons() {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            viewModel.apply {
                radioGroup.setOnCheckedChangeListener { _, i ->
                    if (i == R.id.all_button) {
                        tripList.observe(viewLifecycleOwner) { tripList ->
                            tripAdapter.submitList(tripList)
                        }
                    }

                    if (i == R.id.lying_place_button) {
                        tripLyingList.observe(viewLifecycleOwner) { tripList ->
                            tripAdapter.submitList(tripList)
                        }
                    }

                    if (i == R.id.seat_place_button) {
                        tripSeatingList.observe(viewLifecycleOwner) { tripList ->
                            tripAdapter.submitList(tripList)
                        }
                    }
                }
            }
        }
    }

    private fun loadData() {
        binding.apply {
            rv_trip.apply {
                adapter = tripAdapter
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                addItemDecoration(TripItemDecoration())
            }
        }
    }
}