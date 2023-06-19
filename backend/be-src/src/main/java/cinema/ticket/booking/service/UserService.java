package cinema.ticket.booking.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import cinema.ticket.booking.model.Account;
import cinema.ticket.booking.model.Role;
import cinema.ticket.booking.model.enumModel.ERole;
import cinema.ticket.booking.response.AccountSummaryResponse;
import cinema.ticket.booking.response.MyApiResponse;

@Service
public interface UserService extends UserDetailsService {
	Account saveUser(Account account);
	Role saveRole(Role role);
	
	Account getRawUserByUsername(String username);
	AccountSummaryResponse getUserByUsername(String username);
	AccountSummaryResponse getUserByEmail(String email);
	
	void addRoleToUser(String username, ERole role);
	void removeRoleUser(String username, ERole role);
	List<AccountSummaryResponse> getUsers();
	Boolean UsernameIsExisted(String name);
	Boolean EmailIsExisted(String email);
	Boolean PasswordIsGood(String password);
	
	@Override
	UserDetails loadUserByUsername(String username);
	
	AccountSummaryResponse getUserByName(String name);
	List<AccountSummaryResponse> searchByName(String username);
	void deteleUserByUsername(String username);
	Collection<Role> getRoleFromUser(String username);
	
	boolean userHaveRole(String username, ERole role);
	boolean userHaveRole(Account user, ERole role);
	
	MyApiResponse getURIforgetPassword(String email) throws Exception;
	MyApiResponse checkReocveryCode(String code);
	MyApiResponse setNewPassword(String code, String password);
	
	int countAccFromIP(String client_ip);
}
