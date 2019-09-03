package me.antoniocaccamo.sample.concurrency.runnables;

import lombok.extern.slf4j.Slf4j;
import me.antoniocaccamo.sample.concurrency.beans.BankAccount;
import me.antoniocaccamo.sample.concurrency.beans.BankAccountTransaction;
import me.antoniocaccamo.sample.concurrency.dao.BankAccountDao;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.concurrent.Callable;

@Slf4j
public class ReportProcessor implements Callable<Boolean> {

    private final BankAccountDao bankAccountDao;
    private final BankAccount   bankAccount;

    public ReportProcessor(BankAccountDao bankAccountDao, BankAccount bankAccount) {
        this.bankAccountDao = bankAccountDao;
        this.bankAccount = bankAccount;
    }


    @Override
    public Boolean call() throws Exception {

        Boolean reportGenerated = Boolean.FALSE;

        List<BankAccountTransaction> transactions = bankAccountDao.getTransactionsForAccount(bankAccount);
        File file = new File(  "D:/reports/" + bankAccount.getAccNumber() + "_tx_report.txt");
        for (BankAccountTransaction transaction : transactions) {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                writer.write("Account Number: " + transaction.getAccNumber());
                writer.write("Transaction type: " + transaction.getTxType());
                writer.write("Tx Id: " + transaction.getTxId());
                writer.write("Amount: " + transaction.getAmount());
                writer.write("Transaction Date: " + transaction.getTxDate());
                writer.newLine();
                writer.flush();
            }
            reportGenerated = true;

        }
        return reportGenerated;

    }
}
