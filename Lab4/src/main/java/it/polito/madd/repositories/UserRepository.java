package it.polito.madd.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.madd.entities.User;

public interface UserRepository extends MongoRepository<User, String> {
	
	public User findByEmail(String email);
	

}
