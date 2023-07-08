package cinema.ticket.booking.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import cinema.ticket.booking.response.MyApiResponse;
import io.swagger.v3.oas.annotations.Operation;

@RestController
public class HomeController {

    @Value("${app.jwtPublickey}")
    private String publicKey;

    @GetMapping(value = { "/", "/api" })
    @Operation(hidden = true)
    public MyApiResponse home() {
        return new MyApiResponse("This is a NT213 Web Project", HttpStatus.ACCEPTED);
    }

    @GetMapping("/robots.txt")
    @Operation(hidden = true)
    public ResponseEntity<String> getRobot() {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<String>(this.convertToHTML("Disallow: /publickey.pub"),
                responseHeaders, HttpStatus.ACCEPTED);
    }

    @GetMapping("/publickey.pub")
    @Operation(hidden = true)
    public ResponseEntity<String> getPub() {
        String pemFormattedKey = "-----BEGIN PUBLIC KEY-----\n";
        int keyLength = publicKey.length();
        for (int i = 0; i < keyLength; i += 64)
            pemFormattedKey += publicKey.substring(i, Math.min(i + 64, keyLength)) + "\n";
        pemFormattedKey += "-----END PUBLIC KEY-----";

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentType(MediaType.TEXT_HTML);
        return new ResponseEntity<String>(this.convertToHTML(pemFormattedKey),
                responseHeaders, HttpStatus.ACCEPTED);
    }

    private String convertToHTML(String text) {
        return "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "  <title>Rest API</title>\n" +
                "  <style>\n" +
                "    body {\n" +
                "      font-family: Arial, sans-serif;\n" +
                "      margin: 20px;\n" +
                "    }\n" +
                "\n" +
                "    h1 {\n" +
                "      font-size: 24px;\n" +
                "      margin-bottom: 10px;\n" +
                "    }\n" +
                "\n" +
                "    p {\n" +
                "      margin-top: 0;\n" +
                "    }\n" +
                "\n" +
                "    code {\n" +
                "      font-family: Consolas, monospace;\n" +
                "    }\n" +
                "  </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "  <pre>" + text + "</pre>\n" +
                "</body>\n" +
                "</html>";
    }
}