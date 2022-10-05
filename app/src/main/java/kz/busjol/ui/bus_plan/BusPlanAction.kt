package kz.busjol.ui.bus_plan

import kz.busjol.data.network.model.BusPlan

sealed class BusPlanAction {
    data class AddItemsToBusPlanList(val item: Int) : BusPlanAction()
}