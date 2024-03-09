package com.faigenbloom.testtask.domain.exchange

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val exchangeRatesModule = module {
    singleOf(::GetExchangeRatesUseCase)
}
