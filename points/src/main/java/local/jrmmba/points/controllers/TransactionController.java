package local.jrmmba.points.controllers;

import local.jrmmba.points.models.Spend;
import local.jrmmba.points.models.Transaction;
import local.jrmmba.points.services.SpendService;
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
@RequestMapping
public class TransactionController
{
    @Autowired
    private TransactionService transactionService;

    @Autowired
    private SpendService spendService;

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
     * @see TransactionService#save(Transaction) TransactionService.save(Transaction)
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

    /**
     * Spends the given amount of point
     * <br> Example: <a href="http://localhost:2022/points/spend">http://localhost:2022/points/spend</a>
     *
     * @param newSpend the amount of points to spend
     * @return List of payer and how much they were charged this transaction
     * @see SpendService#spend(Spend)  SpendService.spend(Spend)
     */
    @PostMapping(value = "/spend",
            consumes = {"application/json"},
            produces = {"application/json"})
    public ResponseEntity<?> addExpenditure(
            @Valid
            @RequestBody
                    Spend newSpend)
    {
        return new ResponseEntity<>(spendService.spend(newSpend), HttpStatus.OK);
    }

    /**
     * Returns a list of payers with their remaining points balance
     * <br>Example: <a href="http://localhost:2022/points/balance">http://localhost:2022/points/balance</a>
     *
     * @return the list of payers with their remaining points balance
     * @see TransactionService#getBalances()  TransactionService.getBalances()
     */
    @GetMapping(value = "/balance",
            produces = {"application/json"})
    public ResponseEntity<?> getCurrentBalanceOfPayers()
    {
        return new ResponseEntity<>(transactionService.getBalances(),
                HttpStatus.OK);
    }
}
