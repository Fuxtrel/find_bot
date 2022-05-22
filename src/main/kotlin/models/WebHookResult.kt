package models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class WebHookResult(
    @SerialName("has_custom_certificate")
    val hasCustomCertificate: Boolean? = false,
    @SerialName("ip_address")
    val ipAddress: String? = "",
    @SerialName("max_connections")
    val maxConnections: Int? = 0,
    @SerialName("pending_update_count")
    val pendingUpdateCount: Int? = 0,
    @SerialName("url")
    val url: String? = "",
    @SerialName("last_error_message")
    val last_error_message: String? = "",
    @SerialName("last_error_date")
    val last_error_date: Int? = 0,
    @SerialName("last_synchronization_error_date")
    val last_synchronization_error_date: Int? = 0
)