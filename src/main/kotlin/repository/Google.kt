package repository

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import models.google.response.GoogleSearchResponse

object Google{
    private val client: HttpClient = HttpClient(CIO)
    private const val url = "https://cse.google.com/cse/element/v1"

    suspend fun findGoogle(requestText: String): String{
        val googleResponse = client.request(url) {
            url.set {
                parameters.append("q", requestText)
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
                    val json = Json {
                        this.ignoreUnknownKeys = true
                    }
                    val jsonText = convertToJson(googleResponse.bodyAsText())
                    val googleResponseJson = json.decodeFromString<GoogleSearchResponse>(jsonText)
                    if (googleResponseJson.results?.isNotEmpty() == true) {
                        "${googleResponseJson.results[0].titleNoFormatting}\n" +
                        "${googleResponseJson.results[0].url}\n" +
                        "${googleResponseJson.results[0].contentNoFormatting}"
                    } else if (googleResponseJson.results?.isEmpty() == true) {
                        BotAnswer.CAN_NOT_FIND.answer
                    } else {
                        BotAnswer.ERROR_BY_GOOGLE.answer
                    }
                } else {
                    println(googleResponse.status)
                    BotAnswer.ERROR_BY_GOOGLE.answer
                }
    }

    private fun convertToJson(source: String): String{
        var result = source.substringAfter('(')
        result = result.substringBeforeLast(')')
        return result;
    }
}