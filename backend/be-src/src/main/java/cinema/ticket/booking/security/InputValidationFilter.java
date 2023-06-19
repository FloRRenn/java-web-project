package cinema.ticket.booking.security;

import org.jsoup.safety.Safelist;

public interface InputValidationFilter {
	// Sanitize input to prevent XSS and SQL injection
	public String sanitizeInput(String input);
	public String sanitizeInputWithSafeList(String input, Safelist safelist);
	
	// Check for SQL injection and XSS vulnerabilities
	public boolean containsSqlInjection(String input);
	public boolean containsXss(String input);
	
	public boolean checkInput(String text);
}
