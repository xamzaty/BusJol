package kz.busjol.ui.trip

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_trip.*
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.data.Trip
import kz.busjol.databinding.FragmentTripBinding

class TripFragment : BaseFragment<FragmentTripBinding>(FragmentTripBinding::inflate), TripListAdapter.OnItemClickListener {

    private val tripViewModel: TripViewModel by viewModels()
    private val tripAdapter = TripListAdapter(this)
    private val args: TripFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setTitle()
        setupButtons()
        loadData()
        setupObservers()
        activity?.window?.statusBarColor = resources.getColor(R.color.gray_background)
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
        tripViewModel.apply {
            tripList.observe(viewLifecycleOwner) { tripList ->
                tripAdapter.submitList(tripList)
            }
        }
    }

    private fun setupButtons() {
        binding.backButton.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun loadData() {
        binding.apply {
            rv_trip.apply {
                adapter = tripAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }
}