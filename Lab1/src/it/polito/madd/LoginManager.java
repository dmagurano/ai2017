package it.polito.madd;

import java.util.HashMap;
import java.util.Map;

public class LoginManager implements LoginService {
	
	private boolean isLogged = false;
	private String username = null;
	private boolean initialized = false;
	public static Map<String,String> Users = new HashMap<String,String>();
	
	@Override
	public void login(String username, String password){
		String correctPass;
		
		if ( !this.initialized )
			initializeUsers();
		
		if( ( correctPass = LoginManager.Users.get(username) ) == null )
			return;
				
		if( !correctPass.equals(password) )
			return;
			
		this.isLogged = true;
		this.username = username;
	}

	@Override
	public void logout(){
		isLogged = false;
		username = null;
	} 

	@Override
	public boolean isLogged() {
		return isLogged;
	}
	
	@Override
	public String getUsername() {
		return username;
	}
	
	private void initializeUsers() {
		LoginManager.Users.put("daniele@gmail.com","magurano");
		LoginManager.Users.put("davide@gmail.com","renna");
		LoginManager.Users.put("mattia@gmail.com","manieri");
		LoginManager.Users.put("andrea@gmail.com","pantaleo");
	}

}
