package it.polito.madd;

import java.util.HashMap;
import java.util.Map;

public class LoginManager implements LoginService {
	
	private boolean isLogged;
	private String username;
	public static Map<String,String> Users = new HashMap<String,String>();
	
	public LoginManager(){
		initializeUsers();
	}

	@Override
	public void login(String username, String password) throws Exception {
		String correctPass;
		if( ( correctPass = LoginManager.Users.get(username) ) == null ) 
			throw new Exception("wrong user");
		
		if( !correctPass.equals(password) )
			throw new Exception("wrong pass");
		
		this.isLogged = true;
		this.username = username;
	

	}

	@Override
	public void logout() throws Exception {
		if( isLogged == true )
			isLogged = false;
		else
			throw new Exception("notLogged");
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
		LoginManager.Users.put("Daniele","Magurano");
		LoginManager.Users.put("Davide","Renna");
		LoginManager.Users.put("Mattia","Manieri");
		LoginManager.Users.put("Andrea","Pantaleo");
	}

}
