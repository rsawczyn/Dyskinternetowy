package org.zut.dyskDomain;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority
{
	private String role;
	
	public Role(String role) {
		super();
		this.role = role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	
	@Override
	public String getAuthority() 
	{		
		return role;
	}

}
