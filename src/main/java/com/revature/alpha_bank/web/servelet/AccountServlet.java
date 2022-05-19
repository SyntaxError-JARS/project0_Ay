package com.revature.alpha_bank.web.servelet;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.alpha_bank.exceptions.ResourcePersistanceException;
import com.revature.alpha_bank.models.Account;
import com.revature.alpha_bank.services.AccountService;
import com.revature.alpha_bank.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

import static com.revature.alpha_bank.web.servelet.Authable.checkAuth;

public class AccountServlet extends HttpServlet implements Authable {

    private final AccountService accountService;
    private final ObjectMapper mapper;
    private final Logger logger = Logger.getLogger();

    public AccountServlet(AccountService accountService, ObjectMapper mapper) {
        this.accountService = accountService;
        this.mapper = mapper;
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(!checkAuth(req, resp)) return;
        if(req.getParameter("user_email") != null){
            List<Account> account = accountService.findByUser_email(req.getParameter("user_email"));
            String payload = mapper.writeValueAsString(account);
            resp.getWriter().write(payload);
            return;
        }

        List<Account> accounts = accountService.readAll();
        String payload = mapper.writeValueAsString(accounts);

        resp.getWriter().write(payload);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(!checkAuth(req, resp)) return;

        try{
        Account newAccount = mapper.readValue(req.getInputStream(), Account.class); // from JSON to Java Object (Account)
        Account persistedAccount = accountService.create(newAccount);

        String payload = mapper.writeValueAsString(persistedAccount); // Mapping from Java Object (Account) to JSON

        resp.getWriter().write("Persisted the provided account as show below \n");
        resp.getWriter().write(payload);
        resp.setStatus(201);
    }catch(Exception e){
            resp.getWriter().write("There is a customer registered used this email or already have an account \n");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(!Authable.checkAuth(req, resp)) return;
        try {
            //System.out.println("Hello there");
            //System.out.println(req.getParameter("last_name"));
            //System.out.println(req.getParameter("email"));
            String payload = mapper.writeValueAsString(accountService.update(req.getParameter("account_type"), req.getParameter("user_email")));
            resp.getWriter().write(payload);
        } catch (ResourcePersistanceException e){
            logger.warn(e.getMessage());
            resp.setStatus(404);
            resp.getWriter().write(e.getMessage());
        }
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if(!Authable.checkAuth(req, resp)) return;
        try {
            String payload = mapper.writeValueAsString(accountService.delete(req.getParameter("user_email")));
            resp.getWriter().write(payload);
        } catch (ResourcePersistanceException e){
            logger.warn(e.getMessage());
            resp.setStatus(404);
            resp.getWriter().write(e.getMessage());
        }

    }
}