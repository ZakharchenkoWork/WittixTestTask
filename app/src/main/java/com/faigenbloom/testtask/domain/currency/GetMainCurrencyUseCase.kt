package com.faigenbloom.testtask.domain.currency

import com.faigenbloom.testtask.ui.common.EUR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Currency

class GetMainCurrencyUseCase {
    suspend operator fun invoke(): Currency {
        return withContext(Dispatchers.IO) {
            Currency.getInstance(EUR)
        }
    }
}
