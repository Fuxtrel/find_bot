import server.HttpServer

suspend fun main() {
    val server = HttpServer()
    server.initialiseBot()
    server.startServer()
}