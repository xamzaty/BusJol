package kz.busjol.ui.booking

import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentBookingBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class BookingFragment : BaseFragment<FragmentBookingBinding>(FragmentBookingBinding::inflate) {
    private val viewModel: BookingViewModel by viewModel()
}