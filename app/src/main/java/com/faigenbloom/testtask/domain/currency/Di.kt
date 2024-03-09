package com.faigenbloom.testtask.domain.currency

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val currencyModule = module {
    singleOf(::GetAllCurrenciesUseCase)
    singleOf(::GetMainCurrencyUseCase)
    singleOf(::GetReceiverCurrencyUseCase)
}
