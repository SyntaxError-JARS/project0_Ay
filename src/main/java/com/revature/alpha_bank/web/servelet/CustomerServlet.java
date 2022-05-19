package com.revature.alpha_bank.web.servelet;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.alpha_bank.exceptions.ResourcePersistanceException;
import com.revature.alpha_bank.models.Customer;
import com.revature.alpha_bank.services.CustomerServices;
import com.revature.alpha_bank.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

//@WebServlet("/customer")
public class CustomerServlet extends HttpServlet implements Authable {

    private final CustomerServices customerServices;
    private final ObjectMapper mapper;
    private final Logger logger = Logger.getLogger();

    public CustomerServlet(CustomerServices customerServices, ObjectMapper mapper) {
        this.customerServices = customerServices;
        this.mapper = mapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(!Authable.checkAuth(req, resp)) return;

        if(req.getParameter("lname") != null && req.getParameter("email") != null){
            resp.getWriter().write("Hey you have the follow last name and email " + req.getParameter("lname") + " " + req.getParameter("email") );
            return;
        }

        if(req.getParameter("email") != null){
            List<Customer> customer;
            try {
                customer = customerServices.findByUser_email(req.getParameter("email")); // EVERY PARAMETER RETURN FROM A URL IS A STRING
            } catch (ResourcePersistanceException e){
                logger.warn(e.getMessage());
                resp.setStatus(404);
                resp.getWriter().write(e.getMessage());
                return;
            }
            String payload = mapper.writeValueAsString(customer);
            resp.getWriter().write(payload);

            return;
        }

            List<Customer> customer = customerServices.readAll();
            String payload = mapper.writeValueAsString(customer);
            resp.getWriter().write(payload);

    }

    @Override
    protected void doPost (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(!Authable.checkAuth(req, resp)) return;
        try {
            Customer newCustomer = mapper.readValue(req.getInputStream(), Customer.class); // from JSON to Java Object (Customer)
            System.out.println(req.getInputStream());
            Customer persistedCustomer = customerServices.create(newCustomer);

            String payload = mapper.writeValueAsString(persistedCustomer); // Mapping from Java Object (Customer) to JSON
            System.out.println(payload);
            resp.getWriter().write("Persisted the provided Customer as show below \n");
            resp.getWriter().write(payload);
            resp.setStatus(201);
        } catch(Exception e){
            resp.getWriter().write("This customer is already excited \n");
        }

    }
    @Override
    protected void doPut (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if(!Authable.checkAuth(req, resp)) return;
        try {

            String payload = mapper.writeValueAsString(customerServices.update(req.getParameter("lname"), req.getParameter("email")));
            resp.getWriter().write(payload);
        } catch (ResourcePersistanceException e){
            logger.warn(e.getMessage());
            resp.setStatus(404);
            resp.getWriter().write(e.getMessage());
        }


    }

    @Override
    protected void doDelete (HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(!Authable.checkAuth(req, resp)) return;
        try {
            String payload = mapper.writeValueAsString(customerServices.delete(req.getParameter("fname")));
            resp.getWriter().write(payload);
        } catch (ResourcePersistanceException e){
            logger.warn(e.getMessage());
            resp.setStatus(404);
            resp.getWriter().write(e.getMessage());
        }

    }
}
