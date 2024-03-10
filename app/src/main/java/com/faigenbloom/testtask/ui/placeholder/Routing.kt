package com.faigenbloom.testtask.ui.placeholder

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.faigenbloom.testtask.ui.common.BaseDestination

const val LINKEDIN_LINK = "https://www.linkedin.com/in/konstantyn-zakharchenko"
fun NavGraphBuilder.placeholderPage(
    padding: PaddingValues,
) {
    composable(
        route = PlaceholderPageRoute(),
    ) {
        val uriHandler = LocalUriHandler.current

        PlaceholderPage(
            modifier = Modifier.padding(
                bottom = padding.calculateBottomPadding(),
            ),
            onNameClicked = {
                uriHandler.openUri(LINKEDIN_LINK)
            },
        )
    }
}

data object PlaceholderPageRoute : BaseDestination(
    route = "PlaceholderPageRoute",
) {
    operator fun invoke() = route
}
