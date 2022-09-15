package kz.busjol.ui.user_my_data

import kz.busjol.domain.model.MyData

sealed class UserDataViewState {
    data class InitUserData(val userData: MyData): UserDataViewState()
}