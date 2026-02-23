package com.example.data.mapper

import com.example.data.remote.dto.BookResponse
import com.example.data.remote.dto.ImageLinksResponse
import com.example.data.remote.dto.VolumeInfoResponse
import org.junit.Test
import org.junit.jupiter.api.Assertions.*



class MapperTest {

    @Test
    fun `toDomainModel returns correct Book class`() {
        val bookResponse = BookResponse(
            id = "123",
            volumeInfo = VolumeInfoResponse(
                title = "Test Book",
                authors = listOf("Author One", "Author Two"),
                description = "Test Description",
                imageLinks = ImageLinksResponse(
                    thumbnail = "thumbnail_url",
                    smallThumbnail = "small_thumbnail_url"
                ),
                pageCount = 100,
                averageRating = 10.0
            ),
        )
        val book = bookResponse.toDomainModel()

        assertEquals("123", book.id)
        assertEquals("Test Book", book.title)
        assertEquals(listOf("Author One", "Author Two"), book.authors)
        assertEquals("Test Description", book.description)
        assertEquals("thumbnail_url", book.thumbnail)
        assertEquals("small_thumbnail_url", book.smallThumbnail)
        assertEquals(100, book.pageCount)
        assertEquals(10.0, book.averageRating)
    }

    @Test
    fun `toDomainModel handles null imageLinks`() {
        val bookResponse = BookResponse(
            id = "123",
            volumeInfo = VolumeInfoResponse(
                title = "Test Book",
                authors = listOf("Author One", "Author Two"),
                description = "Test Description",
                imageLinks = null,
                pageCount = 100,
                averageRating = 10.0,
            ),
        )
        val book = bookResponse.toDomainModel()

        assertEquals("", book.thumbnail)
        assertEquals("", book.smallThumbnail)
    }
}