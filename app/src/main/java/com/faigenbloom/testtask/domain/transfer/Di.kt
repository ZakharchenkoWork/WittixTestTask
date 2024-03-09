package com.faigenbloom.testtask.domain.transfer

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val transferModule = module {
    singleOf(::GetTransferPriceUseCase)
}
