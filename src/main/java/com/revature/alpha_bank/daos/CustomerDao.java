package com.revature.alpha_bank.daos;

import com.revature.alpha_bank.exceptions.ResourcePersistanceException;
import com.revature.alpha_bank.models.Customer;
import com.revature.alpha_bank.util.ConnectionFactory;
import com.revature.alpha_bank.util.logging.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

public class CustomerDao implements Genericable<Customer>{

    private Logger logger = Logger.getLogger();

    @Override
    public Customer create(Customer newCustomer) {
        System.out.println("Here is the newCustomer as it enters the DAO layer: "+ newCustomer);

        try(Connection conn = ConnectionFactory.getInstance().getConnection();) {

            String sql = "insert into bank_customers (fname, lname,  email, password,dob ) values (?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, newCustomer.getFname());
            ps.setString(2, newCustomer.getLname());
            ps.setString(3, newCustomer.getEmail());
            ps.setString(4, newCustomer.getPassword());
            ps.setString(5, newCustomer.getDob());

            int checkInsert = ps.executeUpdate();

            if(checkInsert == 0){
                throw new ResourcePersistanceException("User was not entered into database due to some issue.");
            }

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return newCustomer;
    }

    @Override
    public List<Customer> findAll() throws IOException {

    List<Customer> customers = new LinkedList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = "SELECT * FROM bank_customers";//WHERE first_name like ?
/*          PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, "A%");
            ResultSet rs = ps.executeQuery(sql);*/
            Statement s = conn.createStatement();
            ResultSet rs =s.executeQuery(sql);
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setFname(rs.getString("fname"));
                customer.setLname(rs.getString("lname"));
                customer.setEmail(rs.getString("email"));
                customer.setPassword(rs.getString("password"));
                customer.setDob(rs.getString("dob"));

                ;


                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        System.out.println("Returning customer information to user.");
        return customers;
    }

    @Override
    public List<Customer> findByUser_email(String email) {

        List<Customer> customers = new LinkedList<>();
        try(Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql = "select * from bank_customers where email = ?";

            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery(); // remember sql, bc selects are the keywords

            Customer customer = new Customer();

            if(!rs.next()){
                throw new ResourcePersistanceException("User was not found in the database, please check last name entered was correct.");
            }

            customer.setFname(rs.getString("fname"));
            customer.setLname(rs.getString("lname"));
            customer.setEmail(rs.getString("email"));
            customer.setPassword(rs.getString("password"));
            customer.setDob(rs.getString("dob"));

            customers.add(customer);

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
        return customers;
    }

    @Override
    public int update(String lname, String email) {
        //System.out.println("Hello there Dao");
        int affectedRow;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql = String.format( "update bank_customers set lname = ? where email = ? ");
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, lname);
            ps.setString(2, email);

            affectedRow = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return affectedRow;
    }

    @Override
    public int delete (String fname) {

        int affectedRow = 0;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()){

            String sql = String.format( "delete from bank_customers where fname = ?");
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, fname);
            affectedRow = ps.executeUpdate();

         } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return affectedRow;
    }
    public Customer authenticateCustomer(String email, String password){

        try (Connection conn = ConnectionFactory.getInstance().getConnection()){
            String sql = "select * from bank_customers where email = ? and password = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if(!rs.next()){
                return null;
            }

            Customer customer = new Customer();

            customer.setFname(rs.getString("fname")); // this column label MUST MATCH THE DB
            customer.setLname(rs.getString("lname"));
            customer.setEmail(rs.getString("email"));
            customer.setPassword(rs.getString("password"));
            customer.setDob(rs.getString("dob"));

            return customer;

        } catch (SQLException e){
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkEmail(String email) {

        try(Connection conn = ConnectionFactory.getInstance().getConnection()){
            String sql = "select email from bank_customers where email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if(!rs.isBeforeFirst()){
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

