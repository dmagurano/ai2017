package it.polito.madd.services;

import org.springframework.data.domain.Page;

import it.polito.madd.entities.Message;

public interface MessageService {

	public Page<Message> findByTopic(String topic, int page, int size);

}
