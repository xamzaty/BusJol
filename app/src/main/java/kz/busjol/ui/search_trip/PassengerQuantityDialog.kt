package kz.busjol.ui.search_trip

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatTextView
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kz.busjol.R
import kz.busjol.base.BaseBottomFragmentDialog
import kz.busjol.databinding.DialogPassengerQuantityBinding

class PassengerQuantityDialog : BaseBottomFragmentDialog<DialogPassengerQuantityBinding>(DialogPassengerQuantityBinding::inflate, false) {

    private val args: PassengerQuantityDialogArgs by navArgs()

    var adultQuantity = 1
    var childQuantity = 0
    var disabledQuantity = 0

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupFields()
        test()
    }

    private fun setupButtons() {
        binding.apply {
            buttonPlusAdultTariff.setOnClickListener {
                if (adultQuantity < 50) adultQuantity++
                textAdultQuantity.text = adultQuantity.toString()
            }

            buttonMinusAdultTariff.setOnClickListener {
                if (adultQuantity > 1) adultQuantity--
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

    @RequiresApi(Build.VERSION_CODES.M)
    private fun test() {
        binding.apply {
            if (textAdultQuantity.text.toString().toInt() < 1) {
                buttonMinusAdultTariff.setTextColor(requireContext().getColor(R.color.black))
                buttonMinusAdultTariff.setBackgroundResource(R.drawable.ic_quantity_gray_button)
            } else {
                buttonMinusAdultTariff.setTextColor(requireContext().getColor(R.color.white))
                buttonMinusAdultTariff.setBackgroundResource(R.drawable.ic_quantity_blue_button)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setupButtonColors() {
        binding.apply {
            setButtonColors(textAdultQuantity, 1, buttonMinusAdultTariff, false)
            setButtonColors(textAdultQuantity, 50, buttonPlusAdultTariff, true)
            setButtonColors(textChildQuantity, 0, buttonMinusChildTariff, false)
            setButtonColors(textChildQuantity, 50, buttonPlusChildTariff, true)
            setButtonColors(textDisableQuantity, 0, buttonMinusDisableTariff, false)
            setButtonColors(textDisableQuantity, 50, buttonPlusDisableTariff, true)
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun setButtonColors(quantityTv: AppCompatTextView, quantity: Int, button: AppCompatTextView, isPlus: Boolean) {
        if (!isPlus) {
            if (quantityTv.text.toString().toInt() > quantity) {
                button.setTextColor(requireContext().getColor(R.color.white))
                button.setBackgroundResource(R.drawable.ic_quantity_blue_button)
            }
            else {
                button.setTextColor(requireContext().getColor(R.color.black))
                button.setBackgroundResource(R.drawable.ic_quantity_gray_button)
            }
        }
        else {
            if (quantityTv.text.toString().toInt() < quantity) {
                button.setTextColor(requireContext().getColor(R.color.white))
                button.setBackgroundResource(R.drawable.ic_quantity_blue_button)
            } else {
                button.setTextColor(requireContext().getColor(R.color.black))
                button.setBackgroundResource(R.drawable.ic_quantity_gray_button)
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

    private fun passBackstackData() {
        val allQuantity = "quantity"
        val adultQuantity = "adultQuantity"
        val childQuantity = "childQuantity"
        val disabledQuantity = "disabledQuantity"

        if (countPassengers() > 0) {
            backstackData(allQuantity, passengersQuantityText())
            backstackData(adultQuantity, binding.textAdultQuantity.text.toString().toInt())
            backstackData(childQuantity, binding.textChildQuantity.text.toString().toInt())
            backstackData(disabledQuantity, binding.textDisableQuantity.text.toString().toInt())
        }
    }

    private fun backstackData(key: String, value: Any) {
        findNavController().previousBackStackEntry?.savedStateHandle?.set(key, value)
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