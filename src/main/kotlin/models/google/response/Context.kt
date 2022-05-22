package models.google.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Context(
    @SerialName("title")
    val title: String? = "",
    @SerialName("total_results")
    val totalResults: String? = ""
)