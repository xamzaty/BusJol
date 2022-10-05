package kz.busjol.ui.bus_plan

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.data.network.model.BusPlan
import kz.busjol.databinding.FragmentBusPlanBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BusPlanFragment : BaseFragment<FragmentBusPlanBinding>(FragmentBusPlanBinding::inflate), BusPlanAdapter.OnItemClickListener {

    private val viewModel: BusPlanViewModel by viewModel()
    private val seatAdapter = BusPlanAdapter(this)
    private val args: BusPlanFragmentArgs by navArgs()

    private var checkedSeat: BusPlan? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupButtons()
        setupObservers()
        setupRecyclerView()
    }

    override fun onSeatClicked(seat: BusPlan) {
        viewModel.onAction(BusPlanAction.AddItemsToBusPlanList(seat.place))
//        list.forEach {

//        }
    }

    private fun observeViewModel() {
        viewModel.apply {
            selectedSeats.observe(viewLifecycleOwner) {
                it.forEach { seat ->
                    binding.selectedSeats.append("$seat/")
                }
            }
        }
    }

    private fun setupButtons() {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            continueButton.setOnClickListener {
                val action = BusPlanFragmentDirections.actionNavigationBusPlanToPassengerDataFragment(args.journeyData)
                findNavController().navigate(action)
            }
        }
    }

    private fun setupRecyclerView() {
        val gridLayoutManager = GridLayoutManager(context, 4)
        binding.apply {
            rvBusPlan.apply {
                adapter = seatAdapter
                layoutManager = gridLayoutManager
                addItemDecoration(BusPlanItemDecoration())
            }
        }
    }

    private fun setupObservers() {
        viewModel.apply {
            seatList.observe(viewLifecycleOwner) { seatList ->
                seatAdapter.submitList(seatList)
            }
        }
    }
}