package org.real.flickfusion.domain

/**
 * @author Frank Shao
 * @created 22/07/2024
 * Description:
 */
import kotlinx.serialization.json.Json

/*
object TestDataLoader {

    val J = Json { ignoreUnknownKeys = true }

    inline fun <reified T> loadFromJson(fileName: String): T {
        val jsonString = readResourceFile(fileName)
        return J.decodeFromString(jsonString)
    }

    fun readResourceFile(fileName: String): String {
        return TestDataLoader::class.java.classLoader?.getResource(fileName)?.readText()
            ?: throw IllegalArgumentException("File not found: $fileName")
    }
}*/
