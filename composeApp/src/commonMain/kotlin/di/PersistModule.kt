package di

import org.koin.dsl.module
import org.real.flickfusion.local.AccountLocalSource
import org.real.flickfusion.local.AccountLocalSourceImpl

/**
 * @author Frank Shao
 * @created 10/06/2024
 * Description: persist koin module, responsible for persist related dependencies
 */
val PersistModule = module {
    single<AccountLocalSource> { AccountLocalSourceImpl(get()) }
}