package di

import core.domain.repo.AccountRepo
import core.domain.repo.AccountRepoImpl
import core.domain.repo.AuthRepo
import core.domain.repo.AuthRepoImpl
import core.domain.repo.ConfigureRepo
import core.domain.repo.ConfigureRepoImpl
import org.koin.dsl.module

/**
 * @author Frank Shao
 * @created 10/06/2024
 * Description: Repo koin module, responsible for repo related dependencies
 */
val RepoModule = module {
    single<AccountRepo> { AccountRepoImpl(get(), get()) }
    single<AuthRepo> { AuthRepoImpl(get(), get()) }
    single<ConfigureRepo> { ConfigureRepoImpl(get()) }
}