package it.polito.madd.entities;

import org.springframework.hateoas.ResourceSupport;

public class Topic extends ResourceSupport {

	private String topicName;
	
	public Topic(String topicName) {
		this.topicName = topicName;
	}

	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}
}
