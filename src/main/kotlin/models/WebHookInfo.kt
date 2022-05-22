package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class WebHookInfo(
    @SerialName("ok")
    val ok: Boolean? = false,
    @SerialName("result")
    val webHookResult: WebHookResult? = WebHookResult()
)