package kz.busjol.ui.enter_user

import android.os.Bundle
import android.view.View
import kz.busjol.BuildConfig
import kz.busjol.base.BaseBottomFragmentDialog
import kz.busjol.databinding.DialogEnterUserBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val DRIVER_TEST_MAIL = "driver@gmail.com"
private const val USER_TEST_MAIL = "user@gmail.com"
private const val PASSWORD_TEST = "12345"

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
            if (binding.emailEt.text.toString() == DRIVER_TEST_MAIL && binding.passwordEt.text.toString() == PASSWORD_TEST) {
                viewModel.setDriverStatus(true)
            }

            if (binding.emailEt.text.toString() == USER_TEST_MAIL && binding.passwordEt.text.toString() == PASSWORD_TEST) {
                viewModel.setUserStatus(true)
            }
        }
    }
}