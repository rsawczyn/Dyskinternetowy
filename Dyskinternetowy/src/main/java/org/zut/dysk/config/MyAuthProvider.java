package org.zut.dysk.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.zut.dyskService.UserServiceImpl;



public class MyAuthProvider implements AuthenticationProvider 
{

	UserServiceImpl userservice;	
	
	
	public void setUserservice(UserServiceImpl userservice) {
		this.userservice = userservice;
	}

	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException 
	{
		String Login = auth.getName();
		String Pass = auth.getCredentials().toString();
		if(userservice.Authorize(Login, Pass))
		{
		
			List<GrantedAuthority> Auths = null;
			Auths.add(new SimpleGrantedAuthority("ROLE_NORMALUSER"));
			return new UsernamePasswordAuthenticationToken(Login,Pass,Auths);
		}
		else
		{
			return null;
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return false;
	}

}
