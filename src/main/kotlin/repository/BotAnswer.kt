package repository

enum class BotAnswer(val answer: String) {
    EMPTY_MESSAGE("Your message is empty, i can't find anything"),
    ERROR_BY_GOOGLE("An error occurred during the search"),
    CAN_NOT_FIND("Sorry, nothing was found for your request"),
    NOT_CORRECT_USER("Sorry, we can't recognise user data"),
}