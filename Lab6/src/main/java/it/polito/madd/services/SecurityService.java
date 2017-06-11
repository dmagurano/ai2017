package it.polito.madd.services;

public interface SecurityService {

    String findLoggedInUsername();

    void autologin(String username, String password);

}
