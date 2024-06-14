package di

import core.services.local.AccountLocalSource
import framework.persist.AccountLocalSourceImpl
import org.koin.dsl.module

/**
 * @author Frank Shao
 * @created 10/06/2024
 * Description: persist koin module, responsible for persist related dependencies
 */
val PersistModule = module {
    single<AccountLocalSource> { AccountLocalSourceImpl() }
}