package org.real.flickfusion

/**
 * @author Frank Shao
 * @created 14/06/2024
 * Description:
 */

fun UrlFormatter(url: String, param: String) : String {
    return url.replace("$", param)
}

fun UrlFormatter(url: String, param: Int) : String {
    return url.replace("$", param.toString())
}