package kz.busjol.ui.search

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kz.busjol.R
import kz.busjol.databinding.FragmentSearchBinding
import java.text.SimpleDateFormat
import java.util.*

class SearchFragment : Fragment() {

    private val searchViewModel: SearchViewModel by viewModels()
    private var _binding: FragmentSearchBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentSearchBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObservers()
        setupButtons()

        val myCalendar = Calendar.getInstance()

        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, day ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            updateCalendarDate(myCalendar)
        }

        binding.openDateDialog.setOnClickListener {
            activity?.let { it1 ->
                DatePickerDialog(
                    it1, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show()
            }
        }

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            requireActivity().window.statusBarColor = requireActivity().getColor(R.color.white)
//        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateCalendarDate(myCalendar: Calendar) {
        val format = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(format, Locale.UK)
        binding.dateEt.setText(sdf.format(myCalendar.time))
    }

    private fun setupButtons() {
        binding.apply {
            openFromCitiesDialog.setOnClickListener {
                openCityPickerDialog("from")
            }

            openToCitiesDialog.setOnClickListener {
                openCityPickerDialog("to")
            }

            searchTripButton.apply {
                setOnClickListener {
                    validateFields()
                }
            }

            openPassengerNumberDialog.setOnClickListener {
                findNavController().navigate(R.id.action_navigation_search_to_navigation_quantity_dialog)
            }

            swapCitiesButton.setOnClickListener {
                if (fromCityEt.text!!.isNotEmpty() && toCityEt.text!!.isNotEmpty())
                searchViewModel.onAction(SearchFragmentAction.SwapCities)
            }
        }
    }

    private fun openCityPickerDialog(arg: String) {
        val action = SearchFragmentDirections.actionNavigationSearchToCityPickerDialog(arg)
        findNavController().navigate(action)
    }

    private fun navigateToTripFragment() {
        binding.apply {
            val cityArray = arrayOf(fromCityEt.text.toString(), toCityEt.text.toString())
            val action = SearchFragmentDirections.actionNavigationSearchToNavBuyTickets(cityArray)
            findNavController().navigate(action)
        }
    }

    private fun setupObservers() {

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("from")?.observe(
            viewLifecycleOwner) { result ->
            searchViewModel.onAction(SearchFragmentAction.FromCityValue(result))
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("to")?.observe(
            viewLifecycleOwner) { result ->
            searchViewModel.onAction(SearchFragmentAction.ToCityValue(result))
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("quantity")?.observe(
            viewLifecycleOwner) { result ->
            searchViewModel.onAction(SearchFragmentAction.FillPassengersQuantityValue(result))
        }

        searchViewModel.apply {
            fromCityValue.observe(viewLifecycleOwner, { fromCityValue ->
                binding.fromCityEt.setText(fromCityValue)
            })

            toCityValue.observe(viewLifecycleOwner, { toCityValue ->
                binding.toCityEt.setText(toCityValue)
            })

            passengersQuantity.observe(viewLifecycleOwner, { quantity ->
                binding.passengerNumberEt.setText(quantity)
            })
        }
    }

    private fun validateFields() {
        binding.apply {
            if (fromCityEt.text.isNullOrEmpty() ||
                toCityEt.text.isNullOrEmpty() ||
                dateEt.text.isNullOrEmpty() ||
                passengerNumberEt.text.isNullOrEmpty()) {
                showFillAllTheFieldsAlert()
            }
            else if (fromCityEt.text.toString() == toCityEt.text.toString() &&
                fromCityEt.text.toString().isNotEmpty() && toCityEt.text.toString().isNotEmpty()){

                showSimilarCitiesAlert()
                searchViewModel.onAction(SearchFragmentAction.FromCityValue(""))
                searchViewModel.onAction(SearchFragmentAction.ToCityValue(""))
            }
            else {
                navigateToTripFragment()
            }
        }
    }

    private fun showFillAllTheFieldsAlert() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(getString(R.string.error))
        builder.setMessage(getString(R.string.fill_all_the_fields))

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }

    private fun showSimilarCitiesAlert() {
        val builder = AlertDialog.Builder(activity)
        builder.setTitle(getString(R.string.error))
        builder.setMessage(getString(R.string.similar_cities))

        builder.setPositiveButton(android.R.string.yes) { dialog, which ->
            dialog.dismiss()
        }
        builder.show()
    }
}