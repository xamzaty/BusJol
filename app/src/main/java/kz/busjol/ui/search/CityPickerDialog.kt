package kz.busjol.ui.search

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_city_selector.*
import kz.busjol.R
import kz.busjol.base.BaseBottomFragmentDialog
import kz.busjol.data.City
import kz.busjol.databinding.DialogCitySelectorBinding

class CityPickerDialog : BaseBottomFragmentDialog<DialogCitySelectorBinding>(DialogCitySelectorBinding::inflate), CityListAdapter.OnItemClickListener {
    private val cityPickerViewModel: SearchViewModel by viewModels()

    private val cityAdapter = CityListAdapter(this)
    private val args: CityPickerDialogArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            dismiss()
        }

        setupObservers()
        setupFields()
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

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    private fun setupObservers() {
        cityPickerViewModel.apply {
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
                    cityPickerViewModel.onAction(SearchFragmentAction.SearchCity(p0.toString()))
                }

                override fun afterTextChanged(p0: Editable?) {
                }

            })
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