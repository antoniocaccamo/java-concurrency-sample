package me.antoniocaccamo.sample.concurrency.dao;

import me.antoniocaccamo.sample.concurrency.beans.BankAccount;
import me.antoniocaccamo.sample.concurrency.beans.BankAccountTransaction;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ejb.Stateless;


@Stateless
public class BankAccountDao {

    @Resource
    private DataSource dataSource;


    public List<BankAccount> getAllBankAccounts() {

        List<BankAccount> accounts = new ArrayList<>();
        BankAccount account = null;
        try ( Connection connection = dataSource.getConnection() ){
            Statement statement = connection.createStatement();
            ResultSet set = statement.executeQuery("select * from bank_account");
            while (set.next()) {
                account = new BankAccount();
                account.setAccNumber(set.getInt("acc_number"));
                account.setName(set.getString("acc_holder_name"));
                account.setEmail(set.getString("acc_email"));
                account.setAccType(set.getString("acc_type"));
                accounts.add(account);
            }
            return accounts;
        } catch (SQLException ex) {
            Logger.getLogger(BankAccountDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return accounts;
    }

    public List<BankAccountTransaction> getTransactionsForAccount(BankAccount account) {
        BankAccountTransaction transaction = null;
        List<BankAccountTransaction> transactions = new ArrayList<>();
        try ( Connection connection = dataSource.getConnection() ){

            PreparedStatement statement = connection.prepareStatement("select * from bank_account_transaction where acc_number=?");
            statement.setInt(1, account.getAccNumber());
            ResultSet set = statement.executeQuery();

            while (set.next()) {
                transaction = new BankAccountTransaction();
                transaction.setAccNumber(set.getInt("acc_number"));
                transaction.setAmount(set.getDouble("amount"));
                transaction.setTxDate(new Date(set.getDate("transaction_date").getTime()));
                transaction.setTxId(set.getInt("tx_id"));
                transaction.setTxType(set.getString("transaction_type"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(BankAccountDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return transactions;
    }


}
