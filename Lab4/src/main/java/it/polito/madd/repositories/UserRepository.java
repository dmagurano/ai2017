package it.polito.madd.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.madd.entities.User;

public interface UserRepository extends MongoRepository<User, String> {
	
	public User findByEmail(String email);
	public List<User> findAll();
	public Page<User> findAll(Pageable p);
	
}
