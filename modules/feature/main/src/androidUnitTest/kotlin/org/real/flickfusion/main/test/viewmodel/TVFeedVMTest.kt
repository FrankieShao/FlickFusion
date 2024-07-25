package org.real.flickfusion.main.test.viewmodel

import app.cash.turbine.test
import feature.main.entity.FeedUiState
import feature.main.tv.vm.TVFeedViewModel
import io.mockk.clearAllMocks
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.real.flickfusion.repo.ConfigureRepo
import org.real.flickfusion.test.FakeConfigureRepo
import org.real.flickfusion.test.fakeTVsPage1
import org.real.flickfusion.usecase.TVAiringTodayUseCase
import org.real.flickfusion.usecase.TVOnTheAirUseCase
import org.real.flickfusion.usecase.TVPopularUseCase
import org.real.flickfusion.usecase.TVTopRatedUseCase
import org.real.flickfusion.usecase.TVTrendingUseCase
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.time.ExperimentalTime

/**
 * @author Frank Shao
 * @created 24/07/2024
 * Description:
 */
@ExperimentalCoroutinesApi
@ExperimentalTime
class TVFeedVMTest : KoinTest {

    private val trendingUseCase = mockk<TVTrendingUseCase>()
    private val nowPlayingUseCase = mockk<TVAiringTodayUseCase>()
    private val popularUseCase = mockk<TVPopularUseCase>()
    private val topRatedUseCase = mockk<TVTopRatedUseCase>()
    private val upcomingUseCase = mockk<TVOnTheAirUseCase>()
    private val configureRepo = FakeConfigureRepo()

    private val testDispatcher = StandardTestDispatcher()

    private val mockTVs = fakeTVsPage1()

    private lateinit var viewModel: TVFeedViewModel

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
        every { trendingUseCase() } returns flowOf(Result.success(mockTVs))
        every { nowPlayingUseCase(1) } returns flowOf(Result.success(mockTVs))
        every { upcomingUseCase(1) } returns flowOf(Result.success(mockTVs))
        every { popularUseCase(1) } returns flowOf(Result.success(mockTVs))
        every { topRatedUseCase(1) } returns flowOf(Result.success(mockTVs))

        viewModel = TVFeedViewModel()

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
        every { nowPlayingUseCase(1) } returns flowOf(Result.success(mockTVs))
        every { upcomingUseCase(1) } returns flowOf(Result.success(mockTVs))
        every { popularUseCase(1) } returns flowOf(Result.success(mockTVs))
        every { topRatedUseCase(1) } returns flowOf(Result.success(mockTVs))

        viewModel = TVFeedViewModel()

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