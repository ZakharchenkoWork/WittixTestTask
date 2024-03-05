package com.faigenbloom.testtask.ui.send

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.faigenbloom.testtask.ui.theme.TestTaskTheme

@Composable
fun SendPage(
    modifier: Modifier = Modifier,
    state: SendPageState,
) {
    Column(modifier = modifier) {
        TopBar()
    }
}
@Composable
fun TopBar(){

}
@Preview(showBackground = true)
@Composable
fun SendPagePreview() {
    TestTaskTheme {
        Surface {
            SendPage(
                state = SendPageState(),
            )
        }
    }
}
