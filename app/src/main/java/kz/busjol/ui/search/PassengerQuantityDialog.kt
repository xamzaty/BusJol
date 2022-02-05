package kz.busjol.ui.search

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kz.busjol.R
import kz.busjol.databinding.DialogPassengerQuantityBinding

class PassengerQuantityDialog() : BottomSheetDialogFragment() {
    private var _binding: DialogPassengerQuantityBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DialogPassengerQuantityBinding.inflate(inflater, container, false)

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

        setupButtons()
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)

        if (countPassengers() > 0)
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "quantity",
                passengersQuantityText()
            )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupFullHeight(bottomSheet: View) {
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = WindowManager.LayoutParams.MATCH_PARENT
        bottomSheet.layoutParams = layoutParams
    }

    private fun setupButtons() {

        var adultQuantity = 0
        var childQuantity = 0
        var disabledQuantity = 0

        binding.apply {

            buttonPlusAdultTariff.setOnClickListener {
                if (adultQuantity < 50) adultQuantity++
                textAdultQuantity.text = adultQuantity.toString()
            }

            buttonMinusAdultTariff.setOnClickListener {
                if (adultQuantity > 0) adultQuantity--
                textAdultQuantity.text = adultQuantity.toString()
            }


            buttonPlusChildTariff.setOnClickListener {
                if (childQuantity < 50) childQuantity++
                textChildQuantity.text = childQuantity.toString()
            }

            buttonMinusChildTariff.setOnClickListener {
                if (childQuantity > 0) childQuantity--
                textChildQuantity.text = childQuantity.toString()
            }


            buttonPlusDisableTariff.setOnClickListener {
                if (disabledQuantity < 50) disabledQuantity++
                textDisableQuantity.text = disabledQuantity.toString()
            }

            buttonMinusDisableTariff.setOnClickListener {
                if (disabledQuantity > 0) disabledQuantity--
                textDisableQuantity.text = disabledQuantity.toString()
            }

            closeButton.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun countPassengers(): Int {
        binding.apply {
            val adultQuantity = textAdultQuantity.text.toString().toInt()
            val childQuantity = textChildQuantity.text.toString().toInt()
            val disabledQuantity = textDisableQuantity.text.toString().toInt()

            return adultQuantity + childQuantity + disabledQuantity
        }
    }

    private fun passengersQuantityText(): String {
        if (countPassengers() == 1 || countPassengers() in 5..21 || countPassengers() in 25..31 || countPassengers() in 35..41) {
            return "${countPassengers()} ${getString(R.string.one_person)}"
        }

        return if (countPassengers() in 2..4 || countPassengers() in 22..24 || countPassengers() in 32..34 || countPassengers() in 42..44) {
            "${countPassengers()} ${getString(R.string.two_person)}"
        } else {
            "${countPassengers()} ${getString(R.string.one_person)}"
        }
    }
}