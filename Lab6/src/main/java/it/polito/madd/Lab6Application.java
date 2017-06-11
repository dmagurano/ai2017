package it.polito.madd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Lab6Application  {
	
	public static void main(String[] args)  throws Exception {
		SpringApplication.run(Lab6Application.class, args);
	}
	
//	@Override
//	public void run(String... args) throws Exception {
//
//		repository.deleteAll();
//
//		// save a couple of customers
//		repository.save(new User(new EmailAddress("dan@mag.it"), "danmag"));
//		repository.save(new User(new EmailAddress("dav@ide.it"), "fagioli"));
//
//		// fetch all customers
//		System.out.println("Users found with findAll():");
//		System.out.println("-------------------------------");
//		for (User user : repository.findAll()) {
//			System.out.println(user);
//		}
//		System.out.println();
//
//		// fetch an individual customer
//		System.out.println("Customer found with findByEmail('dan@mag.it'):");
//		System.out.println("--------------------------------");
//		System.out.println(repository.findByEmailAddress("dan@mag.it"));
//
////		System.out.println("Customers found with findByLastName('Smith'):");
////		System.out.println("--------------------------------");
////		for (Customer customer : repository.findByLastName("Smith")) {
////			System.out.println(customer);
////		}
//
//	}

}
