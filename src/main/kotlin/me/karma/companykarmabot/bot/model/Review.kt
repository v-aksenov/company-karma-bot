package me.karma.companykarmabot.bot.model

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.sql.Timestamp
import java.time.Instant.now

@Document
data class Review(
    @Id
    val id: ObjectId? = ObjectId.get(),
    val title: String,
    val positive: Boolean = true,
    val comment: String,
    val created: Timestamp = Timestamp.from(now())
) {

    override fun toString(): String =
        """Позитивный: $positive
           Комментарий: $comment
           Создан: $created
        """.trimMargin()
}