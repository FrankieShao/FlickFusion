package org.real.flickfusin.test

import org.real.flickfusion.UrlFormatter
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

/**
 * @author Frank Shao
 * @created 22/07/2024
 * Description:
 */

class UrlFormatterTest {

    @Test
    fun testLegalUrlFormatter() {
        val url = "https://api.themoviedb.org/3/movie/top_rated?api_key=$"
        val param = "123"
        val result = UrlFormatter(url, param)
        assertEquals("https://api.themoviedb.org/3/movie/top_rated?api_key=123", result)
    }

    @Test
    fun testILegalUrlFormatter1() {
        val url = ""
        val param = "123"
        assertFailsWith(IllegalArgumentException::class) {
            UrlFormatter(url, param)
        }
    }

    @Test
    fun testILegalUrlFormatter2() {
        val url = "https://api.themoviedb.org/3/movie/top_rated?api_key=\$"
        val param = ""
        assertFailsWith(IllegalArgumentException::class) {
            UrlFormatter(url, param)
        }
    }
}