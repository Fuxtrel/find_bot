package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class From(
    @SerialName("first_name")
    val firstName: String? = "",
    @SerialName("id")
    val id: Int? = 0,
    @SerialName("is_bot")
    val isBot: Boolean? = false,
    @SerialName("language_code")
    val languageCode: String? = "",
    @SerialName("last_name")
    val lastName: String? = "",
    @SerialName("username")
    val username: String? = ""
)