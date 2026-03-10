package com.bylvin.langlearnbot.log;

import java.security.SecureRandom;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class LogWritter {
	private final ObjectMapper objectMapper;

	private SecureRandom random = new SecureRandom();
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");

	public LogWritter(ObjectMapper objectMapper) {
		this.objectMapper = objectMapper;
	}

	/**
	 * Menghasilkan internal id.
	 *
	 * @return {@code String}
	 * @author 23020218
	 */
	public String generateInternalId() {
		String internalId = dateFormat.format(new Date());
		int number = random.nextInt(10000000);
		return internalId.concat(String.format("%07d", number));
	}

	/**
	 * Mengubah object ke string.
	 *
	 * @param object
	 * @return {@code String}
	 * @author 23020218
	 */
	private String writeObjectAsString(Object object)
	{
		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			return "Error object to string: " + e.getMessage();
		}
	}

	/**
	 * Menulis log.
	 *
	 * @param logLevel
	 * @param xid
	 * @param externalIp
	 * @param messageType
	 * @param process
	 * @param key
	 * @param code
	 * @param desc
	 * @param exception
	 * @param object
	 * @author 23020218
	 */
	public void writeLog(LogLevel logLevel, String xid, String externalIp, String internalId, MessageType messageType, String serviceApplication, String process, String key, String code, String desc, String exception, Object object) {
		try {
			MDC.put("xid", xid);
			MDC.put("external_ip", externalIp);
			MDC.put("internal_id", internalId);
			MDC.put("message_type", messageType.name());
			MDC.put("service_application", serviceApplication);
			MDC.put("process", process);
			MDC.put("key", key);
			MDC.put("code", code);
			MDC.put("desc", desc);
			MDC.put("exception", exception);

			String jsonString = "";
			if (object == null) {jsonString = "";}
			else if (object instanceof String str) {jsonString = str;}
			else {jsonString = writeObjectAsString(object);}

			if (logLevel.equals(LogLevel.DEBUG)) {
				log.debug(jsonString);
			}
			else if (logLevel.equals(LogLevel.INFO)) {
				log.info(jsonString);
			}
			else if (logLevel.equals(LogLevel.WARN)) {
				log.warn(jsonString);
			}
			else {
				log.error(jsonString);
			}
		} finally {
			MDC.clear();
		}
	}
}
