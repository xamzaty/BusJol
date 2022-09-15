package kz.busjol.ui.password_recovery

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentPasswordRecoveryBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class PasswordRecoveryFragment : BaseFragment<FragmentPasswordRecoveryBinding>(FragmentPasswordRecoveryBinding::inflate) {

    private val viewModel: PasswordRecoveryViewModel by viewModel()

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
                setTitle(R.string.send_button)
                setOnClickListener {

                }
            }
        }
    }

}