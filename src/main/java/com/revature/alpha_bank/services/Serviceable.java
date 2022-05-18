package com.revature.alpha_bank.services;

import java.util.List;

public interface Serviceable<T> {

    // Create
    T create(T newObject);

    // Read
    List<T> readAll();
    List<T> findByUser_email(String last_name);

    // Update
    String update(String last_name, String email);

    // Delete
    String delete(String id);

    boolean validateInput(T object);


}
