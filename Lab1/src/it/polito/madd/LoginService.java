package it.polito.madd;

public interface LoginService {
	
	public void logout();
	public boolean isLogged();
	public String getUsername();
	public void login(String username, String password);

}
