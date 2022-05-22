import repository.Bot
import server.HttpServer

suspend fun main() {
    val server = HttpServer()
    Bot.initialiseBot()
    server.startServer()
}