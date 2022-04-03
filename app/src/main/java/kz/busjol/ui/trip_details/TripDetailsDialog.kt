package kz.busjol.ui.trip_details

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import kz.busjol.base.BaseBottomFragmentDialog
import kz.busjol.databinding.DialogTripDetailsBinding
import kz.busjol.ui.passenger_data.PassengerDataViewModel

class TripDetailsDialog : BaseBottomFragmentDialog<DialogTripDetailsBinding>(DialogTripDetailsBinding::inflate, false) {

    private val passengersDataViewModel: PassengerDataViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }
}