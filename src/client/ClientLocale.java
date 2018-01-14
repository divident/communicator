package client;

import java.util.Locale;
import java.util.ResourceBundle;

public class ClientLocale {
	private static Locale currentLocale;
	private static ResourceBundle messages;

	public static void setLocale(String country, String language) {
		ClientLocale.currentLocale = new Locale(language, country);
		messages = ResourceBundle.getBundle("resources/Communicator", currentLocale);
	}

	public static String getMessage(String message) {
		return messages.getString(message);
	}
	
}
