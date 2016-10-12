package com.wei.greenleaflibrary.domain;

import java.util.Date;
import java.util.List;

import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;

import com.wei.greenleaflibrary.commons.domain.BaseEntity;


@Entity
@Table(name = "users")
@AttributeOverride(name = "id", column = @Column(name = "userId"))
public class User extends BaseEntity {
	private static final long serialVersionUID = 5494371662678258939L;
	
	@Size(min = 3, max = 45)
	private String userName;
	
	@Size(min = 3, max = 60)
	private String password;
	
	@Pattern(regexp = "^[0-9]{9}$")
	private String cardNumber;
	
	@Size(min = 2, max = 70)
	private String fullName;
	
	@Temporal(TemporalType.DATE)
	@Past
	private Date dateOfBirth;
	
	@Email(message = "{email.invalid}")
	private String email;
	
	@ManyToMany(fetch=FetchType.EAGER)
	@JoinTable(name = "users_roles", 
	joinColumns = {@JoinColumn(name = "userId", nullable = false)}, 
	inverseJoinColumns = {@JoinColumn(name = "roleId", nullable = false)})
	private List<Role> roles;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<LendingRecord> records;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<Rating> ratings;

	// getters and setters
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getCardNumber() {
		return cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Date getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Date dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public List<LendingRecord> getRecords() {
		return records;
	}

	public void setRecords(List<LendingRecord> records) {
		this.records = records;
	}

	public List<Rating> getRatings() {
		return ratings;
	}

	public void setRatings(List<Rating> ratings) {
		this.ratings = ratings;
	}
	
	@Override
	public String toString() {
		return "user[" + cardNumber + "]";
	}

}
