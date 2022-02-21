package local.jrmmba.points.repositories;

import local.jrmmba.points.models.Transaction;
import org.springframework.data.repository.CrudRepository;

/**
 * Connects the transactions table to the rest of the application
 */
public interface TransactionRepository
        extends CrudRepository<Transaction, Long>
{
}
