package models.google.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RichSnippet(
    @SerialName("cseImage")
    val cseImage: CseImage? = CseImage(),
    @SerialName("cseThumbnail")
    val cseThumbnail: CseThumbnail? = CseThumbnail(),
    @SerialName("metatags")
    val metatags: Metatags? = Metatags()
)