package core.domain.repo

import core.domain.model.Configure
import core.services.remote.ConfigureGateway


/**
 * @author Frank Shao
 * @created 03/06/2024
 * Description: configure repo, for image url prefix
 */
interface ConfigureRepo {
    suspend fun get(): Configure?
}

class ConfigureRepoImpl(
    private val gateway: ConfigureGateway,
) : ConfigureRepo {

    private var configure: Configure? = null

    override suspend fun get(): Configure? {
        if (configure != null) {
            return configure
        }
        return gateway.request()
    }
}

