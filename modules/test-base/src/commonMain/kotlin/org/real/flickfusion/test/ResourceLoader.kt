package org.real.flickfusion.test
import kotlinx.serialization.json.Json

/**
 * @author Frank Shao
 * @created 24/07/2024
 * Description:
 */
expect fun loadJsonResource(fileName: String): String

object TestDataLoader {

    val J = Json { ignoreUnknownKeys = true }

    inline fun <reified T> loadFromJson(fileName: String): T {
        val jsonString = loadJsonResource(fileName)
        return J.decodeFromString(jsonString)
    }
}