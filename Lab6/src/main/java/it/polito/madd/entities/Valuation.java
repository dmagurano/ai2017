package it.polito.madd.entities;

import java.util.ArrayList;
import java.util.List;

public class Valuation {
	
	private Integer sum;
	private Integer count;
	private List<String> userEmails;
	
	public Valuation() {
		this.sum = 0;
		this.count = 0;
		userEmails = new ArrayList<String>();
	}
	
	
	public void addVote(String email, Integer vote) {
		if (!userEmails.contains(email) && vote > 0 && vote < 6)
		{
			userEmails.add(email);
			sum += vote;
			count++;
		}		
		// TODO manage wrong values
	}
	
	public Integer getSum() {
		return sum;
	}
	public void setSum(Integer sum) {
		this.sum = sum;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public List<String> getUserEmails() {
		return userEmails;
	}
	public void setUserEmails(List<String> userEmails) {
		this.userEmails = userEmails;
	}
	
	public Double getVote() {
		if (count == 0)
			return 0.0;
		else
			return new Double(sum/count);
	}
	
}
