package org.real.flickfusion.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @author Frank Shao
 * @created 05/06/2024
 * Description: This file contains the model for the genre.
 */
@Serializable
data class Genre(
    @SerialName("id")
    val id: Int,
    @SerialName("name")
    val name: String
)