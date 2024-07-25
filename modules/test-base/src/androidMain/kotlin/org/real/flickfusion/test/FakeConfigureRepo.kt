package org.real.flickfusion.test

import org.real.flickfusion.model.Configure
import org.real.flickfusion.repo.ConfigureRepo

/**
 * @author Frank Shao
 * @created 22/07/2024
 * Description:
 */
class FakeConfigureRepo: ConfigureRepo {
    override suspend fun get(): Configure? {
        return fakeConfigure()
    }
}