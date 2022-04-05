package kz.busjol.ui.enter_user

import android.os.Bundle
import android.view.View
import kz.busjol.BuildConfig
import kz.busjol.base.BaseBottomFragmentDialog
import kz.busjol.databinding.DialogEnterUserBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class EnterUserDialog : BaseBottomFragmentDialog<DialogEnterUserBinding>(DialogEnterUserBinding::inflate, true) {

    private val viewModel: LoginUserViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
    }

    private fun setupButtons() {
        binding.apply {
            closeButton.setOnClickListener {
                dismiss()
            }
            continueButton.setOnClickListener {
                authorizeUser()
            }
        }
    }

    private fun authorizeUser() {
        if (BuildConfig.DEBUG) {
            if (binding.emailEt.text.toString() == "driver@gmail.com" && binding.passwordEt.text.toString() == "12345") {
                viewModel.setDriverStatus(true)
            }

            if (binding.emailEt.text.toString() == "user@gmail.com" && binding.passwordEt.text.toString() == "12345") {
                viewModel.setUserStatus(true)
            }
        }
    }
}