package net

import io.ktor.util.reflect.TypeInfo

/**
 * @author Frank Shao
 * @created 13/07/2024
 * Description:
 */
interface IHttp {

    suspend fun <T> get(
        url: String,
        param: Map<String, String> = emptyMap(),
        typeInfo: TypeInfo
    ): T

    suspend fun <T> post(
        url: String,
        param: Map<String, String> = emptyMap(),
        body: Map<String, Any> = emptyMap(),
        typeInfo: TypeInfo
    ): T

}