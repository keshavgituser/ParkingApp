/**
 * 
 */
package com.practice.parkapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.boot.context.properties.bind.Name;

import com.practice.parkapp.dao.USERTYPE;


/**
 * @author kmahendr
 *
 */
@Entity
@Table(name="users")
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long userId;
	
	@NotBlank
	@Size(min=5,max=7,message="Example Ravi chandra:-> rvndra")
	@Column(name="usernames",unique = true, updatable = true)
	private String userName;
	
	@NotBlank
	@Column(name="passphrases",unique = true, updatable = true)
	private String passPhrase;
	
	@Size(min=10, max=10,message = "phone number should be 10 digits")
	@NotBlank(message="Please Provide a phone number trust us")
	@Column(name="phone_numbers")
	private String phone;

	@Column(name="usertypes")
	private USERTYPE userType;
	
	
	@Column(name="hasbooked")
	private boolean hasBooked;
	

	/**
	 * Default Constructor
	 */
	private User() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructor
	 * @param userName
	 * @param passPhrase
	 * @param string
	 * @param userType
	 */
	public User(String userName, String passPhrase, String phone, USERTYPE userType) {
		super();
		this.userName = userName;
		this.passPhrase = passPhrase;
		this.phone = phone;
		this.userType = userType;
	}

	/**
	 * @return the userId
	 */
	public Long getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId(Long userId) {
		this.userId = userId;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the passPhrase
	 */
	public String getPassPhrase() {
		return passPhrase;
	}

	/**
	 * @param passPhrase the passPhrase to set
	 */
	public void setPassPhrase(String passPhrase) {
		this.passPhrase = passPhrase;
	}

	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}

	/**
	 * @return the userType
	 */
	public USERTYPE getUserType() {
		return userType;
	}

	/**
	 * @param userType the userType to set
	 */
	public void setUserType(USERTYPE userType) {
		this.userType = userType;
	}

	/**
	 * @return the hasBooked
	 */
	public boolean isHasBooked() {
		return hasBooked;
	}

	/**
	 * @param hasBooked the hasBooked to set
	 */
	public void setHasBooked(boolean hasBooked) {
		this.hasBooked = hasBooked;
	}
	
	
	
	
	

}
