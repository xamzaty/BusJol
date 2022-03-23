package kz.busjol.ui.bus_plan

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.dialog_city_selector.*
import kz.busjol.base.BaseFragment
import kz.busjol.data.BusPlan
import kz.busjol.databinding.FragmentBusPlanBinding
import kz.busjol.ui.search_trip.CityListAdapter
import kz.busjol.utils.GridSpacingItemDecoration
import kz.busjol.utils.SpacesItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class BusPlanFragment : BaseFragment<FragmentBusPlanBinding>(FragmentBusPlanBinding::inflate), BusPlanAdapter.OnItemClickListener {

    private val viewModel: BusPlanViewModel by viewModel()
    private val seatAdapter = BusPlanAdapter(this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
        setupObservers()
        setupRecyclerView()
    }

    private fun setupButtons() {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

    private fun setupRecyclerView() {
        val gridLayoutManager = GridLayoutManager(context, 4)
        binding.apply {
            rvBusPlan.apply {
                adapter = seatAdapter
                layoutManager = gridLayoutManager
                addItemDecoration(SpacesItemDecoration(15))
            }
        }
    }

    private fun setupObservers() {
        viewModel.apply {
            seatList.observe(viewLifecycleOwner) { citiesList ->
                seatAdapter.submitList(citiesList)
            }
        }
    }

    override fun onSeatClicked(seat: BusPlan) {
    }
}