package models.google.response


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleSearchResponse(
    @SerialName("context")
    val context: Context? = Context(),
    @SerialName("cursor")
    val cursor: Cursor? = Cursor(),
    @SerialName("findMoreOnGoogle")
    val findMoreOnGoogle: FindMoreOnGoogle? = FindMoreOnGoogle(),
    @SerialName("results")
    val results: List<Result>? = listOf()
)