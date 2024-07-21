package di

import net.IHttp
import org.koin.dsl.module
import org.real.flickfusion.net.HttpImpl
import org.real.flickfusion.remote.AccountGateway
import org.real.flickfusion.remote.AccountGatewayImpl
import org.real.flickfusion.remote.AuthGateway
import org.real.flickfusion.remote.AuthGatewayImpl
import org.real.flickfusion.remote.ConfigureGateway
import org.real.flickfusion.remote.ConfigureGatewayImpl
import org.real.flickfusion.remote.GenreGateway
import org.real.flickfusion.remote.GenreGatewayImpl
import org.real.flickfusion.remote.MovieGateWayImpl
import org.real.flickfusion.remote.MovieGateway
import org.real.flickfusion.remote.SearchGateway
import org.real.flickfusion.remote.SearchGatewayImpl
import org.real.flickfusion.remote.TVGateway
import org.real.flickfusion.remote.TVGatewayImpl

/**
 * @author Frank Shao
 * @created 10/06/2024
 * Description: network koin module, responsible for network related dependencies
 */
val NetworkModule = module {
    single<IHttp> { HttpImpl(get()) }
    single<AccountGateway> { AccountGatewayImpl(get()) }
    single<AuthGateway> { AuthGatewayImpl(get()) }
    single<ConfigureGateway> { ConfigureGatewayImpl(get()) }
    single<GenreGateway> { GenreGatewayImpl(get()) }
    single<MovieGateway> { MovieGateWayImpl(get()) }
    single<SearchGateway> { SearchGatewayImpl(get()) }
    single<TVGateway> { TVGatewayImpl(get()) }
}