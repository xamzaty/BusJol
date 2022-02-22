package kz.busjol.ui.search

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kz.busjol.R
import kz.busjol.base.BaseBottomFragmentDialog
import kz.busjol.databinding.DialogPassengerQuantityBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PassengerQuantityDialog : BaseBottomFragmentDialog<DialogPassengerQuantityBinding>(DialogPassengerQuantityBinding::inflate, false) {

    private val args: PassengerQuantityDialogArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupButtons()
        setupFields()
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

            continueButton.setOnClickListener {
                passBackstackData()
                dismiss()
            }

            closeButton.setOnClickListener {
                dismiss()
            }
        }
    }

    private fun setupFields() {
        binding.apply {
            textAdultQuantity.text = args.adultsQuantity.toString()
            textChildQuantity.text = args.childQuantity.toString()
            textDisableQuantity.text = args.disabledQuantity.toString()
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

    private fun passBackstackData() {
        if (countPassengers() > 0) {
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "quantity",
                passengersQuantityText()
            )
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "adultQuantity",
                binding.textAdultQuantity.text.toString().toInt()
            )
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "childQuantity",
                binding.textChildQuantity.text.toString().toInt()
            )
            findNavController().previousBackStackEntry?.savedStateHandle?.set(
                "disabledQuantity",
                binding.textDisableQuantity.text.toString().toInt()
            )
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