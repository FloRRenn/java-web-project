package cinema.ticket.booking.utils;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.engines.ChaChaEngine;
import org.bouncycastle.crypto.generators.PKCS5S2ParametersGenerator;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.encoders.Base64;
import org.json.JSONObject;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

public class ChaCha20util {
    private static final int KEY_SIZE_BITS = 256; // ChaCha20 key size in bits
    private static final int KEY_DERIVATION_ITERATIONS = 10000; // PBKDF2 iterations
    private static final int SALT_SIZE_BYTES = 16; // Salt size in bytes

    private byte[] key;
    private byte[] iv;

    public ChaCha20util(String key, long iv, String saltHex) {
        byte[] salt = toByteArray(saltHex);
        this.key = deriveKey(key, salt);
        this.iv = toByteArray(iv);
    }

    public String encrypt(String plaintext) {
        try {
            ChaChaEngine engine = new ChaChaEngine();
            CipherParameters parameters = new ParametersWithIV(new KeyParameter(key), iv);
            engine.init(true, parameters);
            
            JSONObject data = new JSONObject();
            data.put("message", RandomStringGenerator.generate(10) + "&&" + plaintext + "&&" + RandomStringGenerator.generate(10));
            data.put("expired", DateUtils.getDateAfter(2));
            
            byte[] input = data.toString().getBytes(StandardCharsets.UTF_8);
            byte[] output = new byte[input.length];
            engine.processBytes(input, 0, input.length, output, 0);
            
            return new String(Base64.encode(output), StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    public String decrypt(String ciphertext) {
        try {
            ChaChaEngine engine = new ChaChaEngine();
            CipherParameters parameters = new ParametersWithIV(new KeyParameter(key), iv);
            engine.init(false, parameters);

            byte[] input = Base64.decode(ciphertext);
            byte[] output = new byte[input.length];
            engine.processBytes(input, 0, input.length, output, 0);

            return new String(output, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }
    
    
    public String encryptString(String text) {
    	ChaChaEngine engine = new ChaChaEngine();
        CipherParameters parameters = new ParametersWithIV(new KeyParameter(key), iv);
        engine.init(true, parameters);
        
        byte[] input = text.getBytes(StandardCharsets.UTF_8);
        byte[] output = new byte[input.length];
        engine.processBytes(input, 0, input.length, output, 0);
        
        return new String(Base64.encode(output), StandardCharsets.UTF_8);
    }
    

    private byte[] generateSalt() {
        byte[] salt = new byte[SALT_SIZE_BYTES];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);
        return salt;
    }

    private byte[] deriveKey(String password, byte[] salt) {
        PKCS5S2ParametersGenerator generator = new PKCS5S2ParametersGenerator();
        generator.init(password.getBytes(StandardCharsets.UTF_8), salt, KEY_DERIVATION_ITERATIONS);
        return ((KeyParameter) generator.generateDerivedParameters(KEY_SIZE_BITS)).getKey();
    }

    private byte[] toByteArray(long value) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte) (value & 0xFF);
            value >>= 8;
        }
        return result;
    }
    
    private static byte[] toByteArray(String value) {
    	int length = value.length();
        byte[] data = new byte[length / 2];
        
        for (int i = 0; i < length; i += 2) {
            data[i / 2] = (byte) ((Character.digit(value.charAt(i), 16) << 4)
                                 + Character.digit(value.charAt(i + 1), 16));
        }
        
        return data;
    }
}



