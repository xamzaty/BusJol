package kz.busjol.ui.booking

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.navArgs
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentBookingBinding
import kz.busjol.ui.passenger_data.PassengerDataAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookingFragment : BaseFragment<FragmentBookingBinding>(FragmentBookingBinding::inflate) {

    private val viewModel: BookingViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


    }
}