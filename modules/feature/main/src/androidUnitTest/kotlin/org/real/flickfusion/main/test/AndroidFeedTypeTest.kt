package org.real.flickfusion.main.test

import feature.main.FeedType
import feature.main.TrendingFeedType
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @author Frank Shao
 * @created 22/07/2024
 * Description:
 */
class AndroidFeedTypeTest {

    @Test
    fun test() {
        assertEquals(FeedType.BANNER, TrendingFeedType())
    }
}