package org.real.flickfusion.remote

import io.ktor.util.reflect.typeInfo
import net.IHttp
import org.real.flickfusion.API
import org.real.flickfusion.model.Configure

/**
 * @author Frank Shao
 * @created 03/06/2024
 * Description: Gateway for Configure
 */
interface ConfigureGateway {
    suspend fun request(): Configure?
}

class ConfigureGatewayImpl constructor(
    private val client: IHttp
) : ConfigureGateway {
    override suspend fun request(): Configure? =
        client.get(url = API.CONFIGURE.CONFIGURE, typeInfo = typeInfo<Configure>())
}