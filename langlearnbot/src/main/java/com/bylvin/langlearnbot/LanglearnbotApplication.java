package com.bylvin.langlearnbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;
import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class LanglearnbotApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(LanglearnbotApplication.class, args);
		try (TelegramBotsLongPollingApplication telegramBotApp = new TelegramBotsLongPollingApplication()) {
			// Instantiate Telegram Bots API
			AbilityBot bot = ctx.getBean("langLearnBot", AbilityBot.class);

			// Mengaktifkan abilities
			bot.onRegister();

			// Register ability bot
			Environment env = ctx.getBean(Environment.class);
			String botToken = env.getProperty("app.telegram.bot.token");
			telegramBotApp.registerBot(botToken, bot);
		} catch (Exception e) {
			log.error("REGISTER BOT ERROR: Gagal register bot (" + e.getMessage() + ")");
		}
	}

	@EventListener(ApplicationReadyEvent.class)
	public void onReady() {
		log.info("APPLICATION START");
	}

	@EventListener(ContextClosedEvent.class)
	public void onShutdown() {
		log.info("APPLICATION STOP");
	}
}
