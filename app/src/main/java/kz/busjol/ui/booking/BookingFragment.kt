package kz.busjol.ui.booking

import androidx.fragment.app.viewModels
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentBookingBinding

class BookingFragment : BaseFragment<FragmentBookingBinding>(FragmentBookingBinding::inflate) {
    private val bookingViewModel: BookingViewModel by viewModels()
}