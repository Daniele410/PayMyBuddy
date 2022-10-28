package com.danozzo.paymybuddy.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "bank_account")
public class BankAccount {

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id_count_bank")
	private int idCountBank;

	@Column(name="bank_name")
	private String bankName;

	@Column(name= "iban")
	private String iban;

	@Column(name="bank_location")
	private String location;

	public BankAccount() {
	}

	public BankAccount(String bankName, String iban, String location) {
		this.bankName = bankName;
		this.iban = iban;
		this.location = location;
	}

	public BankAccount(String bankName, String iban, String location, long id) {
	}

	public int getIdCountBank() {
		return idCountBank;
	}

	public void setIdCountBank(int idCountBank) {
		this.idCountBank = idCountBank;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getIban() {
		return iban;
	}

	public void setIban(String iban) {
		this.iban = iban;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}



}
