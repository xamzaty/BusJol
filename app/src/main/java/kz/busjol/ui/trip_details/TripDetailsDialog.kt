package kz.busjol.ui.trip_details

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import kz.busjol.R
import kz.busjol.base.BaseBottomFragmentDialog
import kz.busjol.databinding.DialogTripDetailsBinding

class TripDetailsDialog : BaseBottomFragmentDialog<DialogTripDetailsBinding>(DialogTripDetailsBinding::inflate, false) {

    private val args: TripDetailsDialogArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initTextViews()

        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }

    private fun initTextViews() {
        binding.apply {
            tripCitiesTv.text = "${args.journeyData.fromCity?.name} - ${args.journeyData.toCity?.name}"
            tripTime.text = "13ч 30мин в пути"
            tripNumber.text = getString(R.string.trip_number, "1")
            tripFerrymen.text = getString(R.string.carrier, "Автовокзал")
            tripTimeFromCity.text = "09:00"
            tripDateFrom.text = "12 марта"
            tripTimeToCity.text = "14:20"
            tripDateTo.text = "13 марта"
            fromCity.text = "${args.journeyData.fromCity?.name}"
            toCity.text = "${args.journeyData.toCity?.name}"
        }
    }
}