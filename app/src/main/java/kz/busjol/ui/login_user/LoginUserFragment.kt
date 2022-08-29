package kz.busjol.ui.login_user

import android.os.Bundle
import android.text.InputType
import android.text.method.PasswordTransformationMethod
import android.view.View
import androidx.navigation.fragment.findNavController
import kz.busjol.BuildConfig
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentLoginUserBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

private const val DRIVER_TEST_MAIL = "driver@gmail.com"
private const val USER_TEST_MAIL = "user@gmail.com"
private const val PASSWORD_TEST = "12345"

class LoginUserFragment : BaseFragment<FragmentLoginUserBinding>(FragmentLoginUserBinding::inflate) {

    private val viewModel: LoginUserViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupTextFields()
        setupButtons()
    }

    private fun setupTextFields() {
        binding.apply {
            emailEt.enableMailField()
            passwordEt.enablePasswordField()
        }
    }

    private fun setupButtons() {
        binding.apply {

            closeButton.setOnClickListener {
                findNavController().popBackStack()
            }

            continueButton.apply {
                setTitle(R.string.enter_button)
                continueButton.onSetClickListener {
                    authorizeUser()
                }
            }
        }
    }

    private fun authorizeUser() {
        if (BuildConfig.DEBUG) {
            if (binding.emailEt.getText() == DRIVER_TEST_MAIL && binding.passwordEt.getText() == PASSWORD_TEST) {
                viewModel.setDriverStatus(true)
            }

            if (binding.emailEt.getText() == USER_TEST_MAIL && binding.passwordEt.getText() == PASSWORD_TEST) {
                viewModel.setUserStatus(true)
            }
        }
    }
}