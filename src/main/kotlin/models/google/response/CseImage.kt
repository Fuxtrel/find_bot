package models.google.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CseImage(
    @SerialName("src")
    val src: String? = ""
)