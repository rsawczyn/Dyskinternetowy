package org.zut.dysk.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.zut.dyskService.UserServiceImpl;

public class AuthManager implements AuthenticationManager 
{	
	private UserServiceImpl userservice;
	
	

	public void setUserservice(UserServiceImpl userservice) {
		this.userservice = userservice;
	}



	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException 
	{
	
			String Login = authentication.getName();
			String Pass = authentication.getCredentials().toString();
			if(Login.length() == 0 || Pass.length() == 0) return null;
			if(userservice.Authorize(Login, Pass))
			{
				List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
				auths.add(new SimpleGrantedAuthority("ROLE_NORMALUSER"));
				return new UsernamePasswordAuthenticationToken(Login, Pass,auths);
			}
			else
			{
				return null;
			}
		
		
		
	}

}
