package local.jrmmba.points.services;

import local.jrmmba.points.models.Transaction;

import java.util.List;

/**
 * The service that works with the transaction model
 */
public interface TransactionService
{
    /**
     * Returns a list of all transactions
     */
    List<Transaction> findAll();

    /**
     * Give a complete transaction object, the transaction object is saved to the database.
     *
     * @param transaction the transaction to be saved
     * @return the save transaction including the automatically generated id and audit fields
     */
    Transaction save(Transaction transaction);
}
