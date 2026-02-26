package com.example.booksapp.ui.screen.main

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class SearchFieldTest {


    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun searchField_isDisplayed() {
        composeTestRule.setContent {
            SearchField(
                value = "",
                onValueChange = {},
                onSearch = {}
            )
        }

        composeTestRule.onNodeWithTag("search_field").assertIsDisplayed()
    }

    @Test
    fun inputText_callsOnValueChange() {
        var currValue = ""
        composeTestRule.setContent {
            SearchField(
                value = currValue,
                onValueChange = {
                    currValue = it
                },
                onSearch = {}
            )
        }
        composeTestRule.onNodeWithTag("search_field")
            .performTextInput("Test Query")

        assert(currValue == "Test Query")
    }

    @Test
    fun searchButton_callsOnSearch() {
        var searchCalled = false
        composeTestRule.setContent {
            SearchField(
                value = "",
                onValueChange = {},
                onSearch = {
                    searchCalled = true
                }
            )
        }
        composeTestRule.onNodeWithTag("search_button").performClick()

        assert(searchCalled)
    }
}