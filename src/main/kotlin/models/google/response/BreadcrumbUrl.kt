package models.google.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class BreadcrumbUrl(
    @SerialName("host")
    val host: String? = ""
)