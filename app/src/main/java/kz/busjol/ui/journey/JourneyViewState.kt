package kz.busjol.ui.journey

import kz.busjol.domain.model.Journey

sealed class JourneyViewState {
    data class JourneyDataInit(val journeyList: List<Journey>): JourneyViewState()
}