package local.jrmmba.points.services;

import local.jrmmba.points.PointsApplicationTests;
import local.jrmmba.points.exceptions.ResourceNotFoundException;
import local.jrmmba.points.models.Transaction;
import local.jrmmba.points.repositories.TransactionRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PointsApplicationTests.class)
public class TransactionServiceImplTest
{
    @Autowired
    private TransactionService transactionService;

    @MockBean
    private TransactionRepository transactionRepo;

    private List<Transaction> transactionList = new ArrayList<>();

    @Before
    public void setUp() throws Exception
    {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

        Transaction newAdd = new Transaction("DANNO", 1000, LocalDateTime.parse("2020-11-02T14:00:00Z", formatter));
        newAdd.setTransactionId(1);
        transactionList.add(newAdd);

        newAdd = new Transaction("UNILEVER", 200, LocalDateTime.parse("2020-10-31T11:00:00Z", formatter));
        newAdd.setTransactionId(2);
        transactionList.add(newAdd);

        newAdd = new Transaction("DANNON", -200, LocalDateTime.parse("2020-10-31T15:00:00Z", formatter));
        newAdd.setTransactionId(3);
        transactionList.add(newAdd);

        newAdd = new Transaction("MILLER COORS", 10000, LocalDateTime.parse("2020-11-01T14:00:00Z", formatter));
        newAdd.setTransactionId(4);
        transactionList.add(newAdd);

        newAdd = new Transaction("DANNON", 300, LocalDateTime.parse("2020-10-31T10:00:00Z", formatter));
        newAdd.setTransactionId(5);
        transactionList.add(newAdd);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void findAll()
    {
        Mockito.when(transactionRepo.findAllByOrderByTimestampAsc())
                .thenReturn(transactionList);

        assertEquals(transactionList.get(1).getPayer(), transactionService.findAll().get(1).getPayer());
    }

    @Test
    public void save()
    {
        Transaction newTransaction = new Transaction("Test", 100, LocalDateTime.now());
        newTransaction.setTransactionId(0);
        Mockito.when(transactionRepo.save(any(Transaction.class)))
                .thenReturn(newTransaction);

        assertEquals(newTransaction.getPayer(), transactionService.save(newTransaction).getPayer());
    }

    @Test
    public void saveUpdate()
    {
        Transaction newTransaction = new Transaction("Test", 100, LocalDateTime.now());
        newTransaction.setTransactionId(1);
        Mockito.when(transactionRepo.save(any(Transaction.class)))
                .thenReturn(newTransaction);
        Mockito.when(transactionRepo.findById(any(Long.class)))
                .thenReturn(Optional.of(newTransaction));

        assertEquals(newTransaction.getPayer(), transactionService.save(newTransaction).getPayer());
    }

    @Test(expected = ResourceNotFoundException.class)
    public void saveUpdateNotFound()
    {
        Transaction newTransaction = new Transaction("Test", 100, LocalDateTime.now());
        newTransaction.setTransactionId(1);
        Mockito.when(transactionRepo.save(any(Transaction.class)))
                .thenReturn(newTransaction);
        Mockito.when(transactionRepo.findById(any(Long.class)))
                .thenReturn(Optional.empty());

        assertEquals(newTransaction.getPayer(), transactionService.save(newTransaction).getPayer());
    }

    @Test
    public void getBalances()
    {
        Mockito.when(transactionRepo.findAllByOrderByTimestampAsc())
                .thenReturn(transactionList);

        LinkedHashMap<String, Integer> actualMap = transactionService.getBalances();
        int actualPoints = actualMap.get("UNILEVER");
        assertEquals(200, actualPoints);
    }
}