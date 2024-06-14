package framework.network

import framework.network.model.ConfirmResponse
import framework.network.model.ListResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

/**
 * @author Frank Shao
 * @created 25/05/2024
 * Description: Request helper for network
 */
inline fun <reified T> get(
    client: HttpClient,
    url: String,
    param: Map<String, String> = emptyMap()
): Flow<Result<T>> = flow {
    val response: T = client.get(url) {
        url {
            param.forEach { (t, u) ->
                parameters.append(t, u)
            }
        }
    }.body()
    emit(Result.success(response))
}.flowOn(Dispatchers.IO).catch { e ->
    emit(Result.failure(e))
}

inline fun <reified T> getList(
    client: HttpClient,
    url: String,
    param: Map<String, String> = emptyMap()
): Flow<Result<List<T>>> = flow {
    val httpResponse = client.get(url) {
        url {
            param.forEach { (t, u) ->
                parameters.append(t, u)
            }
        }
    }
    val response: ListResponse<T> = httpResponse.body()
    emit(Result.success(response.results))
}.flowOn(Dispatchers.IO).catch { e ->
    emit(Result.failure(e))
}

fun post(
    client: HttpClient,
    url: String,
    param: Map<String, String> = emptyMap(),
    body: Map<String, Any> = emptyMap()
): Flow<Result<Boolean>> = flow {
    val response: ConfirmResponse = client.post {
        url(url) {
            param.forEach { (t, u) ->
                parameters.append(t, u)
            }
        }
        contentType(ContentType.Application.Json)
        setBody(body)
    }.body()
    emit(Result.success(response.success))
}.flowOn(Dispatchers.IO).catch {
    emit(Result.failure(it))
}