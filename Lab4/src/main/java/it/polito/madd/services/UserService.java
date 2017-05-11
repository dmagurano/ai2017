package it.polito.madd.services;

import it.polito.madd.entities.User;

public interface UserService {
	void registerNewUserAccount(User user) throws Exception;

    User findByEmail(String email);

}
