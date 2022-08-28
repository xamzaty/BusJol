package kz.busjol.ui.journey

import kz.busjol.domain.model.JourneyData

sealed class JourneyViewState {
    data class JourneyDataInit(val journeyData: JourneyData): JourneyViewState()
}