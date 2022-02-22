package local.jrmmba.points.services;

import local.jrmmba.points.models.Transaction;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * The service that works with the transaction model
 */
public interface TransactionService
{
    /**
     * Returns a list of all transactions
     *
     * @return List of all transactions sorted by timestamp, earliest first
     */
    List<Transaction> findAll();

    /**
     * Give a complete transaction object, the transaction object is saved to the database.
     * if the transactionId is 0, the object is added to the database
     * if the transactionIs is not 0, the object is updated in the database based off of the given id
     *
     * @param transaction the transaction to be saved
     * @return the save transaction including the automatically generated id and audit fields
     */
    Transaction save(Transaction transaction);

    /**
     * gets the balances of each payer
     *
     * @return the balances of each payer
     */
    LinkedHashMap<String, Integer> getBalances();
}
