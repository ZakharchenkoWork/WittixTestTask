package com.faigenbloom.testtask.ui.common

import com.faigenbloom.testtask.R
import java.util.Currency

const val EUR: String = "EUR"
const val ILS: String = "ILS"
fun Currency.getFlag(): Int {
    return when (this.currencyCode) {
        EUR -> R.drawable.icon_flag_eu
        ILS -> R.drawable.icon_flag_israel
        else -> R.drawable.icon_flag_eu
    }
}
