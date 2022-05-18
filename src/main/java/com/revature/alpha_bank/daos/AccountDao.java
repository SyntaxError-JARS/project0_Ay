package com.revature.alpha_bank.daos;

import com.revature.alpha_bank.exceptions.ResourcePersistanceException;
import com.revature.alpha_bank.models.Account;
import com.revature.alpha_bank.util.ConnectionFactory;
import com.revature.alpha_bank.util.logging.Logger;

import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;


public class AccountDao implements Genericable<Account> {

    private Logger logger = Logger.getLogger();

    @Override
    public Account create(Account newAccount) {
        try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

            String sql = "insert into account_info values (default, ?, ?, ?, ?, ?, ?)";

            PreparedStatement ps = conn.prepareStatement(sql);

            // 1-indexed, so first ? starts are 1
            ps.setDouble(1, newAccount.getBalance());
            ps.setString(2, newAccount.getAccount_type());
            ps.setDouble(3, newAccount.getDeposit());
            ps.setDouble(4, newAccount.getWithdrawal());
            ps.setString(5, newAccount.getOpen_date());
            ps.setString(6, newAccount.getUser_email());


            int checkInsert = ps.executeUpdate();

            if (checkInsert == 0) {
                throw new ResourcePersistanceException("Account was not persisted. ");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return newAccount;
    }

    @Override
    public List<Account> findAll() throws IOException {

        List<Account> accounts = new LinkedList<>();

        try (Connection conn = ConnectionFactory.getInstance().getConnection();) {

            String sql = "select * from account_info";
            Statement s = conn.createStatement();

            ResultSet rs = s.executeQuery(sql);

            while (rs.next()) {
                Account account = new Account();
                account.setId(rs.getInt("id"));
                account.setBalance(rs.getDouble("balance"));
                account.setAccount_type(rs.getString("account_type"));
                account.setDeposit(rs.getDouble("deposit"));
                account.setWithdrawal(rs.getDouble("withdrawal"));
                account.setOpen_date(rs.getString("open_date"));
                account.setUser_email(rs.getString("user_email"));

                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return accounts;
    }

    @Override
    public List<Account> findByUser_email(String user_email) {
        List<Account> accounts = new LinkedList<>();
        try (Connection conn = ConnectionFactory.getInstance().getConnection();) { // try with resources, because Connection extends the interface Auto-Closeable

            String sql = "select * from account_info where user_email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, user_email);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                throw new ResourcePersistanceException("Account was not found in the database, please check ID entered was correct.");
            }

            Account account = new Account();
            account.setId(rs.getInt("id"));
            account.setBalance(rs.getDouble("balance"));
            account.setAccount_type(rs.getString("account_type"));
            account.setDeposit(rs.getDouble("deposit"));
            account.setWithdrawal(rs.getDouble("withdrawal"));
            account.setOpen_date(rs.getString("open_date"));
            account.setUser_email(rs.getString("user_email"));

            accounts.add(account);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return accounts;
    }

    @Override
    public int update(String account_type, String user_email) {
        int affectedRow;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = String.format("update account_info set account_type = ? where user_email = ? ");
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, account_type);
            ps.setString(2, user_email);

            affectedRow = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return affectedRow;

    }

    @Override
    public int delete(String user_email) {
        int affectedRow = 0;
        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {

            String sql = String.format("delete from account_info where user_email = ?");
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user_email);
            affectedRow = ps.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return affectedRow;
    }

    public Account authenticateAccount(String account_type, String user_email) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from account_info where account_type = ? and user_email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, account_type);
            ps.setString(2, user_email);

            ResultSet rs = ps.executeQuery();

            if (!rs.next()) {
                return null;
            }

            Account account = new Account();

            account.setBalance(rs.getDouble("balance"));
            account.setAccount_type(rs.getString("account_type"));
            account.setDeposit(rs.getDouble("deposit"));
            account.setWithdrawal(rs.getDouble("withdrawal"));
            account.setOpen_date(rs.getString("open_date"));
            account.setUser_email(rs.getString("user_email"));


            return account;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean checkUser_email(String user_email) {

        try (Connection conn = ConnectionFactory.getInstance().getConnection()) {
            String sql = "select * from account_info where user_email = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, user_email);

            ResultSet rs = ps.executeQuery();
            if (!rs.isBeforeFirst()) {
                return false;
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}