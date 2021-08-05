package me.karma.companykarmabot.bot.repository

import me.karma.companykarmabot.bot.model.Review
import org.springframework.data.mongodb.repository.MongoRepository
import org.springframework.stereotype.Repository

@Repository
interface ReviewRepository : MongoRepository<Review, String> {

    fun getAllByTitle(title: String): List<Review>
}