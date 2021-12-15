/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.ankoay.hotelmanage.security;

import org.springframework.security.core.Authentication;

/**
 *
 * @author lacha
 */
public interface IAuthenticationFacade {
Authentication getAuthentication();

}
