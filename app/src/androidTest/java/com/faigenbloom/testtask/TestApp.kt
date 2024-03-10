package com.faigenbloom.testtask

import com.faigenbloom.testtask.domain.balance.balanceModule
import com.faigenbloom.testtask.domain.currency.currencyModule
import com.faigenbloom.testtask.domain.exchange.exchangeRatesModule
import com.faigenbloom.testtask.domain.transfer.transferModule
import com.faigenbloom.testtask.ui.send.sendingModule

class TestApp : App() {
    override fun onCreate() {
        super.onCreate()
        koinApplication.modules(
            sendingModule,
            currencyModule,
            balanceModule,
            exchangeRatesModule,
            transferModule,
        )
    }
}

