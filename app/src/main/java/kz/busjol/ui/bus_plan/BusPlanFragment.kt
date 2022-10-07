package kz.busjol.ui.bus_plan

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.GridLayoutManager
import kz.busjol.base.BaseFragment
import kz.busjol.data.network.model.BusPlan
import kz.busjol.databinding.FragmentBusPlanBinding
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class BusPlanFragment : BaseFragment<FragmentBusPlanBinding>(FragmentBusPlanBinding::inflate), BusPlanAdapter.OnItemClickListener {

    private val viewModel: BusPlanViewModel by viewModel {
        parametersOf(args)
    }
    private val seatAdapter = BusPlanAdapter(this)
    private val args: BusPlanFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        setupButtons()
        setupObservers()
        setupRecyclerView()
    }

    override fun addSeat(seat: BusPlan) {
        viewModel.onAction(BusPlanAction.AddItemsToBusPlanList(seat.place))
    }

    override fun removeSeat(seat: BusPlan) {
        viewModel.onAction(BusPlanAction.RemoveItemsFromBusPlanList(seat.place))
    }

    private fun observeViewModel() {
        viewModel.apply {
            selectedSeats.observe(viewLifecycleOwner) {
                val itemWithSeparator = it.toString()
                    .replace(",", " /")
                    .replace("[", "")
                    .replace("]", "")

                val itemWithoutSeparator = it.toString()
                    .replace(",", "")
                    .replace("[", "")
                    .replace("]", "")

                if (it.lastOrNull() == it.size - 1) {
                    binding.selectedSeats.text = itemWithoutSeparator
                } else {
                    binding.selectedSeats.text = itemWithSeparator
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
