package org.real.flickfusion.repo

import org.real.flickfusion.model.Configure

/**
 * @author Frank Shao
 * @created 03/06/2024
 * Description: configure repo, for image url prefix
 */
interface ConfigureRepo {
    suspend fun get(): Configure?
}



