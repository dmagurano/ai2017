package it.polito.madd.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import it.polito.madd.entities.Bike;
import it.polito.madd.entities.Car;
import it.polito.madd.entities.User;
import it.polito.madd.services.SecurityService;
import it.polito.madd.services.UserService;
import it.polito.madd.services.UserServiceImpl;


@Controller
public class TestController {
    @Autowired
    private UserService userService;

    @Autowired
    private SecurityService securityService;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @RequestMapping(value = {"/", "/welcome"}, method = RequestMethod.GET)
    public String welcome(Model model) {
        return "welcome";
    }
    
    @RequestMapping(value = {"/register"}, method = RequestMethod.GET)
    public String test(Model model) {
    	model.addAttribute("user", new User());
        return "register";
    }
    
    @RequestMapping(value = {"/profile"}, method = RequestMethod.GET)
    public String profile(Model model) {
    	
    	User currentUser = userService.findByEmail(securityService.findLoggedInUsername());
    	User test = new User();
    	test.setAge(11);
    	test.setBikeUsage(new Bike(true,false));
    	test.setCarSharing("Enjoy");
    	test.setEducation("primary school");
    	test.setEmail("pippo@a.it");
    	test.setGender("male");
    	test.setJob("no job");
    	test.setNickname("Mr_Test");
    	test.setOwnCar(new Car(1999,"diesel"));
    	test.setPassword("ads");
    	test.setPasswordConfirm("ads");
    	test.setPubTransport("daily");
    	/*
    	 * 
    	 */
    	model.addAttribute("user", currentUser);
        return "profile";
    }
    
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(@ModelAttribute("user") @Valid User user, BindingResult result, Model model) {
    	
    	boolean registered = true;

        if (!result.hasErrors()) {
            registered = createUserAccount(user, result);
        }
        if (registered == false) {
            result.rejectValue("email", "message.regError");
        }
        
        if (result.hasErrors()) {
            return "register";
        }
        
        securityService.autologin(user.getUsername(), user.getPassword());

        return "redirect:/welcome";
    }
    
    private boolean createUserAccount(User user, BindingResult result) {
        
        try {
            userService.registerNewUserAccount(user);
        } catch (Exception e) {
            return false;
        }    
        return true;
    }
}

