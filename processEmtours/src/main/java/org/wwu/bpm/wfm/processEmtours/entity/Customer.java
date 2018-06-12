package org.wwu.bpm.wfm.processEmtours.entity;

import java.util.Date;

public class Customer {
	private int customerId;
	private String name;
	private String address;
	private String email;
	private String tel;
	private Date birthday;
	private Boolean isValid;

	public Customer() {
		this.setIsValid(false);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public Boolean getIsValid() {
		return isValid;
	}

	public void setIsValid(Boolean isValid) {
		this.isValid = isValid;
	}

	public int getId() {
		return customerId;
	}

	public void setId(int id) {
		this.customerId = id;
	}

}
