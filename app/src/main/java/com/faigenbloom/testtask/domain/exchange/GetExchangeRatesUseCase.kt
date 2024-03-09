package com.faigenbloom.testtask.domain.exchange

import com.faigenbloom.testtask.ui.common.EUR
import com.faigenbloom.testtask.ui.common.ILS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Currency

class GetExchangeRatesUseCase {
    suspend operator fun invoke(from: Currency, to: Currency): ExchangeRatesModel {
        return withContext(Dispatchers.IO) {
            if (from.currencyCode == EUR && to.currencyCode == ILS) {
                ExchangeRatesModel(
                    from = "3.61206",
                    to = "0.28",
                )
            } else if (
                from.currencyCode == ILS && to.currencyCode == EUR
            ) {
                ExchangeRatesModel(
                    from = "0.28",
                    to = "3.61206",
                )
            } else {
                ExchangeRatesModel(
                    from = "2",
                    to = "3",
                )
            }
        }
    }
}

data class ExchangeRatesModel(
    val from: String,
    val to: String,
)
