/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.ankoay.hotelmanage.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import mg.ankoay.hotelmanage.bl.repositories.UserRepository;
import mg.ankoay.hotelmanage.bl.services.User;
/**
 *
 * @author lacha
 */
@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		UserDetails userPrinc = null;
		try {
			User user = userRepository.findByName(username);
			if (user == null) {
				throw new UsernameNotFoundException(username);
			}
			if (user.getUser_type().contentEquals("adm")) {
				userPrinc = new AdminPrincipal(user);
			}
			if (user.getUser_type().contentEquals("usr")) {
				userPrinc = new UserPrincipal(user);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return userPrinc;
	}

}
