package me.karma.companykarmabot.bot.service

import me.karma.companykarmabot.bot.model.Review
import me.karma.companykarmabot.utils.Logger
import org.springframework.stereotype.Service

@Service
class BotParser(private val reviewService: ReviewService) : Logger {

    fun parse(text: String): String =
        try {
            when {
                text.startsWith(ADD_COMMAND) -> parseAsAdd(text)
                text.startsWith(GET_COMMAND) -> parseAsGet(text)
                else -> START_MESSAGE
            }
        } catch (e: Exception) {
            log.error(e.message)
            ERROR
        }

    private fun parseAsGet(text: String): String {
        val title = text.substringAfter("$GET_COMMAND ")
        val allReviews = reviewService.getReviewByTitle(title)
        val partition = allReviews.partition { it.positive }
        return REVIEW_TEMPLATE.format(
            title,
            partition.first.size,
            partition.second.size,
            allReviews.sortedByDescending { it.created }.take(5).joinToString("\n")
        )
    }


    private fun parseAsAdd(text: String): String {
        val split = text.split(" ")
        val saveReview = reviewService.saveReview(
            Review(
                title = split[1],
                positive = getReviewType(split[2]),
                comment = text.substringAfter("$ADD_COMMAND ${split[1]} ${split[2]}")
            )
        )
        return "Сохранил:\n$saveReview"
    }

    private fun getReviewType(reviewBlock: String): Boolean =
        when (reviewBlock) {
            "да" -> true
            "нет" -> false
            else -> throw IllegalArgumentException("$reviewBlock не да или нет")
        }
}

private const val ADD_COMMAND = "/add"
private const val GET_COMMAND = "/get"
private const val ERROR = "Не понял сообщение."
private const val START_MESSAGE = """Я - бот с информацией о карме компаний.
    Попробуй:
     /add {название компании} {хорошая ли? да/нет} {отзыв}
     /get {название компании}
    """

private const val REVIEW_TEMPLATE = """Компания: %s
    Позитивных: %s
    Негативных: %s
    
    Последние:
    %s
"""