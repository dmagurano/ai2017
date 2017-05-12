package it.polito.madd.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.userdetails.UserDetails;

import it.polito.madd.validator.PasswordMatches;
import it.polito.madd.validator.ValidEmail;

@PasswordMatches
public class User implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	@ValidEmail
	@NotNull
	@NotEmpty
	@Indexed(unique = true)
	private String email;
	
	//TODO foto profilo
	
	@NotNull
    @NotEmpty
    @Size(min = 8, max= 36)
	private String password;
	
	@NotNull
    @NotEmpty
	private String passwordConfirm;
	
	private List<Role> roles;
	
	@NotNull
    @NotEmpty
	private String nickname;
	
	@NotNull
    @NotEmpty
	private String gender;
	
	@NotNull
	private int age;
	
	@NotNull
    @NotEmpty
	private String education;
	
	@NotNull
    @NotEmpty
	private String job;
	
	//@Field("car")
	private Car ownCar;
	
	private String carSharing;
	
	@NotNull
	@Field("bike")
	private Bike bikeUsage;
	
	@NotNull
    @NotEmpty
	private String pubTransport;

	public User() {
		ownCar = new Car();
		bikeUsage = new Bike();
	}

	public User(String email, String nickname) {
		this.email = email;
		this.nickname = nickname;
	}

	@Override
	public Collection<Role> getAuthorities() {
		// TODO Auto-generated method stub
		return this.roles;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.email;
	}
	
	public void setUsername(String email) {
		setEmail(email);
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public Car getOwnCar() {
		return ownCar;
	}

	public void setOwnCar(Car ownCar) {
		this.ownCar = ownCar;
	}

	public String getCarSharing() {
		return carSharing;
	}

	public void setCarSharing(String carSharing) {
		this.carSharing = carSharing;
	}

	public Bike getBikeUsage() {
		return bikeUsage;
	}

	public void setBikeUsage(Bike bikeUsage) {
		this.bikeUsage = bikeUsage;
	}

	public String getPubTransport() {
		return pubTransport;
	}

	public void setPubTransport(String pubTransport) {
		this.pubTransport = pubTransport;
	}


	public void setPassword(String password) {
		this.password = password;
	}
	
	@Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

	public void setAuthorities(List<String> roles) {
		this.roles = new ArrayList<Role>();
		for (String role : roles) {
			this.roles.add( new Role(role) );
		}
	}
	
}
