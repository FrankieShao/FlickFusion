package core.services.remote

import core.domain.model.Configure


/**
 * @author Frank Shao
 * @created 03/06/2024
 * Description:     Gateway for Configure
 */
interface ConfigureGateway {
    suspend fun request(): Configure?
}