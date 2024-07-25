package org.real.flickfusion.domain

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.real.flickfusion.model.MediaType
import org.real.flickfusion.repo.ConfigureRepo
import org.real.flickfusion.repo.MovieRepo
import org.real.flickfusion.repo.TVRepo
import org.real.flickfusion.test.FakeConfigureRepo
import org.real.flickfusion.test.FakeMovieRepository
import org.real.flickfusion.test.FakeTVRepository
import org.real.flickfusion.usecase.MediaDetailUseCase
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * @author Frank Shao
 * @created 22/07/2024
 * Description:
 */
class MediaDetailUseCaseFailTest {
    private val movieRepo = mockk<MovieRepo>()
    private val tvRepo = mockk<TVRepo>()
    private val configureRepo = mockk<ConfigureRepo>()
    private lateinit var useCase: MediaDetailUseCase

    @Before
    fun before() {
        useCase = MediaDetailUseCase(movieRepo, tvRepo, configureRepo)
    }

    @Test
    fun `get movie detail fail`() = runTest {
        coEvery { movieRepo.getMovieDetail(1) } throws Exception("Failed to get movie detail")
        assertFailsWith<Exception>("Failed to get movie detail") {
            useCase(MediaType.MOVIE, 1)
        }
    }

    @Test
    fun `get tv detail fail`() = runTest {
        coEvery { tvRepo.getTVShowDetail(1) } throws Exception("Failed to get tv detail")
        assertFailsWith<Exception>("Failed to get tv detail") {
            useCase(MediaType.TV, 1)
        }
    }
}

class MediaDetailUseCaseSuccessTest {

    private lateinit var movieRepo : MovieRepo
    private lateinit var tvRepo: TVRepo
    private lateinit var configureRepo: ConfigureRepo
    private lateinit var useCase: MediaDetailUseCase

    @Before
    fun before() {
        movieRepo = FakeMovieRepository()
        tvRepo = FakeTVRepository()
        configureRepo = FakeConfigureRepo()
        useCase = MediaDetailUseCase(movieRepo, tvRepo, configureRepo)
    }

    @Test
    fun `get movie detail success`() = runTest {
        val movie = movieRepo.getMovieDetail(1)
        val credits = movieRepo.getMovieCredits(1)
        val configure = configureRepo.get()
        val media = useCase(MediaType.MOVIE, 1)
        assertEquals(movie.id, media.id)
        assertEquals("${configure?.urlPrefix}${movie.posterPath}", media.posterUrl)
    }

    @Test
    fun `get tv detail success`() = runTest {
        val tv = tvRepo.getTVShowDetail(1)
        val credits = tvRepo.getTVShowCredits(1)
        val configure = configureRepo.get()
        val media = useCase(MediaType.TV, 1)
        assertEquals(tv.id, media.id)
        assertEquals("${configure?.urlPrefix}${tv.posterPath}", media.posterUrl)
    }
}