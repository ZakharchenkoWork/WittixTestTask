package com.faigenbloom.testtask

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollTo
import androidx.compose.ui.test.performTextReplacement
import androidx.test.espresso.Espresso
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.faigenbloom.testtask.ui.send.PurposeType
import com.faigenbloom.testtask.ui.send.compose.AGREEMENT_CHECKBOX
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class SendPageTests : BaseTestSuite() {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    override fun getTestRule() = composeTestRule

    @Test
    fun text_fields_shows_correct_formatting() {
        waitForIdle()
        onSendFieldContainer().assert(borderColor(Color(0xFFE3E6E9)))
        onSendField().performTextReplacement("100000")
        onSendFieldContainer().assert(borderColor(Color(0xFF2431E0)))
        onSendField().assert(hasText("100,000"))

        onSendField().performTextReplacement("100.32")
        onSendField().assert(hasText("100.32"))
    }

    @Test
    fun transfer_shows_error() {
        waitForIdle()
        scrollToBottom()
        onTransferButton().performClick()
        onSendField().performScrollTo()
        onSendFieldContainer().assert(borderColor(Color(0xFFE024CD)))

        onSendField().performTextReplacement("100000")
        Espresso.closeSoftKeyboard()
        scrollToBottom()
        onTransferButton().performClick()

        onPurposeType().assert(borderColor(Color(0xFFE024CD)))

        onPurposeType().performClick()
        getTestRule().onNodeWithTag(PurposeType.ADVERTISING.name, useUnmergedTree = true)
            .performClick()
        scrollToBottom()
        onTransferButton().performClick()
        getTestRule().onNodeWithTag(AGREEMENT_CHECKBOX, useUnmergedTree = true)
            .assert(checkBoxBorderColor(Color(0xFFE024CD)))
    }
}