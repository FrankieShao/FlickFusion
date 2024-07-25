package org.real.flickfusion.domain

import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.real.flickfusion.model.Genre
import org.real.flickfusion.repo.GenreRepo
import org.real.flickfusion.usecase.GenreUseCase
import kotlin.test.assertContentEquals
/**
 * @author Frank Shao
 * @created 22/07/2024
 * Description:
 */
class GenreUseCaseTest {

    private val genreRepo = mockk<GenreRepo>()
    private lateinit var useCase: GenreUseCase

    @Before
    fun before() {
        useCase = GenreUseCase(genreRepo)
    }

    @Test
    fun `test get getGenre success`() = runTest {
        val movieGenres = listOf(
            Genre(1, "Action"),
        )
        val tvGenres = listOf(
            Genre(2, "Comedy"),
        )
        every { genreRepo.getMovieGenres() } returns flowOf(Result.success(movieGenres))
        every { genreRepo.getTVGenres() } returns flowOf(Result.success(tvGenres))

        useCase().test {
            assertContentEquals(movieGenres + tvGenres, awaitItem().getOrNull())
            awaitComplete()
        }
    }

    @Test
    fun `test get getGenre fail`() = runTest {
        val movieGenres = listOf(
            Genre(1, "Action"),
        )
        every { genreRepo.getMovieGenres() } returns flowOf(Result.success(movieGenres))
        every { genreRepo.getTVGenres() } returns flowOf(Result.failure(Exception("Failed to get genres")))
        useCase().test {
            assert(awaitItem().isFailure)
            awaitComplete()
        }
    }

}