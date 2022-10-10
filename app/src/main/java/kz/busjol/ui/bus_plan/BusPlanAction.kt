package kz.busjol.ui.bus_plan

sealed class BusPlanAction {
    data class AddItemsToBusPlanList(val item: Int) : BusPlanAction()
    data class RemoveItemsFromBusPlanList(val item: Int) : BusPlanAction()
    object RemoveAllItemsFromBusPlanList: BusPlanAction()
}