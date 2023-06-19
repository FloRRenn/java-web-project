package cinema.ticket.booking.service.impl;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cinema.ticket.booking.exception.MyBadRequestException;
import cinema.ticket.booking.exception.MyNotFoundException;
import cinema.ticket.booking.model.Account;
import cinema.ticket.booking.model.Role;
import cinema.ticket.booking.model.enumModel.ERole;
import cinema.ticket.booking.repository.RoleRepository;
import cinema.ticket.booking.repository.UserRepository;
import cinema.ticket.booking.response.AccountSummaryResponse;
import cinema.ticket.booking.response.MyApiResponse;
import cinema.ticket.booking.response.EmailResponse;
import cinema.ticket.booking.security.InputValidationFilter;
import cinema.ticket.booking.service.EmailService;
import cinema.ticket.booking.service.UserService;
import cinema.ticket.booking.utils.ChaCha20util;
import cinema.ticket.booking.utils.DateUtils;
import cinema.ticket.booking.utils.RegexExtractor;

@Service
public class UserServiceImpl implements UserService {
	
	@Value("${app.base_recover_pass_url}")
	private String base_recover_pass_url;
	
	private String secretKey = "MyChaChaKeyIsSoBadBut060koldyXC17eWCwF3hPS4bNgMH3wDJ";	
	private Long IV = 3473735834123L;
	private String salt = "3a1f9b8c7d0e2f5a";
	private ChaCha20util cipher = new ChaCha20util(secretKey, IV, salt);
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository UserREPO;
	
	@Autowired
	private RoleRepository RoleREPO;
	
	@Autowired
	private EmailService emailSER;
	
	@Autowired
    private InputValidationFilter inputValidationSER;
	
	private Queue<EmailResponse> mailQueue = new LinkedList<>();

	@Override
	public Account saveUser(Account account) {
		return UserREPO.save(account);
	}

	@Override
	public Role saveRole(Role role) {
		return RoleREPO.save(role);
	}

	@Override
	public AccountSummaryResponse getUserByUsername(String username) {
		Account user = UserREPO.getByUsername(username).orElseThrow();
		return new AccountSummaryResponse(user);
	}

	@Override
	public void addRoleToUser(String username, ERole erole) {
		Account user = UserREPO.getByUsername(username).orElseThrow();
		Role role = RoleREPO.findByName(erole.name());
		user.addRole(role);
		UserREPO.save(user);
	}

	@Override
	public List<AccountSummaryResponse> getUsers() {
		List<Account> list = UserREPO.findAll();
		List<AccountSummaryResponse> res = new ArrayList<AccountSummaryResponse>();
		for (Account user : list) 
			res.add(new AccountSummaryResponse(user));
		return res;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Account user = UserREPO.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new User(user.getUsername(), user.getPassword(), user.getAuthorities());
	}

	@Override
	public Boolean UsernameIsExisted(String name) {
		String regex = "^[a-zA-Z0-9._]{5,}$";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(name);
		
		if (!matcher.matches())
			throw new MyBadRequestException("Username is unvalid. Username must follow these requirements:\n + At least 5 characters long\n + No whitespace and special character, except . and _");
		if (UserREPO.existsByUsername(name))
			return true;
		return false;
	}

	@Override
	public Boolean EmailIsExisted(String email) {
		String regex = "^(.+)@(.+)$";
		Pattern pattern = Pattern.compile(regex);
		
		if (!pattern.matcher(email).matches())
			throw new MyBadRequestException("Email is unvalid");
		if (UserREPO.existsByEmail(email)) 
			return true;
		return false;
	}

	@Override
	public Boolean PasswordIsGood(String password) {;
		Pattern pattern = Pattern.compile(RegexExtractor.GOOD_PASSWORD);
		Matcher matcher = pattern.matcher(password);
		
		if (!matcher.matches())
			throw new MyBadRequestException("Password is unvalid. Password must have:\n + At least 8 characters long\n + Contains at least one uppercase letter\n + Contains at least one lowercase letter\n + Contains at least one digit\n + Contains at least one special character\n");
		return true;
	}
	
