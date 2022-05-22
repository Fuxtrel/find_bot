package models.google.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Cursor(
    @SerialName("currentPageIndex")
    val currentPageIndex: Int? = 0,
    @SerialName("estimatedResultCount")
    val estimatedResultCount: String? = "",
    @SerialName("moreResultsUrl")
    val moreResultsUrl: String? = "",
    @SerialName("pages")
    val pages: List<Page>? = listOf(),
    @SerialName("resultCount")
    val resultCount: String? = "",
    @SerialName("searchResultTime")
    val searchResultTime: String? = ""
)