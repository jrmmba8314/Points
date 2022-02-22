package local.jrmmba.points.services;

import local.jrmmba.points.exceptions.ResourceNotFoundException;
import local.jrmmba.points.models.Transaction;
import local.jrmmba.points.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;

/**
 * Implements the transaction service interface
 */
@Service(value = "transactionService")
public class TransactionServiceImpl
        implements TransactionService
{
    @Autowired
    private TransactionRepository transactionRepo;

    @Override
    public List<Transaction> findAll()
    {
        return transactionRepo.findAllByOrderByTimestampAsc();
    }

    @Override
    public Transaction save(Transaction transaction)
    {
        if (transaction.getTransactionId() > 0)
        {
            if (!transactionRepo.findById(transaction.getTransactionId())
                    .isPresent())
            {
                throw new ResourceNotFoundException("Transaction id " + transaction.getTransactionId() + " not found!");
            }

        } else
        {
            transaction.setRemainingpoints(transaction.getPoints());
        }
        return transactionRepo.save(transaction);
    }

    @Override
    public LinkedHashMap<String, Integer> getBalances()
    {
        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        List<Transaction> transactionList = findAll();
        for (Transaction t : transactionList)
        {
            if (!map.containsKey(t.getPayer()))
            {
                map.put(t.getPayer(), t.getRemainingpoints());
            } else
            {
                map.put(t.getPayer(), map.get(t.getPayer()) + t.getRemainingpoints());
            }
        }
        return map;
    }
}
