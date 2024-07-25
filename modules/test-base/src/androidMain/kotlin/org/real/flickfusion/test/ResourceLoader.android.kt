package org.real.flickfusion.test

/**
 * @author Frank Shao
 * @created 24/07/2024
 * Description:
 */
actual fun loadJsonResource(fileName: String): String {
    return object {}.javaClass.classLoader?.getResource(fileName)?.readText()
        ?: throw IllegalArgumentException("File not found: $fileName")
}