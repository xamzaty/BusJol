package kz.busjol.ui.city_picker

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.dialog_city_selector.*
import kz.busjol.base.BaseBottomFragmentDialog
import kz.busjol.databinding.DialogCitySelectorBinding
import kz.busjol.domain.model.City
import kz.busjol.utils.state.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class CityPickerDialog :
    BaseBottomFragmentDialog<DialogCitySelectorBinding>(DialogCitySelectorBinding::inflate, true), CityListAdapter.OnItemClickListener {

    private val viewModel: CityPickerViewModel by viewModel()

    private val cityAdapter = CityListAdapter(this)
    private val args: CityPickerDialogArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupObservers()
        setupButtons()
        loadData()
    }

    override fun onCityClicked(city: City) {
        backWithData(city)
        dialog?.dismiss()
    }

    private fun setupObservers() {
        viewModel.viewState.observe(viewLifecycleOwner) {
            handleViewStateChanges(it)
        }
    }

    private fun handleViewStateChanges(state: ViewState<CityListViewState>) {
        when (state) {
            is ViewState.Data -> handleViewState(state.data)
            is ViewState.Error -> onError()
            is ViewState.Loading -> onLoading(true)
        }
    }

    private fun handleViewState(state: CityListViewState) {
        when (state) {
            is CityListViewState.CityListDataInit -> {
                onDataInit(state.city)
            }
        }
    }

    private fun onDataInit(cityList: List<City>) {
        onLoading(false)
        setupFields(cityList)
        cityAdapter.submitList(cityList)

        if (cityList.isEmpty()) {
            binding.rvCitySelector.visibility = View.GONE
            binding.nothingFoundImage.visibility = View.VISIBLE
            binding.nothingFoundText.visibility = View.VISIBLE
        } else {
            binding.rvCitySelector.visibility = View.VISIBLE
            binding.nothingFoundImage.visibility = View.GONE
            binding.nothingFoundText.visibility = View.GONE
        }
    }

    private fun onLoading(isLoading: Boolean) {
        binding.apply {
            if (isLoading) {
                rvCitySelector.visibility = View.GONE
                loader.visibility = View.VISIBLE
            } else {
                rvCitySelector.visibility = View.VISIBLE
                loader.visibility = View.GONE
            }
        }
    }

    private fun setupFields(cityList: List<City>) {
        binding.apply {
            findCityEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    viewModel.onAction(CityListAction.SearchCity(p0.toString(), cityList))
                }

                override fun afterTextChanged(p0: Editable?) {}
            })
        }
    }

    private fun setupButtons() {
        binding.buttonBack.setOnClickListener { dismiss() }
    }

    private fun backWithData(city: City) {
        if (args.fromOrToCity == "from") {
            findNavController().previousBackStackEntry?.savedStateHandle?.set("from", city)
        }
        if (args.fromOrToCity == "to") {
            findNavController().previousBackStackEntry?.savedStateHandle?.set("to", city)
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
