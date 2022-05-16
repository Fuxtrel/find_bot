package server

import clients.ClientsData
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.network.tls.certificates.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

class HttpServer(
    private val apiToken: String = "5158476003:AAELemfPVE-QAHVK8ErE7i_DftKrjM7tp_Y",
    private val serverPort: Int = 80,
    private val serverHost: String = "0.0.0.0",
    private var clients: List<ClientsData> = listOf(),
    private val client: HttpClient = HttpClient(CIO)
) {

    fun startServer() {
        println("[+] Start server on port $serverPort")
        val keyStoreFile = File("build/keystore.jks")
        val keystore = generateCertificate(
            file = keyStoreFile,
            keyAlias = "crt",
            keyPassword = "",
            jksPassword = ""
        )

        val environment = applicationEngineEnvironment {
            connector {
                port = serverPort
                host = serverHost
            }
            sslConnector(
                keyStore = keystore,
                keyAlias = "sampleAlias",
                keyStorePassword = { "foobar".toCharArray() },
                privateKeyPassword = { "foobar".toCharArray() }) {
                port = serverPort
                keyStorePath = keyStoreFile
            }
            module(Application::module)
        }
        embeddedServer(Netty, environment).start(wait = true)
    }


    suspend fun initialiseBot() {
        var response: HttpResponse = client.request("https://api.telegram.org/bot$apiToken/getMe") {}
        println(response.bodyAsText())
        response = client.request("https://api.telegram.org/bot$apiToken/setWebhook") {
            parameter("url", "https://localhost:80/finder/sendUpdate")
        }
        println(response.bodyAsText())
        response = client.request("https://api.telegram.org/bot$apiToken/getWebhookInfo")
        println(response.bodyAsText())
    }
}


fun Application.module() {
    routing {
        get("/finder") {
            println("Url of sender: ${call.request.host()}")
            println("Url of sender: ${call.request.port()}")
            call.respondText("Hello, Alex!", status = HttpStatusCode.OK)
        }
        post("/finder/update") {
            call.respondText(
                "getUpdate",
                status = HttpStatusCode.OK,
            )
        }
    }
}
