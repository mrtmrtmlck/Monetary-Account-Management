package com.ingenico.assessment.accountmanagement.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * This class defines and represents Account table in the database. 
 * There are some constraints such as “balance” field value cannot be less than 0.
 */
@Entity
@Table(name = "account")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@NotEmpty
	private String name;

	@NotNull
	@Min(value = 0, message = "balance cannot be negative")
	private double balance;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getBalance() {
		return balance;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}
}