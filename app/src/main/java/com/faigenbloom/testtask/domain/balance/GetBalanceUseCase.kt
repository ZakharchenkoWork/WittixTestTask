package com.faigenbloom.testtask.domain.balance

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetBalanceUseCase {
    suspend operator fun invoke(): BalanceModel {
        return withContext(Dispatchers.IO) {
            BalanceModel(
                total = "2340",
                available = "1700.50",
            )
        }
    }
}

data class BalanceModel(
    val total: String,
    val available: String,
)
