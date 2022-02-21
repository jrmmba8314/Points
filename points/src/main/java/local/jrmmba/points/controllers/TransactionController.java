package local.jrmmba.points.controllers;

import local.jrmmba.points.services.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The entry point for clients to access transaction data
 */
@RestController
@RequestMapping("/points")
public class TransactionController
{
    @Autowired
    private TransactionService transactionService;
}
