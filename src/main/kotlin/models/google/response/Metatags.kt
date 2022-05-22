package models.google.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Metatags(
    @SerialName("colorScheme")
    val colorScheme: String? = "",
    @SerialName("themeColor")
    val themeColor: String? = ""
)