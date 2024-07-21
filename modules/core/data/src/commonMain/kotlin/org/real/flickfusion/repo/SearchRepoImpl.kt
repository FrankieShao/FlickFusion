package org.real.flickfusion.repo

import org.real.flickfusion.remote.SearchGateway
/**
 * @author Frank Shao
 * @created 20/07/2024
 * Description:
 */
class SearchRepoImpl(private val gateway: SearchGateway): SearchRepo {
    override fun search(key: String) = gateway.search(key)
}