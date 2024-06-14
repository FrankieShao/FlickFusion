package framework.network.remote

import com.real.network.API
import core.domain.model.Configure
import core.services.remote.ConfigureGateway
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

/**
 * @author Frank Shao
 * @created 03/06/2024
 * Description: Gateway for Configure
 */
class ConfigureGatewayImpl    constructor(
    private val client: HttpClient
) : ConfigureGateway {
    override suspend fun request(): Configure? {
        return try {
            client.get(API.CONFIGURE.CONFIGURE).body()
        } catch (e: Exception) {
            null
        }
    }
}