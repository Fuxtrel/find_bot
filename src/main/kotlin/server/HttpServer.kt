package server

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.delay
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import models.Update
import models.WebHookInfo
import models.google.response.GoogleSearchResponse

class HttpServer(
    private val apiToken: String = "5158476003:AAELemfPVE-QAHVK8ErE7i_DftKrjM7tp_Y",
    private val serverPort: Int = 8443,
    private val serverHost: String = "0.0.0.0",
    private val client: HttpClient = HttpClient(CIO),
    private var updateRouteUrl: String = "http://localhost:$serverPort/finder/update",
    private val baseLocalUrl: String = "http://localhost:8081/bot",
    private val requestDelay: Long = 1000,
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


    suspend fun initialiseBot() {
        println("Try initialise bot")
        setWebHook()
        println("[+] Bot is initialized")
    }

    private suspend fun isWebHookSet(): Boolean {
        loop@ while (true) {
            println("Check webhook url")
            val response = client.request("$baseLocalUrl$apiToken/getWebhookInfo")
            if (response.status == HttpStatusCode.OK) {
                val webHookInfo = Json.decodeFromString<WebHookInfo>(response.body())
                if (webHookInfo.ok == true) {
                    return (webHookInfo.webHookResult?.url == updateRouteUrl)
                } else {
                    break@loop
                }
            } else {
                println("Error webhook check with error code ${response.status}")
            }
            delay(requestDelay)
        }
        return false
    }

    private suspend fun setWebHook(url: String? = null) {
        println("Try to set webhook")
        while (!isWebHookSet()) {
            val response = client.request("$baseLocalUrl$apiToken/setWebhook") {
                parameter("url", url ?: updateRouteUrl)
            }
            if (response.status == HttpStatusCode.OK) {
                println("[+] Webhook is set")
                return
            } else {
                println("Error in set webhook with status code ${response.status}")
                delay(requestDelay)
            }
        }
        println("[+] Webhook is set")
        if (url != null) {
            updateRouteUrl = url;
        }
    }


    suspend fun getBotMy() {
        var response: HttpResponse = client.request("$baseLocalUrl$apiToken/getMe")
        println(response.bodyAsText())
        response = client.request("https://api.telegram.org/bot$apiToken/logOut")
        println(response.bodyAsText())
        response = client.request("https://api.telegram.org/bot$apiToken/close")
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
            println("New update")
            val json = Json {
                ignoreUnknownKeys = true
            }
            val update = json.decodeFromString<Update>(call.receive<String>())
            val client: HttpClient = HttpClient(CIO)
            val result = if (update.message?.text?.isNotEmpty() == true) {
                println(update.message.text)
                getGoogleRequest(update.message.text)
            }else {
                "По пустому запросу мы ничего не сможем найти"
            }
            print(result)
            val response = client.request("http://localhost:8081/bot5158476003:AAELemfPVE-QAHVK8ErE7i_DftKrjM7tp_Y/sendMessage") {
                url.set {
                    parameters.append("chat_id", (update.message?.chat?.id.toString()))
                    parameters.append("text", result)
                }

            }
            call.respond(HttpStatusCode.OK)
        }
    }
}

suspend fun getGoogleRequest(message: String):String {
    val client: HttpClient = HttpClient(CIO)
    val googleResponse = client.request("https://cse.google.com/cse/element/v1") {
        url.set {
            parameters.append("q", message)
            parameters.append("num", "1")
            parameters.append("rsz", "filtered_cse")
            parameters.append("num", "1")
            parameters.append("hl", "en")
            parameters.append("source", "gcsc")
            parameters.append("cselibv", "3e1664f444e6eb06")
            parameters.append("cx", "f4514ab70e4914513")
            parameters.append("safe", "off")
            parameters.append("cse_tok", "AJvRUv3yAnUjl4WWOKPo29uzlwpY:1653215096340")
            parameters.append("exp", "csqr,cc")
            parameters.append("callback", "google.search.cse.api13056")
        }
    }
    return if (googleResponse.status == HttpStatusCode.OK) {
        val json = Json{
            this.ignoreUnknownKeys = true
        }
        val jsonText = convertToJson(googleResponse.bodyAsText())
        val googleResponseJson = json.decodeFromString<GoogleSearchResponse>(jsonText)
        if(googleResponseJson.results?.isNotEmpty() == true) {
            "${googleResponseJson.results[0].titleNoFormatting}\n" +
            "${googleResponseJson.results[0].url}\n" +
            "${googleResponseJson.results[0].contentNoFormatting}"
        }else{
            "Извините, мы ничего не смогли найти по ващему запросу"
        }
    } else {
        println(googleResponse.status)
        "Извините, мы ничего не смогли найти по ващему запросу"
    }
}

fun convertToJson(source: String): String{
    var result = source.substringAfter('(')
    result = result.substringBeforeLast(')')
    return result;
}


