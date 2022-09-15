package kz.busjol.ui.user_my_data

import android.service.autofill.UserData
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kz.busjol.base.BaseViewModel
import kz.busjol.domain.model.MyData
import kz.busjol.utils.state.ViewState

class UserDataViewModel : BaseViewModel() {

    private val _viewState = MutableLiveData<ViewState<UserDataViewState>>()
    val viewState: LiveData<ViewState<UserDataViewState>> = _viewState

    init {

    }

    private fun saveNewPassengerData(myData: MyData) {
        _viewState.postValue(
            ViewState.Data(
                UserDataViewState.InitUserData(myData)
            )
        )
    }

    fun onAction(action: UserDataAction) {
        when (action) {
            is UserDataAction.OnSaveButtonClicked -> saveNewPassengerData(action.myData)
        }
    }
}