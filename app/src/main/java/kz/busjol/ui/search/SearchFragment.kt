package kz.busjol.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kz.busjol.R
import kz.busjol.databinding.FragmentSearchBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        searchViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupButtons()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupButtons() {
        binding.apply {
            openFromCitiesDialog.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_search_to_cityPickerDialog)
            }

            openToCitiesDialog.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_search_to_cityPickerDialog)
            }

            searchTripButton.setOnClickListener {
                navigateToTripFragment()
            }

            swapCitiesButton.setOnClickListener {
                searchViewModel.onAction(SearchFragmentAction.SwapCities)
            }
        }
    }

    private fun navigateToTripFragment() {
        binding.apply {
            val cityArray = arrayOf(fromCityEt.text.toString(), toCityEt.text.toString())
            val action = SearchFragmentDirections.actionNavigationSearchToNavBuyTickets(cityArray)
            findNavController().navigate(action)
        }
    }

    private fun setupObservers() {
        searchViewModel.apply {
            fromCityValue.observe(viewLifecycleOwner, { fromCityValue ->
                binding.fromCityEt.setText(fromCityValue)
            })

            toCityValue.observe(viewLifecycleOwner, { toCityValue ->
                binding.toCityEt.setText(toCityValue)
            })

        }
    }
}