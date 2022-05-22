package repository


enum class CommandsResponse(val response: String) {
    HELP("/help - Get list of commands\n/start - enable find mode\n/stop - disable find mode"),
    START("Hi, you are welcome in find bot\n use /help to get list of commands"),
    FIND("Find mode is enable"),
    STOP("Find mode is disable"),
    NOT_ENABLE("To start, please type /find")
}