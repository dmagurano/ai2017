package it.polito.madd.repositories;



import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import it.polito.madd.entities.Alert;
import it.polito.madd.entities.Message;

public interface AlertRepository extends MongoRepository<Alert, String> {
	
	//public Page<Message> findByTopic(String topic, Pageable pageable);
	//public List<Message> findTop10ByTopicOrderByTimestampDesc(String topic);

}
