package cinema.ticket.booking.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexExtractor {
	public static final String EMAIL = "[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\\\.[a-zA-Z]{2,}";
	public static final String SQL = "(?i).*((\\\\b(select|drop|alter|insert|update|delete)\\\\b)|(\\\\b(\\\\d+)\\\\W+(union|select|table|information_schema)\\\\b)).*";
	public static final String XSS = ".*[<>\"'].+";
	public static final String NORMAL_TEXT = "^[a-zA-Z0-9\\\\-_.!~*'()]*$";
	public static final String GOOD_PASSWORD = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{8,}$";
	
	public static String extract(String text, String regex) {
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(text);
		
		if (matcher.find())
            return matcher.group();
        return null;
	}
}
