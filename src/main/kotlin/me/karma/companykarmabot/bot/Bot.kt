package me.karma.companykarmabot.bot

import me.karma.companykarmabot.bot.service.BotParser
import me.karma.companykarmabot.utils.Logger
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.TelegramBotsApi
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession
import javax.annotation.PostConstruct

@Service
class Bot(
    @Value("\${bot.token}") private val token: String,
    @Value("\${bot.username}") private val username: String,
    private val botParser: BotParser
) : TelegramLongPollingBot(), Logger {

    override fun onUpdateReceived(update: Update) {
        log.info(update.toString())
        update.message.let { send(it.chatId.toString(), botParser.parse(it.text)) }
    }

    private fun send(to: String, text: String) {
        sendApiMethod(SendMessage(to, text))
    }

    override fun getBotUsername(): String = username

    override fun getBotToken(): String = token

    @PostConstruct
    fun startup() {
        TelegramBotsApi(DefaultBotSession::class.java).registerBot(this)
        log.info("company-karma-bot started")
    }
}