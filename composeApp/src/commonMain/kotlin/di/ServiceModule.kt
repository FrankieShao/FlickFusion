package di

import org.koin.dsl.module
import org.real.flickfusion.BuildKonfig
import service.IProperty

/**
 * @author Frank Shao
 * @created 20/07/2024
 * Description:
 */
val ServiceModule = module {
    single<IProperty> { PropertyImpl() }
}

class PropertyImpl : IProperty {
    override fun accountId() = BuildKonfig.accountId
    override fun apiKey() = BuildKonfig.apiKey
}