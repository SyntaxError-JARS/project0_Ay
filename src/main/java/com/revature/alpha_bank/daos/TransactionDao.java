package com.revature.alpha_bank.daos;

import com.revature.alpha_bank.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDao {

    public static int addToBalance(String deposit, String User_email) {
        int affectedRow;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {




            String sql = String.format("update account_info set balance = balance + ?  where user_email = ?");

            double x = Double.parseDouble(deposit);

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, x);
            ps.setString(2, User_email);




            affectedRow = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return affectedRow;

    }


    public static int subtractFromBalance(String withdrawal, String User_email) {
        int affectedRow;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            double x = Double.parseDouble(withdrawal);
            String sql = String.format("update account_info set balance = balance - ?  where user_email = ? ");


            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1, x);
            ps.setString(2, User_email);




            affectedRow = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return affectedRow;

    }


}
