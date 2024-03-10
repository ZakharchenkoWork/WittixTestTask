package com.faigenbloom.testtask

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
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
class ExampleInstrumentedTest : BaseTestSuite() {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()
    override fun getTestRule() = composeTestRule

    @Test
    fun text_fields_shows_correct_formatting() {
        waitForIdle()
        input("100000")

        onSendField()
            .assert(hasText("100,000"))
        input("100.32")
        onSendField()
            .assert(hasText("100.32"))
    }
}