package clients

data class ClientsData(
    val clientsId: String,
    val clientName: String,
    val countHistoryRequests: Int = 5,
    var requestHistory: ArrayDeque<String> = ArrayDeque(countHistoryRequests),
)