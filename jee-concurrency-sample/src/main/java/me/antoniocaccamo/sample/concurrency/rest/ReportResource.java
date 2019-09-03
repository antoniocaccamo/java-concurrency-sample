package me.antoniocaccamo.sample.concurrency.rest;

import me.antoniocaccamo.sample.concurrency.beans.BankAccount;
import me.antoniocaccamo.sample.concurrency.dao.BankAccountDao;
import me.antoniocaccamo.sample.concurrency.runnables.ReportProcessor;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.ws.rs.Path;

/**
 *


 */

@Path("reports")
public class ReportResource {

    @Resource
    private ManagedExecutorService managedExecutorService;

    @Inject
    private BankAccountDao bankAccountDao;

    @GET
    public String reports(){

        for (BankAccount bankAccount : bankAccountDao.getAllBankAccounts()){

            ReportProcessor processor = new ReportProcessor(bankAccountDao, bankAccount);
            managedExecutorService.submit(processor);
        }

        return "Java EE concurrency starts!";
    }

}

