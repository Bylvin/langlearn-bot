package com.bylvin.langlearnbot.config;

import org.mapdb.DB;
import org.mapdb.DBMaker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.abilitybots.api.db.MapDBContext;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import com.bylvin.langlearnbot.LanglearnBot;
import com.bylvin.langlearnbot.ResponseHandler;

@Configuration
public class TelegramBotConfig {
	@Value("${app.telegram.bot.username}")
	public String botUsername;

	@Bean
	DBContext dbContext() {
		DB db = DBMaker
				.fileDB(botUsername)
				.fileMmapEnableIfSupported()
				.closeOnJvmShutdown()
				.transactionEnable()
				.make();
		return new MapDBContext(db);
	}

	@Bean
	ResponseHandler responseHandler(DBContext dbContext) {
		return new ResponseHandler(dbContext);
	}

	@Bean
	LanglearnBot langLearnBot(TelegramClient telegramClient, DBContext dbContext, ResponseHandler responseHandler) {
		return new LanglearnBot(telegramClient, botUsername, dbContext, responseHandler);
	}
}
