package it.polito.madd.entities;

import org.springframework.security.core.GrantedAuthority;

public class Role implements GrantedAuthority {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String role;
	
	public Role (String role) {
		this.role = role;
	}

	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return role;
	}
	
	
	
}
