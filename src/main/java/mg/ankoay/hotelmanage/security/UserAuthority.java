/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.ankoay.hotelmanage.security;

import org.springframework.security.core.GrantedAuthority;

/**
 *
 * @author lacha
 */
public class UserAuthority implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3438255986115942002L;
	public static String USER = "usr";

	@Override
	public String getAuthority() {
		return USER;
	}

}
