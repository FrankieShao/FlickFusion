package org.real.flickfusion

/**
 * @author Frank Shao
 * @created 14/06/2024
 * Description:
 */

fun UrlFormatter(url: String, param: String) : String {
    if (url.isEmpty() || param.isEmpty()) {
        throw IllegalArgumentException("url or param is empty")
    } else {
        return url.replace("$", param)
    }
}

fun UrlFormatter(url: String, param: Int) : String {
    if (url.isEmpty() || param == 0) {
        throw IllegalArgumentException("url or param is empty")
    } else {
        return url.replace("$", param.toString())
    }
}