package org.real.flickfusion.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @created 14/06/2024
 * @author Frank Shao
 * Description: Confirm Response
 */
@Serializable
data class ConfirmResponse(
    @SerialName("status_code")
    val statusCode: Int,
    @SerialName("status_message")
    val statusMessage: String,
    @SerialName("success")
    val success: Boolean
)