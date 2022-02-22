package kz.busjol.ui.search

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
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by viewModel()
    private var cityList = listOf<City>()
    private var adultPassengersQuantity = 0
    private var childPassengersQuantity = 0
    private var disabledPassengersQuantity = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCalendar()
        setupObservers()
        setupButtons()
        binding.toCityEt.setText("Актау")
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
                openPassengersQuantityDialog()
            }

            swapCitiesButton.setOnClickListener {
                if (fromCityEt.text!!.isNotEmpty() && toCityEt.text!!.isNotEmpty())
                viewModel.onAction(CitiesListAction.SwapCities)
            }
        }
    }

    private fun openCityPickerDialog(arg: String) {
        if (cityList.isNotEmpty()) {
            val action = SearchFragmentDirections.actionNavigationSearchToCityPickerDialog(cityList.toTypedArray(), arg)
            findNavController().navigate(action)
        }
    }

    private fun openPassengersQuantityDialog() {
        val action = SearchFragmentDirections.actionNavigationSearchToNavigationQuantityDialog(adultPassengersQuantity, childPassengersQuantity, disabledPassengersQuantity)
        findNavController().navigate(action)
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
            viewModel.onAction(CitiesListAction.FromCityValue(result))
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("to")?.observe(
            viewLifecycleOwner) { result ->
            viewModel.onAction(CitiesListAction.ToCityValue(result))
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<String>("quantity")?.observe(
            viewLifecycleOwner) { result ->
            viewModel.onAction(CitiesListAction.FillPassengersQuantityValue(result))
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>("adultQuantity")?.observe(
            viewLifecycleOwner) { result ->
            adultPassengersQuantity = result
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>("childQuantity")?.observe(
            viewLifecycleOwner) { result ->
            childPassengersQuantity = result
        }

        findNavController().currentBackStackEntry?.savedStateHandle?.getLiveData<Int>("disabledQuantity")?.observe(
            viewLifecycleOwner) { result ->
            disabledPassengersQuantity = result
        }

        viewModel.apply {
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
                cityList = cityListViewModel
            })
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
                showSimpleAlertDialog(R.string.error, R.string.fill_all_the_fields)
            } else if (fromCityEt.text.toString() == toCityEt.text.toString() &&
                fromCityEt.text.toString().isNotEmpty() && toCityEt.text.toString().isNotEmpty()){

                showSimpleAlertDialog(R.string.error, R.string.similar_cities)
                viewModel.onAction(CitiesListAction.FromCityValue(""))
                viewModel.onAction(CitiesListAction.ToCityValue(""))
            } else {
                navigateToTripFragment()
                viewModel.onAction(CitiesListAction.PassPassengersQuantityData(allPassengersQuantity().toMutableList()))
                println(allPassengersQuantity().toList())
            }
        }
    }
}