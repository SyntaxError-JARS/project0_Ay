package com.revature.alpha_bank.services;


import com.revature.alpha_bank.daos.CustomerDao;
import com.revature.alpha_bank.models.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.text.ParseException;

public class TrainerServiceTestSuite {

    CustomerServices sut;

    @BeforeEach
    public void testPrep(){
        sut = new CustomerServices(new CustomerDao());
    }

    @Test
    public void test_validateInput_givenValidTrainer_returnTrue() throws ParseException {

        // Arrange
        Customer customer = new Customer("Valid", "Valid","Valid","valid","valid","valid", "valid");

        // Act
        boolean actualResult = sut.validateInput(customer);

        // Assert
        Assertions.assertTrue(actualResult);

    }

    @Test
    public void test_create_givenValidUser_returnsTrainer() throws ParseException {
        // Arrange
        Customer customer = new Customer("valid", "valid", "valid","valid","valid","valid", "valid");

        // Act
        Customer actualCustomer = sut.create(customer);

        // Assert
        Assertions.assertEquals("valid", actualCustomer.getFirstName());
        Assertions.assertEquals("valid", actualCustomer.getLastName());
        Assertions.assertEquals("valid", actualCustomer.getDob());
        Assertions.assertEquals("valid", actualCustomer.getSsn());
        Assertions.assertEquals("valid", actualCustomer.getUsername_u());
        Assertions.assertEquals("valid", actualCustomer.getPassword_p());
        Assertions.assertEquals("valid", actualCustomer.getEmail());

    }

    @Test
    @Disabled
    public void test3(){

    }

}
