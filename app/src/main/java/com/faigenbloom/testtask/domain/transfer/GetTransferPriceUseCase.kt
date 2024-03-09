package com.faigenbloom.testtask.domain.transfer

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GetTransferPriceUseCase {
    suspend operator fun invoke(): String {
        return withContext(Dispatchers.IO) {
            "50"
        }
    }
}
