package org.real.flickfusion.main.test.viewmodel

import app.cash.turbine.test
import feature.main.list.CategoryListViewModel
import feature.main.list.UiState
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
import org.real.flickfusion.model.MediaType
import org.real.flickfusion.model.TVCategory
import org.real.flickfusion.repo.ConfigureRepo
import org.real.flickfusion.repo.MovieRepo
import org.real.flickfusion.repo.TVRepo
import org.real.flickfusion.test.FakeConfigureRepo
import org.real.flickfusion.test.FakeMovieRepository
import org.real.flickfusion.test.FakeTVRepository
import org.real.flickfusion.test.fakeTVsPage1
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
class CategoryListVMFailTest : KoinTest {


    private lateinit var viewModel: CategoryListViewModel
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var movieRepo: MovieRepo
    private lateinit var tvRepo: TVRepo

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        movieRepo = mockk()
        tvRepo = mockk()
        startKoin {
            modules(module {
                single<MovieRepo> { movieRepo }
                single<TVRepo> { tvRepo }
                single<ConfigureRepo> { FakeConfigureRepo() }
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
    fun `refresh should update state with NoData when use case returns empty list`() = runTest {
        // Given
        every { tvRepo.getPopularTVShows(1) } returns flowOf(Result.success(emptyList()))
        // When
        viewModel = CategoryListViewModel(TVCategory.Popular, MediaType.TV.value)
        testScheduler.advanceUntilIdle()

        // Then
        viewModel.state.test {
            assertEquals(UiState.NoData(isEmpty = true), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadMore should update state with HasData when use case returns empty list`() = runTest {
        // Given
        every { tvRepo.getPopularTVShows(1) } returns flowOf(Result.success(fakeTVsPage1()))
        every { tvRepo.getPopularTVShows(2) } returns flowOf(Result.success(emptyList()))

        viewModel = CategoryListViewModel(TVCategory.Popular, MediaType.TV.value)
        testScheduler.advanceUntilIdle()

        // When
        viewModel.loadMore()
        testScheduler.advanceUntilIdle()

        // Then
        viewModel.state.test {
            val finalState = awaitItem() as UiState.HasData
            assertEquals(1, finalState.page)
            assertEquals(true, finalState.noMoreData)
            cancelAndIgnoreRemainingEvents()
        }
    }
}

@ExperimentalCoroutinesApi
@ExperimentalTime
class CategoryListVMSuccessTest : KoinTest {

    private lateinit var viewModel: CategoryListViewModel
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var movieRepo: MovieRepo
    private lateinit var tvRepo: TVRepo
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        movieRepo = FakeMovieRepository()
        tvRepo = FakeTVRepository()
        startKoin {
            modules(module {
                single<MovieRepo> { movieRepo }
                single<TVRepo> { tvRepo }
                single<ConfigureRepo> { FakeConfigureRepo() }
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
    fun `refresh should update state with HasData when use case succeeds`() = runTest {
        val fakeTVs = fakeTVsPage1()
        viewModel = CategoryListViewModel(TVCategory.Popular, MediaType.TV.value)
        testScheduler.advanceUntilIdle()

        viewModel.state.test {
            val hasDataState = awaitItem() as UiState.HasData
            assertEquals(fakeTVs.size, hasDataState.data.size)
            assertEquals(1, hasDataState.page)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadMore should update state with HasData when use case succeeds`() = runTest {
        val fakeTVs = fakeTVsPage1()
        viewModel = CategoryListViewModel(TVCategory.Popular, MediaType.TV.value)
        testScheduler.advanceUntilIdle()

        viewModel.loadMore()
        testScheduler.advanceUntilIdle()

        viewModel.state.test {
            val hasDataState = awaitItem() as UiState.HasData
            assertEquals(fakeTVs.size, hasDataState.data.size)
            assertEquals(2, hasDataState.page)
            cancelAndIgnoreRemainingEvents()
        }
    }
}