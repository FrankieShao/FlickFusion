package org.real.flickfusion.repo

import org.real.flickfusion.model.Configure
import org.real.flickfusion.remote.ConfigureGateway

/**
 * @author Frank Shao
 * @created 13/07/2024
 * Description: configure repo, for image url prefix
 */
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