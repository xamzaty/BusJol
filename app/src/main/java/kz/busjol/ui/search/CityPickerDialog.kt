package kz.busjol.ui.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.dialog_city_selector.*
import kz.busjol.base.BaseBottomFragmentDialog
import kz.busjol.data.City
import kz.busjol.databinding.DialogCitySelectorBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class CityPickerDialog : BaseBottomFragmentDialog<DialogCitySelectorBinding>(DialogCitySelectorBinding::inflate, true), CityListAdapter.OnItemClickListener {

    private val viewModel: CityPickerViewModel by viewModel()

    private val cityAdapter = CityListAdapter(this)
    private val args: CityPickerDialogArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.onAction(CitiesListAction.PassCityList(args.cityList.toMutableList()))
        setupObservers()
        setupFields()
        setupButtons()
        loadData()
    }

    override fun onCityClicked(city: City) {
        backWithData(city.name)
        dialog?.dismiss()
    }

    private fun backWithData(city: String) {
        if (args.fromOrToCity == "from") {
            findNavController().previousBackStackEntry?.savedStateHandle?.set("from", city)
        }
        if (args.fromOrToCity == "to") {
            findNavController().previousBackStackEntry?.savedStateHandle?.set("to", city)
        }
    }

    private fun setupObservers() {
        viewModel.apply {
            citiesList.observe(viewLifecycleOwner) { citiesList ->
                cityAdapter.submitList(citiesList)
            }
        }
    }

    private fun setupFields() {
        binding.apply {
            findCityEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.onAction(CitiesListAction.SearchCity(p0.toString()))
                }

                override fun afterTextChanged(p0: Editable?) {
                }
            })
        }
    }

    private fun setupButtons() {
        binding.buttonBack.setOnClickListener {
            dismiss()
        }
    }

    private fun loadData() {
        binding.apply {
            rv_city_selector.apply {
                adapter = cityAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }
}