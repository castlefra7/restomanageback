package mg.ankoay.hotelmanage.security.jwt;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;

import mg.ankoay.hotelmanage.bl.services.User;
import mg.ankoay.hotelmanage.security.AdminPrincipal;
import mg.ankoay.hotelmanage.security.UserPrincipal;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	private AuthenticationManager authenticationManager;
	

	public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
		setFilterProcessesUrl(SecurityConstants.SIGN_UP_URL);
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws AuthenticationException {
		try {
			User creds = new ObjectMapper().readValue(req.getInputStream(), User.class);

			return authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(creds.getName(), creds.getPassword(), new ArrayList<>()));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException {
		String token = "";

		if(auth.getPrincipal() != null) {
			String className = auth.getPrincipal().getClass().getSimpleName();
			if(className.startsWith("User")) {
				UserPrincipal prp = (UserPrincipal) auth.getPrincipal();
				token = JWT.create().withSubject(prp.getUsername())
						.withClaim("id", prp.getUser().getId())
						.withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
						.sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));

			} else if( className.startsWith("Admin")) {
				AdminPrincipal prp = (AdminPrincipal) auth.getPrincipal();
				token = JWT.create().withSubject(prp.getUsername())
						.withClaim("id", prp.getUser().getId())
						.withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
						.sign(Algorithm.HMAC512(SecurityConstants.SECRET.getBytes()));
			}
		}
	
		String body = ((UserDetails) auth.getPrincipal()).getUsername() + " " + token;

		res.getWriter().write(body);
		res.getWriter().flush();
	}
}
