package cinema.ticket.booking.service;

import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import cinema.ticket.booking.exception.MyAccessDeniedException;
import cinema.ticket.booking.exception.MyServerErrorException;
import cinema.ticket.booking.model.Account;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParserBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtService {

	@Autowired
	private UserService userSER;

	@Value("${app.issuer}")
	private String issuer;

	@Value("${app.jwtSecret}")
	private String secretKey;

	@Value("${app.jwtRefreshSecret}")
	private String refreshKey;

	@Value("${app.jwtPublickey}")
	private String publicKey;

	@Value("${app.jwtExpirationInMs}")
	private Long expired_time;

	@Value("${app.jwtRefreshExpirationInMs}")
	private Long expired_refresh_time;

	// Generate Token ------------------------------------------------------------------------------------------------------------

	public String generateToken(UserDetails userDetails) {
		return generateToken(new HashMap<>(), userDetails);
	}

	public String generateToken(Map<String, Collection<?>> extraClaims, UserDetails userDetails) {
		return buildToken(extraClaims, userDetails, secretKey, expired_time);
	}

	public String generateRefreshToken(Map<String, Collection<?>> extraClaims, UserDetails userDetails) {
		return buildToken(extraClaims, userDetails, refreshKey, expired_refresh_time);
	}

	public String generateTokenFromRefreshToken(String refresh_token) {
		Claims claims = this.extractAllClaims(refresh_token, refreshKey);

		if (claims != null) {
			String username = claims.getSubject();
			Account user = userSER.getRawUserByUsername(username);

			Map<String, Collection<?>> list_roles = new HashMap<>();
			list_roles.put("roles", user.getAuthorities());

			return this.generateToken(new HashMap<>(), user);
		}
		return null;
	}

	private String buildToken(Map<String, Collection<?>> extraClaims, 
						UserDetails userDetails, String key, long expiration) {
		return Jwts
				.builder()
				.setIssuer(issuer)
				.setClaims(extraClaims)
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(this.loadPrivateKey(key), SignatureAlgorithm.RS256)
				.compact();
	}
	// ------------------------------------------------------------------------------------------------------------------------


	// Valid Token ------------------------------------------------------------------------------------------------------------
	public boolean isValidToken(String token, UserDetails userDetails, boolean useSecretKey) {
		final String username = extractUsername(token, useSecretKey);
		if (username == null)
			return false;
		return (username.equals(userDetails.getUsername())) && !isTokenExpired(token, useSecretKey);
	}

	private boolean isTokenExpired(String token, boolean useSecretKey) {
		return extractExpiration(token, useSecretKey).before(new Date());
	}

	public String extractUsername(String token, boolean useSecretKey) {
		if (useSecretKey)
			return extractClaim(token, secretKey, Claims::getSubject);
		else
			return extractClaim(token, refreshKey, Claims::getSubject);
	}

	private Date extractExpiration(String token, boolean useSecretKey) {
		if (useSecretKey)
			return extractClaim(token, secretKey, Claims::getExpiration);
		return extractClaim(token, refreshKey, Claims::getExpiration);
	}
	
	public <T> T extractClaim(String token, String key, Function<Claims, T> claimsResolver) {
		Claims claims = extractAllClaims(token, key);
		if (claims == null)
			return null;
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token, String key) {
		String algorithm = this.getAlgorithm(token);
		switch (algorithm) {
    	case "RS256":
    		return this.RS256_verify(token);
    		
		case "HS256":
			return this.HS256_verify(token);
 
    	default:
    		throw new MyAccessDeniedException("Token is invalid");
    	}
	}

	private Claims HS256_verify(String token) {
		try {
			byte[] publicKey = this.publicKey.getBytes("UTF-8");
			JwtParserBuilder claimsBuilder = Jwts.parserBuilder().setSigningKey(publicKey);
	    	return this.getClaims(claimsBuilder, token);

		} catch (Exception e) {
			return null;
		}
    }
    
    private Claims RS256_verify(String token) {
		PublicKey publicKey = this.loadPublicKey();
		JwtParserBuilder claimsBuilder = Jwts.parserBuilder().setSigningKey(publicKey);
		return this.getClaims(claimsBuilder, token);
    }

	private Claims getClaims(JwtParserBuilder claimsBuilder, String token) {
		try {
			return claimsBuilder
					.build()
					.parseClaimsJws(token)
					.getBody();

		} catch (Exception e) {
			return null;
		}
	}

	// ------------------------------------------------------------------------------------------------------------

	// Load Key Object

	private PrivateKey loadPrivateKey(String private_key) {
		try {
			byte[] privateKeyBytes = Base64.getDecoder().decode(private_key);
			PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        return keyFactory.generatePrivate(keySpec);
	        
		} catch (Exception e) {
			throw new MyServerErrorException("Can not load private key " + e.getMessage());
		}
    }

	private PublicKey loadPublicKey() {
    	try {
    		byte[] publicKeyBytes = Base64.getDecoder().decode(this.publicKey);
	        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
	        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
	        return keyFactory.generatePublic(keySpec);
	        
    	} catch (Exception e) {
			throw new MyServerErrorException("Can not load public key");
		}
    }
    
    public String getAlgorithm(String token) {
    	String[] tokenParts = token.split("\\.");

        if (tokenParts.length >= 2) {
            String header = new String(Base64.getUrlDecoder().decode(tokenParts[0]));
            JSONObject headerObject = new JSONObject(header);
            String algorithm = headerObject.getString("alg");
            return algorithm;
        }
        return null;
    }
}