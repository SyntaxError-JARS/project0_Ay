package com.revature.alpha_bank.services;

import com.revature.alpha_bank.daos.AccountDao;
import com.revature.alpha_bank.exceptions.AuthenticationException;
import com.revature.alpha_bank.exceptions.InvalidRequestException;
import com.revature.alpha_bank.exceptions.ResourcePersistanceException;
import com.revature.alpha_bank.models.Account;
import com.revature.alpha_bank.util.logging.Logger;

import java.io.IOException;
import java.util.List;

public class AccountService implements Serviceable<Account> {

    private final AccountDao accountDao;

    private Logger logger = Logger.getLogger();

    public AccountService (AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    @Override
    public List<Account> readAll(){
        logger.info("Begin reading Account in our database.");

        try {
            // TODO: Array List
            List<Account> accounts = accountDao.findAll();
            logger.info("All Accounts have been found here are the results: \n");
            return accounts;

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<Account> findByUser_email(String user_email) throws ResourcePersistanceException {

        logger.info("Begin reading Customers in the database using account number.");

        try {
            List<Account> customer = accountDao.findByUser_email(user_email);
            return customer;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public String update(String account_type, String user_email) {
        //System.out.println("Hello there");
        int updatedCustomer = accountDao.update(account_type, user_email);
        if(updatedCustomer == 0){
            throw new ResourcePersistanceException("Account was not updated in the database. ");
        }
        logger.info("Account " + user_email + " of type " + account_type + " has been updated: " );
        return "Account has been successful updated.";

    }

    @Override
    public String delete(String user_email) {

        int deletedCustomer = accountDao.delete(user_email);
        if(deletedCustomer == 0){
            throw new ResourcePersistanceException("No account found with this account number. ");
        }
        logger.info("Account has been deleted: " + user_email);
        return "Account has been successful deleted.";
    }

    public boolean validateAccountNotUsed(String user_email){
        return accountDao.checkUser_email(user_email);
    }

    public Account create(Account newAccount){

        logger.info("Registering Account : " + newAccount);
        if(!validateInput(newAccount)){ // checking if false

            throw new InvalidRequestException("User input was not validated, either empty String or null values");
        }
        //System.out.println(newAccount.getAccountNumber());
        if(validateAccountNotUsed(newAccount.getUser_email())){
            throw new InvalidRequestException("User account number is already in use. Please try again with another account number or login into previous made account.");

        }

        Account persistedAccount = accountDao.create(newAccount);

        if(persistedAccount == null){
            throw new ResourcePersistanceException("Account was not persisted to the database upon registration");
        }
        logger.info("Account has been persisted: " + newAccount);
        return persistedAccount;
    }
    @Override
    public boolean validateInput(Account newAccount) {

        logger.debug("Validating Account: " + newAccount);
        if(newAccount == null) return false;

        if(newAccount.getBalance() <= 25.0 ) return false;
        if(newAccount.getAccount_type() == null || newAccount.getAccount_type().trim().equals("")) return false;

        if(newAccount.getDeposit() < 0 ) return false;


        if(newAccount.getWithdrawal() < 0 )  return false;

        //TODO: figure out why we can't throw this exception
        if ((newAccount.getBalance())-(newAccount.getWithdrawal()) < 0){
          //  resp.getWriter().write("You are successfully logged out!");
           // throw new InvalidRequestException("The amount you're trying to withdrawal an amount which is exceeding your total BALANCE amount . Please try less amount again");
        }

        if(newAccount.getOpen_date() == null || newAccount.getAccount_type().trim().equals("")) return false;

        return newAccount.getUser_email() != null || newAccount.getUser_email().trim().equals("");
    }
//Todo: we never used it
    public Account authenticateAccount(String account_type, String user_email){

        if(user_email == null || user_email.trim().equals("") || user_email  == null || user_email.trim().equals("")) {
            throw new InvalidRequestException("Either account type or account number is an invalid entry. Please try logging in again");
        }

        Account authenticatedAccount = accountDao.authenticateAccount(account_type, user_email);
        if (authenticatedAccount == null){
            throw new AuthenticationException("Unauthenticated user, information provided was not consistent with our database.");
        }
        System.out.println(authenticatedAccount);

        return authenticatedAccount;

    }

}


