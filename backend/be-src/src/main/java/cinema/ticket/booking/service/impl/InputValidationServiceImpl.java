package cinema.ticket.booking.service.impl;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;
import org.owasp.encoder.Encode;
import org.springframework.stereotype.Service;

import cinema.ticket.booking.security.InputValidationFilter;
import cinema.ticket.booking.utils.RegexExtractor;

@Service
public class InputValidationServiceImpl implements InputValidationFilter {
	 
	 private final String sql_pattern = RegexExtractor.SQL;
	 private final String xss_pattern = RegexExtractor.XSS;
	 private final String allow_pattern = RegexExtractor.NORMAL_TEXT;
	 
	@Override
	public String sanitizeInput(String input) {
		return Encode.forHtml(input);
	}

	@Override
	public String sanitizeInputWithSafeList(String input, Safelist safelist) {
		return Jsoup.clean(input, safelist);
	}

	@Override
	public boolean containsSqlInjection(String input) {
		return input.matches(this.sql_pattern);
	}

	@Override
	public boolean containsXss(String input) {
		return input.matches(this.xss_pattern);
	}

	@Override
	public boolean checkInput(String text) {
		if (this.containsSqlInjection(text)) 
			return false;
//        if (this.containsXss(text)) 
//        	return false;
        
        return true;
	}

}
