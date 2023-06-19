package cinema.ticket.booking.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cinema.ticket.booking.exception.MyAccessDeniedException;
import cinema.ticket.booking.model.Account;
import cinema.ticket.booking.model.JWTToken;
import cinema.ticket.booking.repository.JWTokenRepository;
import cinema.ticket.booking.response.AuthenJWTokenResponse;
import cinema.ticket.booking.service.JWTokenService;
import cinema.ticket.booking.utils.ChaCha20util;

@Service
public class JWTTokenServiceImpl implements JWTokenService {
	
	final private String secretKey = "2dWAFcBTbYN4UU0Mf8Im69cU0tpgBgr81V071ZH56PNAdpQIdE";	
	final private Long IV = 736884916322117L;
	final private String salt = "d93ae9348f39f1bd";
	
	private ChaCha20util cipher = new ChaCha20util(secretKey, IV, salt);
	
	@Autowired
	private JWTokenRepository jwtTokenRepo;
	
	private AuthenJWTokenResponse createResponse(JWTToken info) {
		String accessDecrypt = this.cipher.decrypt(info.getAccessToken());
		String refreshDecrypt = this.cipher.decrypt(info.getRefreshToken());
		
		return new AuthenJWTokenResponse(info, accessDecrypt, refreshDecrypt);
	}

	@Override
	public JWTToken saveInfo(Account user, String accessToken, String refreshToken) {
		String accessTokenEncrypt = cipher.encryptString(accessToken);
		String refreshTokenEncrypt = cipher.encryptString(refreshToken);
		JWTToken data = new JWTToken(user, accessTokenEncrypt, refreshTokenEncrypt);
		return jwtTokenRepo.save(data);
	}
	
	@Override
	public JWTToken updateInfo(JWTToken data, String accessToken, String refreshToken) {
		String accessTokenEncrypt = cipher.encryptString(accessToken);
		String refreshTokenEncrypt = cipher.encryptString(refreshToken);
		data.setAccessToken(accessTokenEncrypt);
		data.setRefreshToken(refreshTokenEncrypt);
		return jwtTokenRepo.save(data);
	}

	@Override
	public String getAccessToken(Account user) {
		JWTToken data = jwtTokenRepo.findByUserId(user.getId()).get();
		if (data == null)
			return null;
		return this.cipher.decrypt(data.getAccessToken());
	}

	@Override
	public String getRefreshToken(Account user) {
		JWTToken data = jwtTokenRepo.findByUserId(user.getId()).orElse(null);
		if (data == null)
			return null;
		return this.cipher.decrypt(data.getRefreshToken());
	}
	
	@Override
	public String setAccessToken(JWTToken data, String accessToken) {
		String accessTokenEncrypt = cipher.encryptString(accessToken);
		data.setAccessToken(accessTokenEncrypt);
		jwtTokenRepo.save(data);
		return accessTokenEncrypt;
	}

	@Override
	public String setRefreshToken(JWTToken data, String refreshToken) {
		String refreshTokenEncrypt = cipher.encryptString(refreshToken);
		data.setAccessToken(refreshTokenEncrypt);
		jwtTokenRepo.save(data);
		return refreshTokenEncrypt;
	}

	@Override
	public AuthenJWTokenResponse getData(Account user) {
		Optional<JWTToken> data = jwtTokenRepo.findByUserId(user.getId());
		if (!data.isPresent())
			return null;
		return this.createResponse(data.get());
		
	}

	@Override
	public AuthenJWTokenResponse getFromRefreshToken(String refresh_token) {
		JWTToken data = jwtTokenRepo.findByRefreshToken(refresh_token).orElseThrow(() -> new MyAccessDeniedException("This refresh token is invalid. Please go to Login Page to get new one."));
		return this.createResponse(data);
	}
}