	@Override
	public Account getRawUserByUsername(String username) {
		return UserREPO.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

	@Override
	public AccountSummaryResponse getUserByName(String name) {
		Account user = this.getRawUserByUsername(name);
		AccountSummaryResponse resp = new AccountSummaryResponse(user);
		return resp;
	}
	
	@Override
	public AccountSummaryResponse getUserByEmail(String email) {
		Account user = UserREPO.getByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new AccountSummaryResponse(user);
	}

	@Override
	public List<AccountSummaryResponse> searchByName(String username) {
		List<Account> list = UserREPO.findByUsernameContaining(username);
		List<AccountSummaryResponse> res = new ArrayList<AccountSummaryResponse>();
		for (Account user : list) 
			res.add(new AccountSummaryResponse(user));
		return res;
	}

	@Override
	public void deteleUserByUsername(String username) {
		UserREPO.deleteByUsername(username);
	}

	@Override
	public Collection<Role> getRoleFromUser(String username) {
		Account user = UserREPO.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return user.getRoles();
	}

	@Override
	public boolean userHaveRole(String username, ERole role) {
		Collection<Role> roles = this.getRoleFromUser(username);
		for (Role r : roles) {
			if (r.getRole().equals(role.name())) 
				return true;
		}
		return false;
	}
	
	@Override
	public boolean userHaveRole(Account user, ERole role) {
		for (Role r : user.getRoles()) {
			if (r.getRole().equals(role.name())) 
				return true;
		}
		return false;
	}

	@Override
	public void removeRoleUser(String username, ERole role) {
		Account user = UserREPO.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		Collection<Role> old_roles = user.getRoles();
		Collection<Role> new_roles = new ArrayList<>();
		
		for (Role r : old_roles) {
			if (r.getRole().equals(role.name()))
				continue;
			new_roles.add(RoleREPO.findByName(r.getRole()));
		}
		user.setRoles(new_roles);
		UserREPO.save(user);
	}

	@Override
	public MyApiResponse getURIforgetPassword(String username) throws Exception {
		Account user = UserREPO.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));
		String ciphertext = cipher.encrypt(username);

		// JSONObject data = new JSONObject();
		// data.put("username", username);
		// data.put("expired", DateUtils.getDateAfter(2));
		// String ciphertext = Base64util.encode5Times(data.toString());

		this.mailQueue.offer(new EmailResponse(user.getEmail(), 
				"Movie-Project: Recover your email", 
				"This is your link to set new password, it will be expired in 2 hours. Please, do not share it to anyone.\n" + 
				base_recover_pass_url + ciphertext));

		return new MyApiResponse("Please check your email");
	}
	
	private JSONObject checkToken(String code) {
		String decryption =  cipher.decrypt(code);
		// String decryption = Base64util.decode5Times(code);
		if (decryption == null)
			return null;
		
		try {
			JSONObject json_data = new JSONObject(decryption); 
			String date = (String)json_data.get("expired");
			
			if (DateUtils.YourDateIsGreaterThanNow(date, 2))
				return null;
			
			return json_data;
		} catch (Exception e) {
			return null;
		}
		
	}
	
	@Override
	public MyApiResponse checkReocveryCode(String code) {
		JSONObject decryption = this.checkToken(code);
		if (decryption == null)
			throw new MyNotFoundException("URL Not Found");
		return new MyApiResponse("Token is valid");
	}

	@Override
	public MyApiResponse setNewPassword(String code, String password) {
		JSONObject decryption = this.checkToken(code);
		if (decryption == null)
			throw new MyNotFoundException("URL Not Found");
		
		String data = (String)decryption.get("username");
		String username = RegexExtractor.extract(data, "(?<=&&)[^&]+(?=&&)");
		Account user = UserREPO.getByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

		if (!inputValidationSER.checkInput(password))
			throw new MyBadRequestException("Contain illegal character");
		this.PasswordIsGood(password);
		
		user.setPassword(passwordEncoder.encode(password));
		UserREPO.save(user);
		return new MyApiResponse("Set new password");
	}
	
	@Scheduled(fixedDelay = 500)
	public void sendRestCodeMail() {
		while (this.mailQueue.size() != 0) {
			EmailResponse data = this.mailQueue.poll();
			emailSER.sendMail(data.getMail(), data.getSubject(), data.getContent());
		}
	}

	@Override
	public int countAccFromIP(String client_ip) {
		return UserREPO.countByIp(client_ip);
	}
}
