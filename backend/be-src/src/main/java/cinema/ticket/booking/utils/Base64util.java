package cinema.ticket.booking.utils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class Base64util {
    
    private static final int TIMES = 5;

    public static String encode5Times(String plainText) {
        String encodedString = plainText;
        for (int i = 0; i < TIMES; i++) 
            encodedString = encode(encodedString);
        return encodedString;
    }

    public static String decode5Times(String encodedText) {
        String decodedString = encodedText;
        for (int i = 0; i < TIMES; i++)
            decodedString = decode(decodedString);
        return decodedString;
    }

    public static String encode(String plainText) {
        byte[] bytes = plainText.getBytes(StandardCharsets.UTF_8);
        byte[] encodedBytes = Base64.getEncoder().encode(bytes);
        return new String(encodedBytes, StandardCharsets.UTF_8);
    }

    public static String decode(String encodedText) {
        byte[] encodedBytes = encodedText.getBytes(StandardCharsets.UTF_8);
        byte[] decodedBytes = Base64.getDecoder().decode(encodedBytes);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }

}
