package me.karma.companykarmabot.bot.service

import me.karma.companykarmabot.bot.model.Review
import me.karma.companykarmabot.bot.repository.ReviewRepository
import org.springframework.stereotype.Service

@Service
class ReviewService(private val reviewRepository: ReviewRepository) {

    fun saveReview(review: Review): Review = reviewRepository.save(review)

    fun getReviewByTitle(title: String): List<Review> = reviewRepository.getAllByTitle(title)

}