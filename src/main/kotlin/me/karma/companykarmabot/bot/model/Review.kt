package me.karma.companykarmabot.bot.model

import me.karma.companykarmabot.utils.DATE_FORMAT
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Review(
    @Id
    val id: ObjectId? = ObjectId.get(),
    val title: String,
    val positive: Boolean = true,
    val comment: String,
    val created: Calendar = Calendar.getInstance()
) {

    override fun toString(): String =
        """Компания: $title
    Позитивный: $positive
    Комментарий: $comment
    Создан: ${DATE_FORMAT.format(created.time)}""".trimMargin()
}