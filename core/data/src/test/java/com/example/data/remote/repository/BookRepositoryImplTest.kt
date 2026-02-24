package com.example.data.remote.repository

import android.R.attr.description
import android.health.connect.datatypes.units.Volume
import android.icu.lang.UCharacter.GraphemeClusterBreak.T
import com.example.data.remote.api.BooksApiService
import com.example.data.remote.dto.BookResponse
import com.example.data.remote.dto.BooksResponse
import com.example.data.remote.dto.VolumeInfoResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.*

class BookRepositoryImplTest {

    private lateinit var apiService: BooksApiService
    private lateinit var repository: BookRepositoryImpl

    @Before
    fun setUp() {
        apiService = mockk<BooksApiService>()
        repository = BookRepositoryImpl(apiService)
    }

    @Test
    fun `searchBooks returns expected results`() = runTest {
        val fakeResponse = BooksResponse(
            items = listOf(
                BookResponse(
                    id = "123",
                    volumeInfo = VolumeInfoResponse(
                        title = "Test Book 1",
                        authors = listOf("Author One", "Author Two"),
                        description = "Test Description 1",
                        imageLinks = null,
                        pageCount = 123,
                        averageRating = 2.0
                    ),
                ),
                BookResponse(
                    id = "456",
                    volumeInfo = VolumeInfoResponse(
                        title = "Test Book 2",
                        authors = listOf("Author One", "Author Two"),
                        description = "Test Description 2",
                        imageLinks = null,
                        pageCount = 456,
                        averageRating = 2.5
                    ),
                ),
                BookResponse(
                    id = "789",
                    volumeInfo = VolumeInfoResponse(
                        title = "Test Book 3",
                        authors = listOf("Author One", "Author Two"),
                        description = "Test Description 3",
                        imageLinks = null,
                        pageCount = 789,
                        averageRating = 2.6
                    ),
                )
            ),
            totalItems = 3
        )
        coEvery {
            apiService.searchBooks("test")
        } returns fakeResponse

        val response = repository.searchBooks("test")

        assertTrue(response.isSuccess)
        assertEquals(3, response.getOrNull()?.size)
        assertEquals("123", response.getOrNull()?.get(0)?.id)
    }

    @Test
    fun `searchBooks returns empty list when no items found`() = runTest {
        val fakeResponse = BooksResponse(
            items = emptyList(),
            totalItems = 0
        )
        coEvery {
            apiService.searchBooks("test")
        } returns fakeResponse

        val response = repository.searchBooks("test")

        assertTrue(response.isSuccess)
        assertTrue(response.getOrNull()?.isEmpty() ?: false)
    }
}