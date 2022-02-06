package kz.busjol.ui.passenger_data

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kz.busjol.base.BaseBottomFragmentDialog
import kz.busjol.databinding.DialogTripDetailsBinding

class TripDetailsDialog : BaseBottomFragmentDialog<DialogTripDetailsBinding>(DialogTripDetailsBinding::inflate) {
    private lateinit var passengersDataViewModel: PassengerDataViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.closeButton.setOnClickListener {
            dismiss()
        }
    }
}