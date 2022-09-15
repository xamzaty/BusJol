package kz.busjol.ui.registration

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentRegistrationBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegistrationFragment :
        BaseFragment<FragmentRegistrationBinding>(FragmentRegistrationBinding::inflate) {

        private val viewModel: RegistrationViewModel by viewModel()

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
                super.onViewCreated(view, savedInstanceState)
                setupTextFields()
                setupButtons()
        }

        private fun setupTextFields() {
                binding.emailEt.enableMailField()
        }

        private fun setupButtons() {
                binding.apply {
                        backButton.setOnClickListener {
                                findNavController().popBackStack()
                        }

                        button.apply {
                                setTitle(R.string.button_registration)
                                onSetClickListener {

                                }
                        }
                }
        }
}