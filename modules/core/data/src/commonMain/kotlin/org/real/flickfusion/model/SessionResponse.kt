package org.real.flickfusion.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @created 14/06/2024
 * @author Frank Shao
 * Description: Session Response
 */
@Serializable
data class SessionResponse(
    @SerialName("session_id")
    val sessionId: String,
    @SerialName("success")
    val success: Boolean
)