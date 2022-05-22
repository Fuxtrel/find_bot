package models.google.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CseThumbnail(
    @SerialName("height")
    val height: String? = "",
    @SerialName("src")
    val src: String? = "",
    @SerialName("width")
    val width: String? = ""
)