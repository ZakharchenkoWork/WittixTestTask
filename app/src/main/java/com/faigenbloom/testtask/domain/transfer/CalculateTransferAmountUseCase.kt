package com.faigenbloom.testtask.domain.transfer

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.math.BigDecimal

class CalculateTransferAmountUseCase {
    suspend operator fun invoke(
        isForSend: Boolean,
        amount: String,
        rate: String,
        transferPrice: String,
    ): String {
        return withContext(Dispatchers.Default) {
            val amountDecimal: BigDecimal = amount.toBigDecimal()
            val rateDecimal: BigDecimal = rate.toBigDecimal()
            val transferPriceDecimal: BigDecimal = transferPrice.toBigDecimal()

            if (isForSend) {
                ((amountDecimal - transferPriceDecimal) * rateDecimal).toString()
            } else {
                ((amountDecimal / rateDecimal - transferPriceDecimal)).toString()
            }
        }
    }
}
