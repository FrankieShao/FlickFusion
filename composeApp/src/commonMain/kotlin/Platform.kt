import feature.main.FeedType

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform


// the style of Trending Movie or Tv showing in feed
expect fun TrendingFeedType(): FeedType
