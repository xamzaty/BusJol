package kz.busjol.ui.user_my_data

import kz.busjol.domain.model.MyData

sealed class UserDataAction {
    data class OnSaveButtonClicked(val myData: MyData): UserDataAction()
}