package local.jrmmba.points.controllers;

import local.jrmmba.points.models.Transaction;
import local.jrmmba.points.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * The entry point for clients to access transaction data
 */
@RestController
@RequestMapping("/points")
public class TransactionController
{
    @Autowired
    private TransactionService transactionService;

    /**
     * Returns a list of all transactions
     * <br>Example: <a href="http://localhost:2022/points/transactions">http://localhost:2022/points/transactions</a>
     *
     * @return the list of transactions
     * @see TransactionService#findAll() TransactionService.findAll()
     */
    @GetMapping(value = "/transactions",
            produces = {"application/json"})
    public ResponseEntity<?> listAllTransactions()
    {
        List<Transaction> myTransactions = transactionService.findAll();
        return new ResponseEntity<>(myTransactions,
                HttpStatus.OK);
    }

    /**
     * Creates a new transaction based off of the data sent
     * <br> Example: <a href="http://localhost:2022/points/earn">http://localhost:2022/points/earn</a>
     *
     * @param newTransaction the data to create the new transaction
     * @return a status of created (201)
     * @see TransactionService#save(Transaction) TransactionService.save(Transaction
     */
    @PostMapping(value = "/earn",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addNewTransaction(
            @Valid
            @RequestBody
                    Transaction newTransaction)
    {
        transactionService.save(newTransaction);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
