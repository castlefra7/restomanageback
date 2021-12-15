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
public class AdminAuthority implements GrantedAuthority {
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 6810914351947605726L;
	public static String ADMIN = "adm";
    
    @Override
    public String getAuthority() {
        return ADMIN;
    }
    
}
