package kz.busjol.module

import kz.busjol.repository.CityRepository
import org.koin.dsl.module

val repoModule = module {
    single { CityRepository(api = get()) }
}