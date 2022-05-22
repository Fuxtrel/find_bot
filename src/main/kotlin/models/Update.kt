package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Update(
    @SerialName("message")
    val message: Message? = Message(),
    @SerialName("update_id")
    val updateId: Int? = 0
)