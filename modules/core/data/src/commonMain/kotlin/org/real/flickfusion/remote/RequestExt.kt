package org.real.flickfusion.remote

import io.ktor.util.reflect.typeInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import net.IHttp
import org.real.flickfusion.model.ListResponse

/**
 * @author Frank Shao
 * @created 25/05/2024
 * Description: Request helper for network
 */
inline fun <reified T> get(
    client: IHttp,
    url: String,
    param: Map<String, String> = emptyMap()
): Flow<Result<T>> = flow {
    val response: T = client.get(url, param, typeInfo<T>())
    emit(Result.success(response))
}.flowOn(Dispatchers.IO).catch { e ->
    emit(Result.failure(e))
}

inline fun <reified T> getList(
    client: IHttp,
    url: String,
    param: Map<String, String> = emptyMap()
): Flow<Result<List<T>>> = flow {
    val response: ListResponse<T> = client.get(url, param, typeInfo<ListResponse<T>>())
    emit(Result.success(response.results))
}.flowOn(Dispatchers.IO).catch { e ->
    emit(Result.failure(e))
}

inline fun <reified T> post(
    client: IHttp,
    url: String,
    param: Map<String, String> = emptyMap(),
    body: Map<String, Any> = emptyMap()
): Flow<Result<T>> = flow {
    val response: T = client.post(url, param, body, typeInfo<T>())
    emit(Result.success(response))
}.flowOn(Dispatchers.IO).catch {
    emit(Result.failure(it))
}