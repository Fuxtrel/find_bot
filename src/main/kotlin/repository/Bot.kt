package repository

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.coroutines.delay
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import models.WebHookInfo

object Bot {
    private const val apiToken: String = "5158476003:AAELemfPVE-QAHVK8ErE7i_DftKrjM7tp_Y"
    private var updateRouteUrl: String = "http://localhost:8443/finder/update"
    private const val baseLocalUrl: String = "http://localhost:8081/bot"
    private const val tgBotApiServerUrl: String = "$baseLocalUrl$apiToken"
    private const val requestDelay: Long = 1000
    private val client: HttpClient = HttpClient(CIO)

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

    suspend fun sendMessage(chatId: Int?, message: String?): HttpStatusCode? {
        if (chatId == null || message == null) {
            return null
        }
        var response: HttpResponse? = null
        for (i in 0..5) {
            response = client.request("$tgBotApiServerUrl/sendMessage") {
                url.set {
                    parameters.append("chat_id", chatId.toString())
                    parameters.append("text", message)
                }
            }
            if (response.status == HttpStatusCode.OK) {
                break
            }
        }
        return response?.status
    }
}