package it.polito.madd.services;

import java.util.List;

import org.springframework.data.domain.Page;

import it.polito.madd.entities.User;

public interface UserService {
	void registerNewUserAccount(User user) throws Exception;
	User findLoggedInUser();

    User findByEmail(String email);
    List<User> findAll();
    Page<User> findAll(Integer page, Integer per_page);

}
