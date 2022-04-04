package kz.busjol.ui.trip_details

import android.os.Bundle
import android.view.View
import kz.busjol.base.BaseBottomFragmentDialog
import kz.busjol.databinding.DialogTripDetailsBinding
import kz.busjol.ui.passenger_data.PassengerDataViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class TripDetailsDialog : BaseBottomFragmentDialog<DialogTripDetailsBinding>(DialogTripDetailsBinding::inflate, false) {

    private val viewModel: PassengerDataViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }
}