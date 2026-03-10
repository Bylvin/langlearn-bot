package com.bylvin.langlearnbot;

import java.time.LocalTime;
import java.util.Map;

import org.telegram.telegrambots.abilitybots.api.db.DBContext;
import org.telegram.telegrambots.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import com.bylvin.langlearnbot.enumeration.Constants;
import com.bylvin.langlearnbot.enumeration.UserState;

public class ResponseHandler {
	private final Map<Long, UserState> chatStates;
	private SilentSender sender;

	public ResponseHandler(DBContext db) {
		chatStates = db.getMap(Constants.CHAT_STATES);
	}

	public boolean userIsActive(Long chatId) {
		return chatStates.containsKey(chatId);
	}

	public void setSender(SilentSender sender) {
		this.sender = sender;
	}

	private String ucapanSelamat(String nama) {
		LocalTime waktu = LocalTime.now();
		if (waktu.isBefore(LocalTime.of(12, 0))) {
			return "Selamat pagi " + nama + "🙏";
		} else if (waktu.isBefore(LocalTime.of(15, 0))) {
			return "Selamat siang " + nama + "🙏";
		} else if (waktu.isBefore(LocalTime.of(18, 0))) {
			return "Selamat sore " + nama + "🙏";
		} else {
			return "Selamat malam " + nama + "🙏";
		}
	}

	public void memulaiPercakapanBot(long chatId, String firstName) {
		String ucapan = ucapanSelamat(firstName) + "\nAda yang bisa saya bantu?";
		SendMessage message = new SendMessage(Long.toString(chatId), ucapan);
		message.enableMarkdownV2(true);
		sender.execute(message);
		chatStates.put(chatId, UserState.START);
	}
}
