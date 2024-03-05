package com.faigenbloom.testtask.ui.send

import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val sendingModule = module {
    viewModelOf(::SendPageViewModel)
}
