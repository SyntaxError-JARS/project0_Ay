package com.revature.alpha_bank.services;

import com.revature.alpha_bank.daos.CustomerDao;
import com.revature.alpha_bank.exceptions.AuthenticationException;
import com.revature.alpha_bank.exceptions.InvalidRequestException;
import com.revature.alpha_bank.exceptions.ResourcePersistanceException;
import com.revature.alpha_bank.models.Customer;
import com.revature.alpha_bank.util.logging.Logger;


import java.io.IOException;
import java.util.List;

public class CustomerServices implements Serviceable<Customer>{
    private CustomerDao customerDao;

    private Logger logger = Logger.getLogger();

    public CustomerServices(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    @Override
    public List<Customer> readAll(){
        logger.info("Begin reading Customers in our database.");

        try {
            // TODO: Array List
            List<Customer> customers = customerDao.findAll();
            logger.info("All Customers have been found here are the results: \n");
            return customers;

        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }
    @Override
    public List<Customer> findByUser_email(String email) throws ResourcePersistanceException {

        logger.info("Begin reading Customers in the database using last name.");

        try {
            List<Customer> customer = customerDao.findByUser_email(email);
            return customer;
        } catch (NullPointerException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String update(String last_name, String email) {
        //System.out.println("Hello there");
        int updatedCustomer = customerDao.update(last_name, email);
        if(updatedCustomer == 0){
            throw new ResourcePersistanceException("Customer was not updated in the database. ");
        }
        logger.info("Customer " + last_name + " with email " + email + " has been updated: " );
        return "Customer has been successful updated.";

    }

    @Override
    public String delete(String first_name) {

        int deletedCustomer = customerDao.delete(first_name);
        if(deletedCustomer == 0){
            throw new ResourcePersistanceException("Customer was not deleted from the database. ");
        }
        logger.info("Customer has been deleted: " + first_name);
        return "Customer has been successful deleted.";
    }

    public boolean validateEmailNotUsed(String email){
        return customerDao.checkEmail(email);
    }

    public Customer create(Customer newCustomer){

        logger.info("Customer trying to be registered: " + newCustomer);
        if(!validateInput(newCustomer)){ // checking if false
            // TODO:
            throw new InvalidRequestException("User input was not validated, either empty String or null values");
        }


        if(validateEmailNotUsed(newCustomer.getEmail())){


            throw new InvalidRequestException("User email is already in use. Please try again with another email or login into previous made account.");
        }

        Customer persistedCustomer = customerDao.create(newCustomer);

        if(persistedCustomer == null){
            throw new ResourcePersistanceException("Customer was not persisted to the database upon registration");
        }
        logger.info("Customer has been persisted: " + newCustomer);
        return persistedCustomer;
    }
    @Override
    public boolean validateInput(Customer newCustomer) {

        logger.debug("Validating Customer: " + newCustomer);
        if(newCustomer == null) return false;
        if(newCustomer.getFname() == null || newCustomer.getFname().trim().equals("")) return false;
        if(newCustomer.getLname() == null || newCustomer.getLname().trim().equals("")) return false;
        if(newCustomer.getDob() == null || newCustomer.getDob().equals("")) return false;
        if(newCustomer.getPassword() == null || newCustomer.getPassword().trim().equals("")) return false;
        return newCustomer.getEmail() != null || !newCustomer.getEmail().trim().equals("");
    }
    public Customer authenticateCustomer(String username, String password){

        if(password == null || password.trim().equals("") || password  == null || password.trim().equals("")) {
            throw new InvalidRequestException("Either email or password is an invalid entry. Please try logging in again");
        }

        Customer authenticatedCustomer = customerDao.authenticateCustomer(username, password);

        if (authenticatedCustomer == null){
            throw new AuthenticationException("Unauthenticated user, information provided was not consistent with our database.");
        }

        return authenticatedCustomer;

    }
}

