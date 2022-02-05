package kz.busjol.ui.search

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.dialog_city_selector.*
import kz.busjol.R
import kz.busjol.base.AdapterListener
import kz.busjol.data.City
import kz.busjol.databinding.DialogCitySelectorBinding

class CityPickerDialog : BottomSheetDialogFragment(), CityListAdapter.OnItemClickListener {
    private lateinit var cityPickerViewModel: SearchViewModel
    private var _binding: DialogCitySelectorBinding? = null

    private val binding get() = _binding!!
    private val cityAdapter = CityListAdapter(this)
    private val args: CityPickerDialogArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        cityPickerViewModel = ViewModelProvider(this)[SearchViewModel::class.java]

        _binding = DialogCitySelectorBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = BottomSheetDialog(requireContext(), theme)
        dialog.setOnShowListener {

            val bottomSheetDialog = it as BottomSheetDialog
            val parentLayout = bottomSheetDialog.findViewById<View>(R.id.design_bottom_sheet)
            parentLayout?.let { it ->
                val behaviour = BottomSheetBehavior.from(it)
                setupFullHeight(it)
                behaviour.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
        return dialog
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonBack.setOnClickListener {
            dismiss()
        }

        setupObservers()
        loadData()
    }

    override fun onCityClicked(city: City) {
        backWithData(city.name)
        dialog?.dismiss()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
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

    private fun loadData() {
        binding.apply {
            rv_city_selector.apply {
                adapter = cityAdapter
                layoutManager = LinearLayoutManager(context)
            }
        }
    }
}