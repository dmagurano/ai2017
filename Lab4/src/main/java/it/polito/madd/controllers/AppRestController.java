package it.polito.madd.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import it.polito.madd.entities.Message;
import it.polito.madd.entities.Topic;
import it.polito.madd.entities.User;
import it.polito.madd.services.UserService;

//import org.springframework.web.bind.annotation.RestController;

@RestController
public class AppRestController {
	
	@Autowired
	private UserService userService;
	
	@Value("#{'${topics}'.split(',')}")
	private List<String> topics;
	
	@RequestMapping(value="/rest/users", method=RequestMethod.GET)
	public HttpEntity<PagedResources<User>> getUsers(
			@RequestParam(required=false) Integer page, 
			@RequestParam(required=false) Integer size,
			PagedResourcesAssembler assembler)
	{
		Page<User> res;
		if (page == null || size == null)
			res = userService.findAll(0,Integer.MAX_VALUE);
		else
			res = userService.findAll(page, size);
		return new ResponseEntity<>(assembler.toResource(res), HttpStatus.OK);
	}
	
	@RequestMapping(value="/rest/topics", method=RequestMethod.GET)
	public HttpEntity<Topic> getTopics()
	{	
		List<Topic> topicList = new ArrayList<Topic>();
		for(String topic : topics){
			Topic t = new Topic(topic);
			// TODO continue developing
			topicList.add(t);
		}
		return null;
	}
	
	@RequestMapping(value="/rest/topics/{id}/messages", method=RequestMethod.GET)
	public HttpEntity<PagedResources<Message>> getTopicMessages(
			@PathVariable String id,
			PagedResourcesAssembler assembler)
	{
		
		return null;
	}
}
