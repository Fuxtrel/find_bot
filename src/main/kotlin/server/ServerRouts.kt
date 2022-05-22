package server

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.decodeFromString
import models.Update
import repository.Bot
import repository.BotAnswer
import repository.Clients
import repository.JsonBot

fun Application.module() {
    routing {
        get("/finder") {
            println("Url of sender: ${call.request.host()}")
            println("Url of sender: ${call.request.port()}")
            call.respondText("Hello, Alex!", status = HttpStatusCode.OK)
        }
        post("/finder/update") {
            println("New update")
            val update = JsonBot.json.decodeFromString<Update>(call.receive())
            val result = if (update.message?.text?.isNotEmpty() == true) {
                if(
                    update.message.from?.id != null &&
                    update.message.chat?.id != null
                ){
                    Clients.processNewMessage(
                        message = update.message.text,
                        userId = update.message.from.id,
                        chatId = update.message.chat.id,
                )
                }else{
                    BotAnswer.NOT_CORRECT_USER.answer
                }
            } else {
                BotAnswer.EMPTY_MESSAGE.answer
            }
            Bot.sendMessage(update.message?.chat?.id, result)
            call.respond(HttpStatusCode.OK)
        }
    }
}

