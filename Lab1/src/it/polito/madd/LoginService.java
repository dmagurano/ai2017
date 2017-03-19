package it.polito.madd;

public interface LoginService {
	
	public void logout() throws Exception;
	public boolean isLogged();
	public String getUsername();
	public void login(String username, String password) throws Exception;

}
