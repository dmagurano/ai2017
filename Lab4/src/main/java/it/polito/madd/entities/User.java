package it.polito.madd.entities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.security.core.userdetails.UserDetails;

public class User implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	private String id;
	
	@Field("email")
	@Indexed(unique = true)
	private EmailAddress emailAddress;
	
	private String password;
	
	private List<Role> roles;
	
	private String nickname;
	
	private String gender;
	
	private int age;
	
	private String education;
	
	private String job;
	
	@Field("car")
	private Car ownCar;
	
	private String carSharing;
	
	@Field("bike")
	private Bike bikeUsage;
	
	private String pubTransport;
	
	

	public User(EmailAddress emailAddress, String nickname) {
		this.emailAddress = emailAddress;
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
		return this.emailAddress.toString();
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

	public EmailAddress getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(EmailAddress emailAddress) {
		this.emailAddress = emailAddress;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setAuthorities(List<String> roles) {
		this.roles = new ArrayList<Role>();
		for (String role : roles) {
			this.roles.add( new Role(role) );
		}
	}
	
}
