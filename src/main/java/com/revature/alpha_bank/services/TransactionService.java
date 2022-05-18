package com.revature.alpha_bank.services;

import com.revature.alpha_bank.daos.TransactionDao;
import com.revature.alpha_bank.exceptions.ResourcePersistanceException;
import com.revature.alpha_bank.util.logging.Logger;

public class TransactionService {

    private final TransactionDao transactionDao;
    public TransactionService (TransactionDao transactionDao) {
        this.transactionDao = transactionDao;
    }
    private Logger logger = Logger.getLogger();
    public String addToBalance (String deposit, String user_email){

        double updatedAccount = TransactionDao.addToBalance(deposit, user_email);
        if(updatedAccount == 0){
            throw new ResourcePersistanceException("Amount was not deposited in the database. ");
        }
        logger.info("Amount " + deposit + " of user email " + user_email + " has been deposited: " );
        return "Amount has been successful deposited.";
    }

    public String subtractFromBalance (String withdrawal, String user_email){

        double updatedAccount = TransactionDao.subtractFromBalance(withdrawal, user_email);
        if(updatedAccount == 0){
            throw new ResourcePersistanceException("Amount was not deposited in the database. ");
        }
        logger.info("Amount " + withdrawal + " of user email " + user_email + " has been withdrawn: " );
        return "Amount has been successful withdrawn.";

    }
}
