package server

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

class HttpServer(
    private val serverPort: Int = 8443,
    private val serverHost: String = "0.0.0.0",
) {
    fun startServer() {
        val environment = applicationEngineEnvironment {
            connector {
                port = serverPort
                host = serverHost
            }
            module(Application::module)
        }
        embeddedServer(Netty, environment).start(wait = true)
        println("[+] Server is listening on port $serverPort")
    }
}

