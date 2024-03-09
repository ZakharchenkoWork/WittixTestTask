@file:OptIn(ExperimentalAnimationApi::class)

package com.faigenbloom.testtask.ui.common.animations

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.IntSize

@Composable
fun AnimatedVisibility(
    isVisible: Boolean,
    content: @Composable AnimatedContentScope.(targetState: Boolean) -> Unit,
) {
    AnimatedContent(
        targetState = isVisible,
        transitionSpec = {
            fadeIn(animationSpec = tween(durationMillis = 150)) togetherWith
                fadeOut(animationSpec = tween(durationMillis = 150)) using
                SizeTransform { initialSize, targetSize ->
                    if (targetState) {
                        keyframes {
                            IntSize(initialSize.width, initialSize.height) at 150
                            durationMillis = 300
                        }
                    } else {
                        keyframes {
                            IntSize(targetSize.width, targetSize.height) at 150
                            durationMillis = 300
                        }
                    }
                }
        },
        content = content,
        label = "AnimatedContent",
    )
}
