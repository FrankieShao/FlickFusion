package org.real.flickfusion.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @created 14/06/2024
 * @author Frank Shao
 * Description: Delete Session Response
 */
@Serializable
data class DeleteSessionResponse(
    @SerialName("success")
    val success: Boolean
)