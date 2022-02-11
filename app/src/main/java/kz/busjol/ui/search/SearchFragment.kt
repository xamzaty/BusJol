package kz.busjol.ui.search

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.data.City
import kz.busjol.databinding.FragmentSearchBinding
import kz.busjol.ext.FragmentExt.showSimpleAlertDialog
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val searchViewModel: SearchViewModel by viewModel()
    private lateinit var cityList: Array<City>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCalendar()
        setupObservers()
        setupButtons()
    }

    private fun setupCalendar() {
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
                searchViewModel.onAction(CitiesListAction.SwapCities)
            }
        }
    }

    private fun openCityPickerDialog(arg: String) {
        if (cityList.isNotEmpty()) {
            val action = SearchFragmentDirections.actionNavigationSearchToCityPickerDialog(cityList, arg)
            findNavController().navigate(action)
        }
    }

    private fun navigateToTripFragment() {
        binding.apply {
            val cityArray = arrayOf(fromCityEt.text.toString(), toCityEt.text.toString())
            val date = dateEt.text.toString()
            val action = SearchFragmentDirections.actionNavigationSearchToNavBuyTickets(cityArray, date)
            findNavController().navigate(action)
        }
    }

    private fun setupObservers() {

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("from")?.observe(
            viewLifecycleOwner) { result ->
            searchViewModel.onAction(CitiesListAction.FromCityValue(result))
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("to")?.observe(
            viewLifecycleOwner) { result ->
            searchViewModel.onAction(CitiesListAction.ToCityValue(result))
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("quantity")?.observe(
            viewLifecycleOwner) { result ->
            searchViewModel.onAction(CitiesListAction.FillPassengersQuantityValue(result))
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

            citiesList.observe(viewLifecycleOwner, { cityListViewModel ->
                cityList = cityListViewModel.toTypedArray()
            })
        }
    }

    private fun validateFields() {
        binding.apply {
            if (fromCityEt.text.isNullOrEmpty() ||
                toCityEt.text.isNullOrEmpty() ||
                dateEt.text.isNullOrEmpty() ||
                passengerNumberEt.text.isNullOrEmpty()) {
                showSimpleAlertDialog(R.string.error, R.string.fill_all_the_fields)
            } else if (fromCityEt.text.toString() == toCityEt.text.toString() &&
                fromCityEt.text.toString().isNotEmpty() && toCityEt.text.toString().isNotEmpty()){

                showSimpleAlertDialog(R.string.error, R.string.similar_cities)
                searchViewModel.onAction(CitiesListAction.FromCityValue(""))
                searchViewModel.onAction(CitiesListAction.ToCityValue(""))
            } else {
                navigateToTripFragment()
            }
        }
    }
}