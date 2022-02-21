package local.jrmmba.points.services;

import local.jrmmba.points.models.Transaction;
import local.jrmmba.points.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Implements the restaurant service interface
 */
@Service(value = "transactionService")
public class TransactionServiceImpl
        implements TransactionService
{
    @Autowired
    private TransactionRepository transactionrepo;

    @Override
    public List<Transaction> findAll()
    {
        List<Transaction> list = new ArrayList<>();
        transactionrepo.findAll()
                .iterator()
                .forEachRemaining(list::add);
        return list;
    }

    @Override
    public Transaction save(Transaction transaction)
    {
        return transactionrepo.save(transaction);
    }
}
