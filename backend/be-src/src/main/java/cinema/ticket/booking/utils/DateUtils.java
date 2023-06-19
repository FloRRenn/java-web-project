package cinema.ticket.booking.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
	public static String getDateAfter(int hours) {
		LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime dateTimeAfterTwoHours = currentDateTime.plusHours(hours);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String dateString = dateTimeAfterTwoHours.format(formatter);
        return dateString;
	}
	
	public static Boolean YourDateIsGreaterThanNow(String date, int hours_to_comapre) {
		LocalDateTime currentDateTime = LocalDateTime.now();
        LocalDateTime dateTimeAfter = currentDateTime.plusHours(hours_to_comapre);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        LocalDateTime parsedDateTime = LocalDateTime.parse(date, formatter);
        
        if (dateTimeAfter.isBefore(parsedDateTime)) 
        	return true;
        return false;
	}
	
	public static LocalDateTime convertStringDateToDate(String date, String format) {
		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
	    	LocalDateTime newDate = LocalDateTime.parse(date, formatter);
	    	return newDate;
		} catch (Exception e) {
			return null;
		}
		
	}
}
