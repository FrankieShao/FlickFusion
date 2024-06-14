package feature.main

/**
 * @author Frank Shao
 * @created 02/06/2024
 * Description: Display Meta
 */

enum class FeedType {
    BANNER,
    NORMAL
}

data class DisplayMeta(
    val title: String,
    val type: FeedType = FeedType.NORMAL,
    val showMore: Boolean = true
)

