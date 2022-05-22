package models.google.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FindMoreOnGoogle(
    @SerialName("url")
    val url: String? = ""
)