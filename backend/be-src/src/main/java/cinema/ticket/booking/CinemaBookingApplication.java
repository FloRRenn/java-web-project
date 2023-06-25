package cinema.ticket.booking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
// import org.springframework.context.annotation.Bean;
// import org.springframework.security.crypto.password.PasswordEncoder;

// import java.util.List;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.CommandLineRunner;

// import cinema.ticket.booking.model.Account;
// import cinema.ticket.booking.model.CinemaHall;
// import cinema.ticket.booking.model.Role;
// import cinema.ticket.booking.model.Movie;
// import cinema.ticket.booking.model.CinemaSeat;
// import cinema.ticket.booking.model.CinemaShow;
// import cinema.ticket.booking.model.ShowSeat;
// import cinema.ticket.booking.model.enumModel.ERole;
// import cinema.ticket.booking.model.enumModel.ESeat;
// import cinema.ticket.booking.model.enumModel.ESeatStatus;
// import cinema.ticket.booking.model.enumModel.UserStatus;
// import cinema.ticket.booking.repository.CinemaHallRepository;
// import cinema.ticket.booking.repository.CinemaSeatRepository;
// import cinema.ticket.booking.repository.MovieRepo;
// import cinema.ticket.booking.repository.ShowRepository;
// import cinema.ticket.booking.repository.ShowSeatRepository;
// import cinema.ticket.booking.service.UserService;
// import io.swagger.v3.oas.annotations.OpenAPIDefinition;
// import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@EnableScheduling
public class CinemaBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(CinemaBookingApplication.class, args);
	}
	
	// @Autowired
	// private PasswordEncoder passwordEncoder;

	// @Autowired
	// private CinemaHallRepository hallREPO;
	
	// @Autowired
	// private CinemaSeatRepository seatREPO;
	
	// @Bean
	// CommandLineRunner run(UserService uService) {
	// 	return args -> {
	// 		uService.saveRole(new Role(null, ERole.ROLE_ADMIN));
	// 		uService.saveRole(new Role(null, ERole.ROLE_USER));
	// 		uService.saveRole(new Role(null, ERole.ROLE_SUPER_ADMIN));
			
	// 		uService.saveUser(new Account(null, "I Am Super Admin", "super_admin.1234", passwordEncoder.encode("3Mt^tmM85YUL"), "0122323", "213/324 asdsa", "vA@gmail.com", "1.2.3.4", UserStatus.ACTIVE));
	// 		uService.saveUser(new Account(null, "I Am Admin", "admin.1234", passwordEncoder.encode("3zP!6Z13SN^w"), "0124323", "213/324 asdsa", "vB@gmail.com", "4.5.6.7",UserStatus.ACTIVE));
	// 		uService.saveUser(new Account(null, "I Am User 1", "user_1",  passwordEncoder.encode("k9G*Ni91r!"), "0126323", "213/324 asdsa", "vC@gmail.com", "23.42.54.42", UserStatus.ACTIVE));
	// 		uService.saveUser(new Account(null, "I Am User 2", "user_2", passwordEncoder.encode("hS5f%1*8V1"), "0129323", "213/324 asdsa", "vD@gmail.com", "23.4.3.6", UserStatus.ACTIVE));
			
	// 		uService.addRoleToUser("super_admin.1234", ERole.ROLE_SUPER_ADMIN);
	// 		uService.addRoleToUser("super_admin.1234", ERole.ROLE_ADMIN);
	// 		uService.addRoleToUser("super_admin.1234", ERole.ROLE_USER);
			
	// 		uService.addRoleToUser("admin.1234", ERole.ROLE_ADMIN);
	// 		uService.addRoleToUser("admin.1234", ERole.ROLE_USER);

	// 		uService.addRoleToUser("user_1", ERole.ROLE_USER);
	// 		uService.addRoleToUser("user_2", ERole.ROLE_USER);

	// 		hallREPO.save(new CinemaHall("A", 5,8));
	// 		hallREPO.save(new CinemaHall("B", 5,8));
	// 		hallREPO.save(new CinemaHall("C", 5,8));
	// 		hallREPO.save(new CinemaHall("D", 5,8));
			
	// 		add_("A");
	// 		add_("B");
	// 		add_("C");
	// 		add_("D");
	// 	};
	// }
	
	// private void add_(String hall_name) {
	// 	CinemaHall hall = hallREPO.findByName(hall_name).get();
	// 	for (int row = 1; row <= hall.getTotalRow(); row++) {
    //        for (int column = 1; column <= hall.getTotalCol(); column++) {
    //        	CinemaSeat cinemaSeat = new CinemaSeat(hall, row, column, ESeat.REGULAR);
    //        	seatREPO.save(cinemaSeat);
    //        }
    //    }
	// }	
	
}
