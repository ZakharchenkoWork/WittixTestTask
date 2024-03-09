package com.faigenbloom.testtask.ui.send

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.faigenbloom.testtask.ui.common.BaseDestination
import com.faigenbloom.testtask.ui.common.DocumentPickerContract
import com.faigenbloom.testtask.ui.common.DocumentRequest
import org.koin.androidx.compose.koinViewModel

const val REASON_SENDING: String = "Sending"
fun NavGraphBuilder.sendPage(
    padding: PaddingValues,
    onTransfer: () -> Unit,
) {
    composable(
        route = SendPageRoute(),
    ) {
        val viewModel = koinViewModel<SendPageViewModel>()
        val launcher = rememberLauncherForActivityResult(DocumentPickerContract()) {
            it?.let {
                if (it.reason == REASON_SENDING) {
                    it.uri?.let { uri ->
                        viewModel.onDocumentLoaded(uri)
                    }
                }
            }
        }

        viewModel.onTransfer = onTransfer
        viewModel.onDocumentPickerRequested = {
            launcher.launch(DocumentRequest(REASON_SENDING))
        }

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
