package com.faigenbloom.testtask

import android.app.Application
import com.faigenbloom.testtask.domain.balance.balanceModule
import com.faigenbloom.testtask.domain.currency.currencyModule
import com.faigenbloom.testtask.domain.exchange.exchangeRatesModule
import com.faigenbloom.testtask.domain.transfer.transferModule
import com.faigenbloom.testtask.ui.send.sendingModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

open class App : Application() {
    protected lateinit var koinApplication: KoinApplication
    override fun onCreate() {
        super.onCreate()

        koinApplication = startKoin {
            androidContext(this@App)
            modules(
                sendingModule,
                currencyModule,
                balanceModule,
                exchangeRatesModule,
                transferModule,
            )
        }
    }
}
