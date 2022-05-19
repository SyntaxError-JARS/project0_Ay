package com.revature.alpha_bank.daos;

import com.revature.alpha_bank.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDao {

    public static int addToBalance(String deposit, String User_email) {
        int affectedRow;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = String.format("update account_info set deposit = deposit + ?  where user_email = ?");

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
        int affectedRow = 0;

        try {
            if (Double.parseDouble(withdrawal) < 0) {
                System.out.println(" Please enter a positive value. ");
            } else {
                try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

                    String sql = String.format("update account_info set withdrawal = withdrawal + ?  where User_email = ?");

                    double x = Double.parseDouble(withdrawal);

                    PreparedStatement ps = conn.prepareStatement(sql);
                    ps.setDouble(1, x);
                    ps.setString(2, User_email);

                    affectedRow = ps.executeUpdate();

                } catch (SQLException e) {
                    System.out.println(" Please make sure that the data entered was correct");
                }
            }

        } catch (NumberFormatException e) {
            System.out.println("Hey, you entered something wrong...");
        }
        return affectedRow;
    }

}







