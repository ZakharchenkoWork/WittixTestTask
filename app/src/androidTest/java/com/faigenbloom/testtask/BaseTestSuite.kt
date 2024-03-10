package com.faigenbloom.testtask

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.junit4.AndroidComposeTestRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.performTextReplacement
import androidx.compose.ui.unit.dp
import androidx.test.ext.junit.rules.ActivityScenarioRule
import com.faigenbloom.testtask.ui.send.compose.TRANSFER_BUTTON

abstract class BaseTestSuite {
    protected fun waitForIdle() = getTestRule().waitForIdle()
    protected fun input(amount: String) =
        onSendField().performTextReplacement(amount)

    protected fun onSendField() =
        getTestRule().onNodeWithContentDescription(getString(R.string.send_funds_field_title_send))

    protected fun onTransferButton() =
        getTestRule().onNodeWithContentDescription(TRANSFER_BUTTON)

    protected abstract fun getTestRule(): AndroidComposeTestRule<ActivityScenarioRule<MainActivity>, MainActivity>

    protected fun getString(@StringRes id: Int): String {
        return getTestRule().activity.getString(id)
    }

    fun backgroundColor(
        color: Color,
    ): SemanticsMatcher {
        val propertyName = "backgroundColor"
        return SemanticsMatcher(
            "$propertyName is '$color'",
        ) {
            it.layoutInfo.getModifierInfo().filter { modifierInfo ->
                modifierInfo.modifier == Modifier.background(color = color)
            }.size == 1
        }
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
}

