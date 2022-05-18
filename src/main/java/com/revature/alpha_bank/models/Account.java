package com.revature.alpha_bank.models;

public class Account {

    private int id;
    protected double balance;
    private String account_type;
    private double deposit;
    private double withdrawal;
    private String open_date;
    private String user_email;

    public Account(){
        super();
    }

    public Account(double balance, String account_type, double deposit, double withdrawal, String open_date,String  user_email){
        super();
        this.balance= balance;
        this.account_type = account_type;
        this.deposit = deposit;
        this.withdrawal = withdrawal;
        this.open_date = open_date;
        this.user_email = user_email;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }





    public String getAccount_type() {
        return account_type;
    }

    public void setAccount_type(String account_type) {
        this.account_type = account_type;
    }

    public double getDeposit() {
        return deposit;
    }

    public void setDeposit(double deposit) {
        this.deposit = deposit;
    }

    public double getWithdrawal() {
        return withdrawal;
    }

    public void setWithdrawal(double withdrawal) {
        this.withdrawal = withdrawal;
    }

    public String getOpen_date() {
        return open_date;
    }

    public void setOpen_date(String registrationDate) {
        this.open_date = registrationDate;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Account{" +
                "balance='" + balance + '\'' +
                ", account_type ='" + account_type + '\'' +
                ", deposit ='" + deposit + '\'' +
                ", withdrawal='" + withdrawal + '\'' +
                ", open_date'" + open_date + '\'' +
                ", user_email = " + user_email + '\''+
                '}';
    }


}




