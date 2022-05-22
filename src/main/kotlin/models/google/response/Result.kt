package models.google.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Result(
    @SerialName("breadcrumbUrl")
    val breadcrumbUrl: BreadcrumbUrl? = BreadcrumbUrl(),
    @SerialName("cacheUrl")
    val cacheUrl: String? = "",
    @SerialName("clicktrackUrl")
    val clicktrackUrl: String? = "",
    @SerialName("content")
    val content: String? = "",
    @SerialName("contentNoFormatting")
    val contentNoFormatting: String? = "",
    @SerialName("formattedUrl")
    val formattedUrl: String? = "",
    @SerialName("richSnippet")
    val richSnippet: RichSnippet? = RichSnippet(),
    @SerialName("title")
    val title: String? = "",
    @SerialName("titleNoFormatting")
    val titleNoFormatting: String? = "",
    @SerialName("unescapedUrl")
    val unescapedUrl: String? = "",
    @SerialName("url")
    val url: String? = "",
    @SerialName("visibleUrl")
    val visibleUrl: String? = ""
)