package kz.busjol.module

import kz.busjol.data.network.repository.DefaultCityRepository
import kz.busjol.data.network.repository.DefaultJourneyRepository
import kz.busjol.domain.repository.CityRepository
import kz.busjol.domain.repository.JourneyRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val repoModule = module {

    factory {
        DefaultCityRepository(
            dataSource = get(),
            mapper = get()
        )
    }.bind(CityRepository::class)

    factory {
        DefaultJourneyRepository(
            dataSource = get(),
            mapper = get()
        )
    }.bind(JourneyRepository::class)
}