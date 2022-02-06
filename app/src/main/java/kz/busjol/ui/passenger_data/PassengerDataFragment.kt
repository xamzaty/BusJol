package kz.busjol.ui.passenger_data

import androidx.fragment.app.viewModels
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentPassengerDataBinding

class PassengerDataFragment : BaseFragment<FragmentPassengerDataBinding>(FragmentPassengerDataBinding::inflate) {

    private val passengerDataViewModel: PassengerDataViewModel by viewModels()

}