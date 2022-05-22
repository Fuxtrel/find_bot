package models.google.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Page(
    @SerialName("label")
    val label: Int? = 0,
    @SerialName("start")
    val start: String? = ""
)