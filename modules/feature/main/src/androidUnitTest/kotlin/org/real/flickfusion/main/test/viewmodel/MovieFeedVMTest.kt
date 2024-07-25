package org.real.flickfusion.main.test.viewmodel

import app.cash.turbine.test
import feature.main.entity.FeedUiState
import feature.main.movie.vm.MovieFeedViewModel
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.real.flickfusion.repo.ConfigureRepo
import org.real.flickfusion.test.FakeConfigureRepo
import org.real.flickfusion.test.fakeMoviesPage1
import org.real.flickfusion.usecase.MovieNowPlayingUseCase
import org.real.flickfusion.usecase.MoviePopularUseCase
import org.real.flickfusion.usecase.MovieTopRatedUseCase
import org.real.flickfusion.usecase.MovieTrendingUseCase
import org.real.flickfusion.usecase.MovieUpcomingUseCase
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class MovieFeedViewModelTest : KoinTest {

    private val trendingUseCase = mockk<MovieTrendingUseCase>()
    private val nowPlayingUseCase = mockk<MovieNowPlayingUseCase>()
    private val popularUseCase = mockk<MoviePopularUseCase>()
    private val topRatedUseCase = mockk<MovieTopRatedUseCase>()
    private val upcomingUseCase = mockk<MovieUpcomingUseCase>()
    private val configureRepo = FakeConfigureRepo()

    private val testDispatcher = StandardTestDispatcher()

    private val mockMovie = fakeMoviesPage1()

    private lateinit var viewModel: MovieFeedViewModel

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        startKoin {
            modules(module {
                single { trendingUseCase }
                single { nowPlayingUseCase }
                single { popularUseCase }
                single { topRatedUseCase }
                single { upcomingUseCase }
                single<ConfigureRepo> { configureRepo }
            })
        }
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `getData should update state with HasData when all use cases succeed`() = runTest {
        every { trendingUseCase() } returns flowOf(Result.success(mockMovie))
        every { nowPlayingUseCase(1) } returns flowOf(Result.success(mockMovie))
        every { upcomingUseCase(1) } returns flowOf(Result.success(mockMovie))
        every { popularUseCase(1) } returns flowOf(Result.success(mockMovie))
        every { topRatedUseCase(1) } returns flowOf(Result.success(mockMovie))

        viewModel = MovieFeedViewModel()

        testScheduler.advanceUntilIdle()
        // Then
        viewModel.state.test {
            val hasDataState = awaitItem()
            assertTrue(hasDataState is FeedUiState.HasData)
            assertEquals(false, hasDataState.isRefreshing)
            assertEquals(5, hasDataState.data.size)
            cancelAndConsumeRemainingEvents()
        }

        coVerify(exactly = 1) {
            trendingUseCase()
            nowPlayingUseCase(1)
            upcomingUseCase(1)
            popularUseCase(1)
            topRatedUseCase(1)
        }
    }


    @Test
    fun `getData should update state with NoData when any use case fails`() = runTest {
        every { trendingUseCase() } returns flowOf(Result.failure(Exception("Error")))
        every { nowPlayingUseCase(1) } returns flowOf(Result.success(mockMovie))
        every { upcomingUseCase(1) } returns flowOf(Result.success(mockMovie))
        every { popularUseCase(1) } returns flowOf(Result.success(mockMovie))
        every { topRatedUseCase(1) } returns flowOf(Result.success(mockMovie))

        viewModel = MovieFeedViewModel()

        testScheduler.advanceUntilIdle()
        // Then
        viewModel.state.test {
            val noDataState = awaitItem()
            assertTrue(noDataState is FeedUiState.NoData)
            assertEquals(true, (noDataState as FeedUiState.NoData).isEmpty)
            cancelAndConsumeRemainingEvents()
        }
    }
}