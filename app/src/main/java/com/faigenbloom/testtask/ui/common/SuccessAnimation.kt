package com.faigenbloom.testtask.ui.common

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.faigenbloom.testtask.R

@Composable
fun Success(state: AnimationState) {
    if (state.isShown) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.success))
        val progress by animateLottieCompositionAsState(composition)
        if (progress < 1) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                LottieAnimation(
                    modifier = Modifier.fillMaxWidth(0.5f),
                    composition = composition,
                    progress = { progress },
                )
            }
        } else {
            state.onFinish()
        }
    }
}
data class AnimationState(
    val isShown: Boolean = false,
    val onFinish: () -> Unit = {},
)
