package kz.busjol.ui.passenger_data

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentPassengerDataBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PassengerDataFragment : BaseFragment<FragmentPassengerDataBinding>(FragmentPassengerDataBinding::inflate) {

    private val viewModel: PassengerDataViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
    }

    private fun setupButtons() {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }
        }
    }

}