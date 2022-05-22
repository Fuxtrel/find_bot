package repository

import kotlinx.serialization.json.Json

object JsonBot {
    val json: Json = Json {
        ignoreUnknownKeys = true
    }
}