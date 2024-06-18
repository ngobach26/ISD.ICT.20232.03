package utils;

import java.util.logging.Logger;

public class LOGGER {
	private static final Logger LOGGER = getLogger(utils.LOGGER.class.getName());
	static {
		System.setProperty("java.util.logging.SimpleFormatter.format", "[%4$-4s] [%1$tF %1$tT] [%2$-7s] %5$s %n");
	}
	public static Logger getLogger(String className) {
		return Logger.getLogger(className);
	}
}
