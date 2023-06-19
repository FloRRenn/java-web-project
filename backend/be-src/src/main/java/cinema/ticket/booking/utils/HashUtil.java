package cinema.ticket.booking.utils;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class HashUtil {
    private MessageDigest digest;
    
    public HashUtil() throws NoSuchAlgorithmException {
        this.digest = MessageDigest.getInstance("SHA-256");
    }
    
    public String calculateHash(String inputData) {
        byte[] inputBytes = inputData.getBytes(StandardCharsets.UTF_8);
        byte[] hashBytes = digest.digest(inputBytes);
        return bytesToHex(hashBytes);
    }
    
    private String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte b : bytes)
            result.append(String.format("%02x", b));

        return result.toString();
    }
    
    public boolean verifyHash(String inputData, String hash) {
        byte[] inputBytes = inputData.getBytes(StandardCharsets.UTF_8);
        byte[] hashBytes = digest.digest(inputBytes);
        String calculatedHash = bytesToHex(hashBytes);
        
        return calculatedHash.equals(hash);
    }
}