package it.polito.madd.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import it.polito.madd.entities.User;
import it.polito.madd.repositories.UserRepository;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
   
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void registerNewUserAccount(User user) throws Exception {
    	
    	if (emailExist(user.getEmail())) {  
            throw new Exception(
              "There is an account with that email adress: "
              +  user.getEmail());
        }
    	
    	List<String> roles = new ArrayList<String>();
    	roles.add("ROLE_USER");
    	
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setAuthorities(roles);
        userRepository.save(user);
    }
    
    private boolean emailExist(String email) {
        User user = this.findByEmail(email);
        if (user != null) {
            return true;
        }
        return false;
    }
    
    @Override
    public User findByEmail(String username) {
        return userRepository.findByEmail(username);
    }

	@Override
	public List<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Page<User> findAll(Integer page, Integer per_page) {
		PageRequest pageReq = new PageRequest(page,per_page);
		Page<User> pageRes = userRepository.findAll(pageReq);
		if (page > pageRes.getTotalPages())
			return null;
		//return pageRes.getContent();
		return pageRes;
	}

}