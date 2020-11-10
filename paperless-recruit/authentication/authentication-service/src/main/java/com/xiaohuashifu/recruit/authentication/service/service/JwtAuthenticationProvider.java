package com.xiaohuashifu.recruit.authentication.service.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xiaohuashifu.recruit.authentication.service.configuration.JwtAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.NonceExpiredException;
import org.springframework.stereotype.Component;

import java.util.Calendar;

//@Component
public class JwtAuthenticationProvider implements AuthenticationProvider{
	
	private final JwtUserService jwtUserService;
	
	public JwtAuthenticationProvider(JwtUserService jwtUserService) {
		this.jwtUserService = jwtUserService;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		DecodedJWT jwt = ((JwtAuthenticationToken)authentication).getToken();
		if(jwt.getExpiresAt().before(Calendar.getInstance().getTime()))
			throw new NonceExpiredException("Token expires");
		String username = jwt.getSubject();
		UserDetails user = jwtUserService.getUserLoginInfo(username);
		if(user == null || user.getPassword()==null)
			throw new NonceExpiredException("Token expires");
		String encryptSalt = user.getPassword();
		try {
            Algorithm algorithm = Algorithm.HMAC256(encryptSalt);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withSubject(username)
                    .build();
            verifier.verify(jwt.getToken());
        } catch (Exception e) {
            throw new BadCredentialsException("JWT token verify fail", e);
        }
		return new JwtAuthenticationToken(user, jwt, user.getAuthorities());
	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.isAssignableFrom(JwtAuthenticationToken.class);
	}

}
