package com.revature.alpha_bank.web.servelet;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.alpha_bank.exceptions.AuthenticationException;
import com.revature.alpha_bank.exceptions.InvalidRequestException;
import com.revature.alpha_bank.models.Customer;
import com.revature.alpha_bank.services.CustomerServices;
import com.revature.alpha_bank.web.dto.LoginCreds;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//@WebServlet("/auth") // this requires a default No-Args constructor
public class AuthServlet extends HttpServlet {

    private final CustomerServices customerServices;
    // ObjectMapper provided by jackson
    private final ObjectMapper mapper;

    public AuthServlet(CustomerServices customerServices, ObjectMapper mapper){
        this.customerServices = customerServices;
        this.mapper = mapper;
    }

    @Override
    protected void doDelete (HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {

            req.getRequestDispatcher("/auth");
            HttpSession session=req.getSession();
            session.invalidate();
            resp.getWriter().write("You are successfully logged out!");

        } catch (AuthenticationException | InvalidRequestException e) {
            resp.setStatus(404);
            resp.getWriter().write(e.getMessage());
        } catch (Exception e) {
            resp.setStatus(409);
            resp.getWriter().write(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

            try {
                // The jackson library has the ObjectMapper with methods to readValues from the HTTPRequest
                // body as an input stream and assign it to the class
                //Customer reqCustomer = mapper.readValue(req.getInputStream(), Customer.class);
                LoginCreds loginCreds = mapper.readValue(req.getInputStream(), LoginCreds.class);

                Customer authCustomer = customerServices.authenticateCustomer(loginCreds.getEmail(), loginCreds.getPassword());
                HttpSession httpSession = req.getSession(true);
                httpSession.setAttribute("authCustomer", authCustomer);

                resp.getWriter().write("You have successfully logged in!");

            } catch (AuthenticationException | InvalidRequestException e) {
                resp.setStatus(404);
                resp.getWriter().write(e.getMessage());
            } catch (Exception e) {
                resp.setStatus(409);
                resp.getWriter().write(e.getMessage());
            }
        }

    }

