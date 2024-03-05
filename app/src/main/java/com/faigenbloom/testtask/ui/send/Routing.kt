package com.faigenbloom.testtask.ui.send

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.faigenbloom.testtask.ui.common.BaseDestination
import org.koin.androidx.compose.koinViewModel

fun NavGraphBuilder.sendPage(
    padding: PaddingValues,
    onTransfer: () -> Unit,
) {
    composable(
        route = SendPageRoute(),
    ) {
        val viewModel = koinViewModel<SendPageViewModel>()
        viewModel.onTransfer = onTransfer
        val state by viewModel.stateFlow.collectAsState()

        SendPage(
            modifier = Modifier.padding(
                bottom = padding.calculateBottomPadding(),
            ),
            state = state,
        )
    }
}
data object SendPageRoute : BaseDestination(
    route = "SendPageRoute",
) {
    operator fun invoke() = route
}
