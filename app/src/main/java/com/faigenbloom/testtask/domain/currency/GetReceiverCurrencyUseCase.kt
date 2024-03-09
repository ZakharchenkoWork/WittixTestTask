package com.faigenbloom.testtask.domain.currency

import com.faigenbloom.testtask.ui.common.ILS
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Currency

class GetReceiverCurrencyUseCase {
    suspend operator fun invoke(): Currency {
        return withContext(Dispatchers.IO) {
            Currency.getInstance(ILS)
        }
    }
}
