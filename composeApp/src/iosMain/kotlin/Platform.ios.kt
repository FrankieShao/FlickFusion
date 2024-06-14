import co.touchlab.kermit.Logger
import feature.main.FeedType
import platform.Foundation.NSString
import platform.Foundation.stringWithFormat
import platform.UIKit.UIDevice

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

actual fun TrendingFeedType() = FeedType.BANNER