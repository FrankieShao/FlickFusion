package framework.network.model


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @created 14/06/2024
 * @author Frank Shao
 * Description: TokenValid response
 */
@Serializable
data class TokenValid(
    @SerialName("expires_at")
    val expiresAt: String,
    @SerialName("request_token")
    val requestToken: String,
    @SerialName("success")
    val success: Boolean
)