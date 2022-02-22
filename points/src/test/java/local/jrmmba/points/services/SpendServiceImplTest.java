package local.jrmmba.points.services;

import local.jrmmba.points.PointsApplicationTests;
import local.jrmmba.points.exceptions.InsufficientFundsException;
import local.jrmmba.points.models.ReportSpends;
import local.jrmmba.points.models.Spend;
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
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PointsApplicationTests.class)
public class SpendServiceImplTest
{
    @Autowired
    private SpendService spendService;

    @MockBean
    private TransactionRepository transactionRepo;

    private List<Transaction> transactionList = new ArrayList<>();
    private List<Spend> spendList = new ArrayList<>();

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

        Spend newSpend = new Spend(5000);
        newSpend.setSpendId(1);
        spendList.add(newSpend);
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test(expected = InsufficientFundsException.class)
    public void spendInsufficientFunds()
    {
        Mockito.when(transactionRepo.sumRemainingPoints())
                .thenReturn(1);
        List<ReportSpends> expected = new ArrayList<>();
        expected.add(new ReportSpends("DANNON", -100));
        expected.add(new ReportSpends("UNILEVER", -200));
        expected.add(new ReportSpends("MILLER COORS", -4700));

        assertEquals(expected, spendService.spend(spendList.get(0)));
    }

    @Test
    public void spend()
    {
        Mockito.when(transactionRepo.sumRemainingPoints())
                .thenReturn(10000);
        Mockito.when(transactionRepo.findAllByRemainingpointsNotOrderByTimestampAsc(0))
                .thenReturn(transactionList);

        List<ReportSpends> expected = new ArrayList<>();
        expected.add(new ReportSpends("DANNON", -100));
        expected.add(new ReportSpends("UNILEVER", -200));
        expected.add(new ReportSpends("MILLER COORS", -4700));

        assertEquals(expected.get(1).getPayer(), spendService.spend(new Spend(5000)).get(1).getPayer());
    }
}