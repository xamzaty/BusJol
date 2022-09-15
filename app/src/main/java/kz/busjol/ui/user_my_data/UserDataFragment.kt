package kz.busjol.ui.user_my_data

import android.os.Bundle
import android.service.autofill.UserData
import android.view.View
import android.widget.Toast
import androidx.core.view.isEmpty
import androidx.navigation.fragment.findNavController
import kz.busjol.R
import kz.busjol.base.BaseFragment
import kz.busjol.databinding.FragmentUserDataBinding
import kz.busjol.domain.model.MyData
import kz.busjol.ui.search_journey.SearchJourneyViewState
import kz.busjol.utils.state.ViewState
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDataFragment :
    BaseFragment<FragmentUserDataBinding>(FragmentUserDataBinding::inflate) {

    private val viewModel: UserDataViewModel by viewModel()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupButtons()
        setupTextFields()
        setupObservers()
    }

    private fun setupButtons() {
        binding.apply {
            backButton.setOnClickListener {
                findNavController().popBackStack()
            }

            button.apply {
                setTitle(R.string.save_button)
                onSetClickListener {
                    isAnyFieldsEmpty {
                        viewModel.onAction(UserDataAction.OnSaveButtonClicked(MyData(
                            lastname = binding.lastNameEt.toString(),
                            firstname = binding.firstNameEt.toString(),
                            birthDate = binding.dateEt.toString(),
                            phoneNumber = binding.phoneEt.toString()
                        )))
                    }
                }
            }
        }
    }

    private fun isAnyFieldsEmpty(body: () -> Unit) {
        if (binding.lastNameEt.isEmpty() || binding.firstNameEt.isEmpty() ||
            binding.dateEt.isEmpty() || binding.phoneEt.isEmpty()) body()
        else emptyFieldsToast()
    }

    private fun emptyFieldsToast() =
        Toast.makeText(activity, R.string.fill_all_the_fields, Toast.LENGTH_SHORT).show()

    private fun setupTextFields() {
        binding.apply {
            lastNameEt.apply {
                setTitle(R.string.last_name)
                setHint(R.string.last_name_hint)
            }

            firstNameEt.apply {
                setTitle(R.string.first_name)
                setHint(R.string.first_name_hint)
            }

            dateEt.apply {
                enableCalendarView(activity, false)
                setTitle(R.string.birthday_date)
            }

            phoneEt.enablePhoneField()
        }
    }

    private fun setupObservers() {
        viewModel.apply {
            viewState.observe(viewLifecycleOwner) {
                handleViewStateChanges(it)
            }
        }
    }

    private fun handleViewStateChanges(state: ViewState<UserDataViewState>) {
        when (state) {
            is ViewState.Data -> {
                handleViewState(state.data)
            }
            is ViewState.Error -> {
                onError()
            }
            is ViewState.Loading -> Unit
        }
    }

    private fun handleViewState(state: UserDataViewState) {
        when (state) {
            is UserDataViewState.InitUserData -> Unit
        }
    }
}