package com.danozzo.paymybuddy.model;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

@Entity
@Table(name = "user", uniqueConstraints = @UniqueConstraint(columnNames = "email"))
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "balance")
    private BigDecimal balance;



    @ManyToMany( cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    }
    )

    private List<User> friends = new ArrayList<>();


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BankAccount> bankAccountList = new ArrayList<>();



    /**
     * All payments received in pay may buddy account
     **/
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "creditAccount",
            cascade = {
                    CascadeType.MERGE,
                    CascadeType.PERSIST
            })
    private Set<Transfer> receivedPayments = new TreeSet<>();


    /**
     * All payments sent from pay may buddy account to contacts
     **/
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "debitAccount", cascade = {
            CascadeType.MERGE,
            CascadeType.PERSIST
    }
    )
    private Set<Transfer> sentPayments = new TreeSet<>();

    public User() {
    }

    public void addBankAccount(BankAccount bankAccount) {
        if (bankAccountList.contains(bankAccount)) {
            throw new IllegalArgumentException();
        }
        bankAccountList.add(bankAccount);
        bankAccount.setUser(this);
    }

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }

    public List<BankAccount> getBankAccountList() {
        return bankAccountList;
    }

    public void setBankAccountList(List<BankAccount> bankAccountList) {
        this.bankAccountList = bankAccountList;
    }


    public Set<Transfer> getReceivedPayments() {
        return receivedPayments;
    }

    public void setReceivedPayments(Set<Transfer> receivedPayments) {
        this.receivedPayments = receivedPayments;
    }

    public Set<Transfer> getSentPayments() {
        return sentPayments;
    }

    public void setSentPayments(Set<Transfer> sentPayments) {
        this.sentPayments = sentPayments;
    }

    public Object getFriends(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        return getFriends();
    }
}
