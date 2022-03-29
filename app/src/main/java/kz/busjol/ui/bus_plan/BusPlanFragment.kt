package kz.busjol.ui.bus_plan

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import kz.busjol.base.BaseFragment
import kz.busjol.data.BusPlan
import kz.busjol.databinding.FragmentBusPlanBinding
import kz.busjol.utils.GridSpacingItemDecoration
import org.koin.androidx.viewmodel.ext.android.viewModel

class BusPlanFragment : BaseFragment<FragmentBusPlanBinding>(FragmentBusPlanBinding::inflate), BusPlanAdapter.OnItemClickListener {

    private val viewModel: BusPlanViewModel by viewModel()
    private val seatAdapter = BusPlanAdapter(this)

    private var checkedSeat: BusPlan? = null

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
                addItemDecoration(GridSpacingItemDecoration())
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
        checkedSeat = seat
        binding.selectedSeats.text = seat.place.toString()
    }
}