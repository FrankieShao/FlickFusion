package org.real.flickfusion.test

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Foundation.NSBundle
import platform.Foundation.NSString
import platform.Foundation.NSUTF8StringEncoding
import platform.Foundation.stringWithContentsOfFile

/**
 * @author Frank Shao
 * @created 24/07/2024
 * Description:
 */
@OptIn(ExperimentalForeignApi::class)
actual fun loadJsonResource(fileName: String): String {
    val resourcePath = NSBundle.mainBundle.pathForResource(fileName.removeSuffix(".json"), "json")
    return requireNotNull(NSString.stringWithContentsOfFile(resourcePath!!, NSUTF8StringEncoding, null)) {
        "Resource $fileName not found"
    }
}