package com.bylvin.langlearnbot;

import org.telegram.telegrambots.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.abilitybots.api.objects.Ability;
import org.telegram.telegrambots.abilitybots.api.objects.Locality;
import org.telegram.telegrambots.abilitybots.api.objects.Privacy;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import com.bylvin.langlearnbot.enumeration.Constants;

public class LanglearnBot extends AbilityBot {
	private final ResponseHandler responseHandler;

	public LanglearnBot(TelegramClient telegramClient,
			String botUsername,
			DBContext dbContext,
			ResponseHandler responseHandler) {
		super(telegramClient, botUsername, dbContext);
		this.responseHandler = responseHandler;
	}

	@Override
	public long creatorId() {
		return 1L;
	}

	public Ability startBot() {
		return Ability.builder()
				.name("greeting")
				.info(Constants.START_DESCRIPTION)
				.locality(Locality.USER)
				.privacy(Privacy.PUBLIC)
				.action(ctx -> responseHandler.memulaiPercakapanBot(ctx.chatId(), ctx.update().getMessage().getFrom().getFirstName()))
				.build();
	}
}
