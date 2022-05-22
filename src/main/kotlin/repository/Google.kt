package repository

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.http.*

object Google{
    private val client: HttpClient = HttpClient(CIO)
    private const val url = "https://cse.google.com/cse/element/v1"

    suspend fun findGoogle(requestText: String){
        val googleResponse = client.prepareRequest(url) {
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
    }
}