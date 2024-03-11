package com.faigenbloom.testtask

import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.faigenbloom.testtask.ui.send.compose.MAIN_SCREEN_SCROLL
import com.faigenbloom.testtask.ui.send.compose.PURPOSE_TYPE
import com.faigenbloom.testtask.ui.send.compose.TRANSFER_BUTTON
import com.faigenbloom.testtask.ui.send.compose.containerDescriptionFor

abstract class BaseTestSuite {
    protected fun waitForIdle() = getTestRule().waitForIdle()
    protected fun input(amount: String) =
        onSendField().performTextReplacement(amount)

    protected fun onSendField() =
        getTestRule().onNodeWithTag(getString(R.string.send_funds_field_title_send))

    protected fun onSendFieldContainer() =
        getTestRule().onNodeWithTag(containerDescriptionFor(getString(R.string.send_funds_field_title_send)))

    protected fun onTransferButton() =
        getTestRule().onNodeWithTag(TRANSFER_BUTTON)

    protected fun onPurposeType() =
        getTestRule().onNodeWithTag(PURPOSE_TYPE, useUnmergedTree = true)

    protected fun scrollToBottom() {
        onTransferButton().performScrollTo()
        getTestRule().onNodeWithTag(MAIN_SCREEN_SCROLL)
            .performTouchInput { swipeUp(startY = 500f, endY = 0f) }
    }

    protected abstract fun getTestRule(): AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

    protected fun getString(@StringRes id: Int): String {
        return getTestRule().activity.getString(id)
    }

    fun borderColor(
        color: Color,
    ): SemanticsMatcher {
        val propertyName = "borderColor"
        return SemanticsMatcher(
            "$propertyName is '$color'",
        ) {
            it.layoutInfo.getModifierInfo().filter { modifierInfo ->
                modifierInfo.modifier == Modifier.border(
                    width = 1.dp,
                    color = color,
                    shape = RoundedCornerShape(8.dp),
                )
            }.size == 1
        }
    }

    fun checkBoxBorderColor(
        color: Color,
    ): SemanticsMatcher {
        val propertyName = "borderColor"
        return SemanticsMatcher(
            "$propertyName is '$color'",
        ) {
            it.layoutInfo.getModifierInfo().filter { modifierInfo ->
                modifierInfo.modifier == Modifier.border(
                    width = 2.dp,
                    color = color,
                    shape = RoundedCornerShape(4.dp),
                )
            }.size == 1
        }
    }
}

