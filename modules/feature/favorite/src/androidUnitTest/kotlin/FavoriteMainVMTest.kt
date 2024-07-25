import app.cash.turbine.test
import favorite.FavoriteMainViewModel
import favorite.UiState
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
import org.real.flickfusion.test.fakeTVsPage1
import org.real.flickfusion.usecase.FavoriteMovieUseCase
import org.real.flickfusion.usecase.FavoriteTvUseCase
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class FavoriteMainViewModelTest : KoinTest {

    private lateinit var viewModel: FavoriteMainViewModel
    private lateinit var mockFavoriteMovieUseCase: FavoriteMovieUseCase
    private lateinit var mockFavoriteTvUseCase: FavoriteTvUseCase
    private lateinit var mockConfigureRepo: ConfigureRepo
    private val testDispatcher = StandardTestDispatcher()
    private val mockMovies = fakeMoviesPage1()
    private val mockTvs = fakeTVsPage1()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        mockFavoriteMovieUseCase = mockk()
        mockFavoriteTvUseCase = mockk()
        mockConfigureRepo = FakeConfigureRepo()

        startKoin {
            modules(module {
                single { mockFavoriteMovieUseCase }
                single { mockFavoriteTvUseCase }
                single { mockConfigureRepo }
            })
        }
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `initial state is NoData with isRefreshing true, and empty data`() = runTest {
        every { mockFavoriteMovieUseCase(any()) } returns flowOf(Result.success(emptyList()))
        every { mockFavoriteTvUseCase(any()) } returns flowOf(Result.success(emptyList()))
        viewModel = FavoriteMainViewModel()

        viewModel.state.test {
            assertEquals(UiState.NoData(isRefreshing = true), awaitItem())
        }
        testScheduler.advanceUntilIdle()
        viewModel.state.test {
            assertEquals(UiState.NoData(isEmpty = true), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `refresh with data updates state to HasData`() = runTest {
        every { mockFavoriteMovieUseCase(any()) } returns flowOf(Result.success(mockMovies))
        every { mockFavoriteTvUseCase(any()) } returns flowOf(Result.success(mockTvs))
        viewModel = FavoriteMainViewModel()
        testScheduler.advanceUntilIdle()
        viewModel.state.test {
            val hasDataState = awaitItem() as UiState.HasData
            assertEquals(mockTvs.size + mockMovies.size, hasDataState.data.size)
            assertEquals(1, hasDataState.page)
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `loadMore adds new data to existing state`() = runTest {
        every { mockFavoriteMovieUseCase(any()) } returns flowOf(Result.success(mockMovies))
        every { mockFavoriteTvUseCase(any()) } returns flowOf(Result.success(mockTvs))
        viewModel = FavoriteMainViewModel()
        testScheduler.advanceUntilIdle()
        viewModel.loadMore()
        testScheduler.advanceUntilIdle()
        viewModel.state.test {
            val finalState = awaitItem() as UiState.HasData
            assertEquals(2, finalState.page)
            assertEquals((mockTvs.size + mockMovies.size) * 2, finalState.data.size)
            cancelAndIgnoreRemainingEvents()
        }
    }


    @Test
    fun `loadMore with no new data sets noMoreData to true`() = runTest {
        every { mockFavoriteMovieUseCase(1) } returns flowOf(Result.success(mockMovies))
        every { mockFavoriteTvUseCase(1) } returns flowOf(Result.success(mockTvs))
        every { mockFavoriteMovieUseCase(2) } returns flowOf(Result.success(emptyList()))
        every { mockFavoriteTvUseCase(2) } returns flowOf(Result.success(emptyList()))
        viewModel = FavoriteMainViewModel()
        testScheduler.advanceUntilIdle()
        viewModel.loadMore()
        testScheduler.advanceUntilIdle()
        viewModel.state.test {
            val finalState = awaitItem() as UiState.HasData
            assertEquals(1, finalState.page)
            assertEquals(true, finalState.noMoreData)
            assertEquals(mockTvs.size + mockMovies.size, finalState.data.size)
            cancelAndIgnoreRemainingEvents()
        }
    }
}
