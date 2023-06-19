package cinema.ticket.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CinemaBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaBookingApplication.class, args);
	}
	
}
