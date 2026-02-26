package com.example.booksapp.ui.screen.main

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.booksapp.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class BookItemTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private fun renderBookItem(
        title: String = "Clean Code",
        authors: List<String>? = listOf("Robert Martin"),
        thumbnail: String? = null,
        pageCount: Int = 431,
        averageRating: Double? = 4.5,
        onClick: () -> Unit = {}
    ) {
        composeTestRule.setContent {
            BookItem(
                title = title,
                authors = authors,
                thumbnail = thumbnail,
                pageCount = pageCount,
                averageRating = averageRating,
                onClick = onClick
            )
        }
    }

    @Test
    fun bookItem_showsTitle() {
        renderBookItem(title = "Clean Code")

        composeTestRule
            .onNodeWithText("Clean Code")
            .assertIsDisplayed()
    }

    @Test
    fun bookItem_showsAuthors() {
        renderBookItem(authors = listOf("Robert Martin", "John Doe"))

        composeTestRule
            .onNodeWithText("Robert Martin, John Doe")
            .assertIsDisplayed()
    }

    @Test
    fun bookItem_nullAuthors_showsUnknownAuthor() {
        renderBookItem(authors = null)

        composeTestRule
            .onNodeWithText("Unknown Author")
            .assertIsDisplayed()
    }

    @Test
    fun bookItem_showsPageCount() {
        renderBookItem(pageCount = 431)

        composeTestRule
            .onNodeWithText("431 pages")
            .assertIsDisplayed()
    }

    @Test
    fun bookItem_showsRating() {
        renderBookItem(averageRating = 4.5)

        composeTestRule
            .onNodeWithText("Rating: 4.5")
            .assertIsDisplayed()
    }

    @Test
    fun bookItem_nullRating_showsNA() {
        renderBookItem(averageRating = null)

        composeTestRule
            .onNodeWithText("Rating: N/A")
            .assertIsDisplayed()
    }

    @Test
    fun bookItem_click_triggersOnClick() {
        var clicked = false
        renderBookItem(onClick = { clicked = true })

        composeTestRule
            .onNodeWithTag("book_item")
            .performClick()

        assert(clicked)
    }
}