package models


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Document(
    @SerialName("file_name")
    val file_name: String? = "",
    @SerialName("mime_type")
    val mime_type: String? = "",
    @SerialName("file_id")
    val file_id: String? = "",
    @SerialName("file_unique_id")
    val file_unique_id: String? = "",
    @SerialName("file_size")
    val file_size: Int? = 0,

)