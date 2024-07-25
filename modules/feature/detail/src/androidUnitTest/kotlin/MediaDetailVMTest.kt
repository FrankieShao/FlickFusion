/**
 * @author Frank Shao
 * @created 23/07/2024
 * Description:
 */
import app.cash.turbine.test
import detail.MediaDetailViewModel
import detail.UiState
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.real.flickfusion.model.MediaType
import org.real.flickfusion.test.fakeMediaModel
import org.real.flickfusion.usecase.MediaDetailUseCase
import kotlin.test.assertEquals
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalCoroutinesApi::class, ExperimentalTime::class)
class MediaDetailViewModelTest : KoinTest {

    private lateinit var viewModel: MediaDetailViewModel
    private lateinit var mockMediaDetailUseCase: MediaDetailUseCase

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        mockMediaDetailUseCase = mockk()
        startKoin {
            modules(module {
                single { mockMediaDetailUseCase }
            })
        }
    }

    @After
    fun tearDown() {
        stopKoin()
        Dispatchers.resetMain()
    }

    @Test
    fun `loadData success updates state to HasData`() = runTest {
        val fakeMediaModel = fakeMediaModel()
        coEvery {  mockMediaDetailUseCase(any(), any()) } returns fakeMediaModel

        viewModel = MediaDetailViewModel(MediaType.MOVIE, 1)

        testScheduler.advanceUntilIdle()

        viewModel.state.test {
            assertEquals(UiState.HasData(isRefreshing = false, data = fakeMediaModel), awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadData failure updates state to NoData with error`() = runTest {
        val errorMessage = "Network error"
        coEvery {  mockMediaDetailUseCase(any(), any()) } throws RuntimeException(errorMessage)

        viewModel = MediaDetailViewModel(MediaType.MOVIE, 1)

        testScheduler.advanceUntilIdle()

        viewModel.state.test {
            val errorState = awaitItem() as UiState.NoData
            assertEquals(true, errorState.isError)
            assertEquals(errorMessage, errorState.errorMessage)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `loadData is called on init`() = runTest {
        viewModel = MediaDetailViewModel(MediaType.MOVIE, 1)
        testScheduler.advanceUntilIdle()
        coVerify(exactly = 1) { mockMediaDetailUseCase(MediaType.MOVIE, 1) }
    }
}