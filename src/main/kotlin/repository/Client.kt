package repository

data class Client(var findMode: Boolean, val userId: Int)

object Clients {
    var clients: MutableMap<Int, Client> = mutableMapOf<Int, Client>()

    suspend fun processNewMessage(message: String, userId: Int, chatId: Int): String {
        if (!clients.containsKey(userId)) {
            addClient(
                userId = userId,
                chatId = chatId,
            )
        }
        return when (message.trim()) {
            "/start" -> {
                CommandsResponse.START.response
            }

            "/help" -> {
                CommandsResponse.HELP.response
            }

            "/find" -> {
                setMode(
                    userId = userId,
                    chatId = chatId,
                    mode = true,
                )
                CommandsResponse.FIND.response
            }

            "/stop" -> {
                setMode(
                    userId = userId,
                    chatId = chatId,
                    mode = false,
                )
                CommandsResponse.STOP.response
            }
            else -> {
                if(clients[chatId]?.findMode == true) {
                    Google.findGoogle(message)
                }else{
                    CommandsResponse.NOT_ENABLE.response
                }
            }
        }
    }

    private fun addClient(userId: Int, chatId: Int) {
        clients[chatId] = Client(false, userId)
    }

    fun deleteClient(userId: Int) {
        clients.remove(userId)
    }

    private fun setMode(userId: Int, chatId: Int, mode: Boolean) {
        if (!clients.containsKey(chatId)) {
            clients[userId] = Client(
                findMode = mode,
                userId = chatId,
            )
        }
        clients[userId]?.findMode = mode
    }

}
