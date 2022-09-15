package kz.busjol.ui.passenger_quantity

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kz.busjol.R
import kz.busjol.base.BaseBottomFragmentDialog
import kz.busjol.databinding.DialogPassengerQuantityBinding
import kz.busjol.ext.setQuantityButtonAction

class PassengerQuantityDialog :
    BaseBottomFragmentDialog<DialogPassengerQuantityBinding>(
        DialogPassengerQuantityBinding::inflate,
        false
    ) {

    private val args: PassengerQuantityDialogArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupFields()
    }

    private fun setupButtons() {
        val quantity = args.passengerData!!

        binding.apply {
            buttonPlusAdultTariff.setOnClickListener {
                if (quantity.adultQuantity < 50) quantity.adultQuantity++
                textAdultQuantity.text = quantity.adultQuantity.toString()
            }

            buttonMinusAdultTariff.setOnClickListener {
                if (quantity.adultQuantity > 1 || (quantity.disabledQuantity > 0 && quantity.adultQuantity > 0)) quantity.adultQuantity--
                textAdultQuantity.text = quantity.adultQuantity.toString()
            }

            buttonPlusChildTariff.setOnClickListener {
                if (quantity.childQuantity < 50) quantity.childQuantity++
                textChildQuantity.text = quantity.childQuantity.toString()
            }

            buttonMinusChildTariff.setOnClickListener {
                if (quantity.childQuantity > 0) quantity.childQuantity--
                textChildQuantity.text = quantity.childQuantity.toString()
            }

            buttonPlusDisableTariff.setOnClickListener {
                if (quantity.disabledQuantity < 50) quantity.disabledQuantity++
                textDisableQuantity.text = quantity.disabledQuantity.toString()
            }

            buttonMinusDisableTariff.setOnClickListener {
                if (quantity.disabledQuantity > 0) quantity.disabledQuantity--
                textDisableQuantity.text = quantity.disabledQuantity.toString()
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
            textAdultQuantity.text = args.passengerData?.adultQuantity.toString()
            textChildQuantity.text = args.passengerData?.childQuantity.toString()
            textDisableQuantity.text = args.passengerData?.disabledQuantity.toString()
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
        val allQuantity = "quantity"
        val data = "journeyData"

        if (countPassengers() > 0) {
            backstackData(allQuantity, passengersQuantityText())
            backstackData(data, args.passengerData!!)
        }
    }

    private fun backstackData(key: String, value: Any) =
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, value)

    private fun passengersQuantityText() = when {
        countPassengers() == 1 || countPassengers() in 5..21 || countPassengers() in 25..31 || countPassengers() in 35..41 ->
            "${countPassengers()} ${getString(R.string.one_person)}"
        countPassengers() in 2..4 || countPassengers() in 22..24 || countPassengers() in 32..34 || countPassengers() in 42..44 ->
            "${countPassengers()} ${getString(R.string.two_person)}"
        else ->
            "${countPassengers()} ${getString(R.string.one_person)}"
    }
}