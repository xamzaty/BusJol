package kz.busjol.ui.passenger_quantity

sealed class PassengerQuantityAction {
    data class ChangePassengerQuantity(val isAdd: Boolean, val type: PassengerType): PassengerQuantityAction()
}