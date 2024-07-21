package di

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.dsl.module
import org.real.flickfusion.repo.AccountRepo
import org.real.flickfusion.repo.AccountRepoImpl
import org.real.flickfusion.repo.AuthRepo
import org.real.flickfusion.repo.AuthRepoImpl
import org.real.flickfusion.repo.ConfigureRepo
import org.real.flickfusion.repo.ConfigureRepoImpl
import org.real.flickfusion.repo.GenreRepo
import org.real.flickfusion.repo.GenreRepoImpl
import org.real.flickfusion.repo.MovieRepo
import org.real.flickfusion.repo.MovieRepoImpl
import org.real.flickfusion.repo.SearchRepo
import org.real.flickfusion.repo.SearchRepoImpl
import org.real.flickfusion.repo.TVRepo
import org.real.flickfusion.repo.TVRepoImpl

/**
 * @author Frank Shao
 * @created 10/06/2024
 * Description: Repo koin module, responsible for repo related dependencies
 */
@OptIn(ExperimentalCoroutinesApi::class)
val RepoModule = module {
    single<AccountRepo> { AccountRepoImpl(get(), get()) }
    single<AuthRepo> { AuthRepoImpl(get(), get()) }
    single<ConfigureRepo> { ConfigureRepoImpl(get()) }
    single<GenreRepo> { GenreRepoImpl(get()) }
    single<MovieRepo> { MovieRepoImpl(get()) }
    single<SearchRepo> { SearchRepoImpl(get()) }
    single<TVRepo> { TVRepoImpl(get()) }
}