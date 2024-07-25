package org.real.flickfusion.main.test

import feature.main.FeedType
import feature.main.TrendingFeedType
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @author Frank Shao
 * @created 24/07/2024
 * Description:
 */
class DesktopFeedTypeTest {
    @Test
    fun test() {
        assertEquals(FeedType.NORMAL, TrendingFeedType())
    }

}