package local.jrmmba.points.repositories;

import local.jrmmba.points.models.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Connects the transactions table to the rest of the application
 */
public interface TransactionRepository
        extends CrudRepository<Transaction, Long>
{
    /**
     * finds all the transactions and orders them by timestamp, earliest first.
     *
     * @return all transactions ordered by timestamp
     */
    List<Transaction> findAllByOrderByTimestampAsc();

    /**
     * finds all the transactions with a remaining points balance that is not equal to the given parameter
     * and order them by timestamp, earliest first.
     *
     * @param points exclude transactions with these points. Usually will be 0
     * @return transactions ordered by timestamp
     */
    List<Transaction> findAllByRemainingpointsNotOrderByTimestampAsc(int points);

    /**
     * Returns the total amount of points that can be spent.
     *
     * @return the total amount of points (int) that can be spent
     */
    @Query(value = "select sum(remainingpoints) as sumRemainingPoints from transactions where remainingpoints > 0",
            nativeQuery = true)
    int sumRemainingPoints();
}
