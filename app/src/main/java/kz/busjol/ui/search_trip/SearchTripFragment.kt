package kz.busjol.ui.search_trip

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.data.City
import kz.busjol.databinding.FragmentSearchTripBinding
import kz.busjol.ext.FragmentExt.showSimpleAlertDialog
import kz.busjol.ext.FragmentExt.showSnackBar
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.text.SimpleDateFormat
import java.util.*

class SearchTripFragment : BaseFragment<FragmentSearchTripBinding>(FragmentSearchTripBinding::inflate) {

    private val tripViewModel: SearchTripViewModel by viewModel()
    private var cityList = listOf<City>()
    private var adultPassengersQuantity = 1
    private var childPassengersQuantity = 0
    private var disabledPassengersQuantity = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupCalendar()
        setupObservers()
        setupButtons()
        activity?.window?.statusBarColor = resources.getColor(R.color.white)
    }

    private fun setupCalendar() {
        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { _, year, month, day ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, day)
            updateCalendarDate(myCalendar)
        }

        binding.openDateDialog.setOnClickListener {
            val datePickerDialog = activity?.let { it1 ->
                DatePickerDialog(
                    it1, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH))
            }
            datePickerDialog?.datePicker?.minDate = System.currentTimeMillis() - 1000
            datePickerDialog?.show()
        }
    }

    private fun updateCalendarDate(myCalendar: Calendar) {
        val format = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(format, Locale.UK)
        binding.dateEt.setText(sdf.format(myCalendar.time))
    }

    private fun setupButtons() {
        val fromCity = "from"
        val toCity = "to"

        binding.apply {
            openFromCitiesDialog.setOnClickListener {
                openCityPickerDialog(fromCity)
            }

            openToCitiesDialog.setOnClickListener {
                openCityPickerDialog(toCity)
            }

            searchTripButton.apply {
                setOnClickListener {
                    validateFields()
                }
            }

            openPassengerNumberDialog.setOnClickListener {
                openPassengersQuantityDialog()
            }

            swapCitiesButton.setOnClickListener {
                if (fromCityEt.text!!.isNotEmpty() && toCityEt.text!!.isNotEmpty())
                tripViewModel.onAction(CitiesListAction.SwapCities)
            }
        }
    }

    private fun openCityPickerDialog(arg: String) {
        if (cityList.isNotEmpty()) {
            val action = SearchTripFragmentDirections.actionNavigationSearchToCityPickerDialog(cityList.toTypedArray(), arg)
            findNavController().navigate(action)
        }
    }

    private fun openPassengersQuantityDialog() {
        val action = SearchTripFragmentDirections.actionNavigationSearchToNavigationQuantityDialog(
            adultPassengersQuantity, childPassengersQuantity, disabledPassengersQuantity
        )
        findNavController().navigate(action)
    }

    private fun navigateToTripFragment() {
        binding.apply {
            val cityArray = arrayOf(fromCityEt.text.toString(), toCityEt.text.toString())
            val date = dateEt.text.toString()
            val action = SearchTripFragmentDirections.actionNavigationSearchToNavBuyTickets(cityArray, date)
            findNavController().navigate(action)
        }
    }

    private fun setupObservers() {
        val fromKey = "from"
        val toKey = "to"
        val quantityKey = "quantity"
        val adultQuantityKey = "adultQuantity"
        val childQuantityKey = "childQuantity"
        val disabledQuantityKey = "disabledQuantity"

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(fromKey)?.observe(
            viewLifecycleOwner) { result ->
            tripViewModel.onAction(CitiesListAction.FromCityValue(result))
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(toKey)?.observe(
            viewLifecycleOwner) { result ->
            tripViewModel.onAction(CitiesListAction.ToCityValue(result))
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>(quantityKey)?.observe(
            viewLifecycleOwner) { result ->
            tripViewModel.onAction(CitiesListAction.FillPassengersQuantityValue(result))
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>(adultQuantityKey)?.observe(
            viewLifecycleOwner) { result ->
            adultPassengersQuantity = result
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>(childQuantityKey)?.observe(
            viewLifecycleOwner) { result ->
            childPassengersQuantity = result
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>(disabledQuantityKey)?.observe(
            viewLifecycleOwner) { result ->
            disabledPassengersQuantity = result
        }

        tripViewModel.apply {
            fromCityValue.observe(viewLifecycleOwner) { fromCityValue ->
                binding.fromCityEt.setText(fromCityValue)
            }

            toCityValue.observe(viewLifecycleOwner) { toCityValue ->
                binding.toCityEt.setText(toCityValue)
            }

            passengersQuantity.observe(viewLifecycleOwner) { quantity ->
                binding.passengerNumberEt.setText(quantity)
            }

            citiesList.observe(viewLifecycleOwner) { cityListViewModel ->
                cityList = cityListViewModel
            }
        }
    }

    private fun allPassengersQuantity(): Array<Int> {
        val allPassengers = adultPassengersQuantity + childPassengersQuantity + disabledPassengersQuantity
        return arrayOf(allPassengers, adultPassengersQuantity, childPassengersQuantity, disabledPassengersQuantity)
    }

    private fun validateFields() {
        binding.apply {
            if (fromCityEt.text.isNullOrEmpty() ||
                toCityEt.text.isNullOrEmpty() ||
                dateEt.text.isNullOrEmpty() ||
                passengerNumberEt.text.isNullOrEmpty()) {
                    showSnackBar(R.string.fill_all_the_fields)
            } else if (fromCityEt.text.toString() == toCityEt.text.toString() &&
                fromCityEt.text.toString().isNotEmpty() && toCityEt.text.toString().isNotEmpty()){
                    showSnackBar(R.string.similar_cities)
                    tripViewModel.onAction(CitiesListAction.FromCityValue(""))
                    tripViewModel.onAction(CitiesListAction.ToCityValue(""))
            } else {
                navigateToTripFragment()
                tripViewModel.onAction(CitiesListAction.PassPassengersQuantityData(allPassengersQuantity().toMutableList()))
                println(allPassengersQuantity().toList())
            }
        }
    }
}