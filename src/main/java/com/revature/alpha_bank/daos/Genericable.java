package com.revature.alpha_bank.daos;

import java.io.IOException;
import java.util.List;


public interface Genericable<T> {

    // Create
    T create(T newObject);

    // Read
    List<T> findAll() throws IOException;
   List<T> findByUser_email(String user_email);

    // Update
    int update(String last_name, String email);

    //Delete
    int delete (String User_email);

}

