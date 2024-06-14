import android.os.Build
import feature.main.FeedType

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual fun TrendingFeedType() = FeedType.BANNER