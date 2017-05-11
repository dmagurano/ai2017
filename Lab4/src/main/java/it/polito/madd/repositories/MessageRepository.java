package it.polito.madd.repositories;



import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.madd.entities.Message;

public interface MessageRepository extends MongoRepository<Message, String> {
	
	public Page<Message> findByTopic(String topic, Pageable pageable);

}
