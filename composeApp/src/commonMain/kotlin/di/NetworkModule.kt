package di

import core.services.remote.AccountGateway
import core.services.remote.AuthGateway
import core.services.remote.ConfigureGateway
import core.services.remote.GenreGateway
import core.services.remote.MovieGateway
import core.services.remote.SearchGateway
import core.services.remote.TVGateway
import framework.network.CLIENT
import framework.network.remote.AccountGatewayImpl
import framework.network.remote.AuthGatewayImpl
import framework.network.remote.ConfigureGatewayImpl
import framework.network.remote.GenreGatewayImpl
import framework.network.remote.MovieGateWayImpl
import framework.network.remote.SearchGatewayImpl
import framework.network.remote.TVGatewayImpl
import io.ktor.client.HttpClient
import org.koin.dsl.module

/**
 * @author Frank Shao
 * @created 10/06/2024
 * Description: network koin module, responsible for network related dependencies
 */
val NetworkModule = module {
    single<HttpClient> { CLIENT }
    single<AccountGateway> { AccountGatewayImpl(get()) }
    single<AuthGateway> { AuthGatewayImpl(get()) }
    single<ConfigureGateway> { ConfigureGatewayImpl(get()) }
    single<GenreGateway> { GenreGatewayImpl(get()) }
    single<MovieGateway> { MovieGateWayImpl(get())}
    single<SearchGateway> { SearchGatewayImpl(get()) }
    single<TVGateway> { TVGatewayImpl(get()) }
}