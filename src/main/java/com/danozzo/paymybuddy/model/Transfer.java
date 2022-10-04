package com.danozzo.paymybuddy.model;

import org.hibernate.annotations.DynamicUpdate;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="transfer")
@DynamicUpdate
public class Transfer {
	
	@Id
	@GeneratedValue(strategy= GenerationType.IDENTITY)
	@Column(name="transfer_id")
	private int transferId;

	@Column(name="amount")
	private double amount;

	@Column(name= "description")
	private String description;
	
	@Column(name= "transaction_date")
	private Date transactionDate;

	@Column(name= "id_count_bank")
	private int idCountBank;

	public int getTransferId() {
		return transferId;
	}

	public void setTransferId(int transferId) {
		this.transferId = transferId;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getTransactionDate() {
		return transactionDate;
	}

	public void setTransactionDate(Date transactionDate) {
		this.transactionDate = transactionDate;
	}

	public int getIdCountBank() {
		return idCountBank;
	}

	public void setIdCountBank(int idCountBank) {
		this.idCountBank = idCountBank;
	}
}
