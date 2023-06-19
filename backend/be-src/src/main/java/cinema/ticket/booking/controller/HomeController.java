package cinema.ticket.booking.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.ticket.booking.response.MyApiResponse;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class HomeController {

    @GetMapping(value = {"/", "/api"})
    @Operation(hidden = true)
    public MyApiResponse home() {
        return new MyApiResponse("This is a NT213 Web Project", HttpStatus.ACCEPTED);
    }
    
}