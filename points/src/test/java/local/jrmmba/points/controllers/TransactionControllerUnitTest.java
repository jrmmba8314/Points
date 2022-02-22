package local.jrmmba.points.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import local.jrmmba.points.PointsApplicationTests;
import local.jrmmba.points.models.ReportSpends;
import local.jrmmba.points.models.Spend;
import local.jrmmba.points.models.Transaction;
import local.jrmmba.points.services.SpendService;
import local.jrmmba.points.services.TransactionService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = PointsApplicationTests.class)
@AutoConfigureMockMvc
public class TransactionControllerUnitTest
{
    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @MockBean
    private SpendService spendService;

    private List<Transaction> transactionList = new ArrayList<>();
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");

    @Before
    public void setUp() throws Exception
    {
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

        RestAssuredMockMvc.webAppContextSetup(webApplicationContext);

        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @After
    public void tearDown() throws Exception
    {
    }

    @Test
    public void listAllTransactions() throws Exception
    {
        String apiUrl = "/points/transactions";
        Mockito.when(transactionService.findAll())
                .thenReturn(transactionList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
                .andReturn();
        String tr = r.getResponse()
                .getContentAsString();

        assertThat(tr, containsString("UNILEVER"));
    }

    @Test
    public void addNewTransaction() throws Exception
    {
        String apiUrl = "/points/earn";
        Transaction newTransaction = new Transaction("TEST", 100, LocalDateTime.now());
        String transactionString = "{ \"payer\": \"TEST\", \"points\": 100, \"timestamp\": \"2020-11-02T14:00:00Z\" }";

        Mockito.when(transactionService.save(any(Transaction.class)))
                .thenReturn(newTransaction);

        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(transactionString);

        mockMvc.perform(rb)
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void addExpenditure() throws Exception
    {
        List<ReportSpends> rtnSpendReport = new ArrayList<>();
        rtnSpendReport.add(new ReportSpends("TEST", 100));
        String apiUrl = "/points/spend";
        Mockito.when(spendService.spend(any(Spend.class)))
                .thenReturn(rtnSpendReport);

        String spendJSON = "{ \"points\": 5000 }";
        RequestBuilder rb = MockMvcRequestBuilders.post(apiUrl)
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(spendJSON);
        MvcResult r = mockMvc.perform(rb)
                .andReturn();
        String tr = r.getResponse()
                .getContentAsString();

        assertThat(tr, containsString("TEST"));
    }

    @Test
    public void getCurrentBalanceOfPayers() throws Exception
    {
        LinkedHashMap<String, Integer> rtnBalanceList = new LinkedHashMap<>();
        rtnBalanceList.put("TEST", 100);
        String apiUrl = "/points/balance";
        Mockito.when(transactionService.getBalances())
                .thenReturn(rtnBalanceList);

        RequestBuilder rb = MockMvcRequestBuilders.get(apiUrl)
                .accept(MediaType.APPLICATION_JSON);
        MvcResult r = mockMvc.perform(rb)
                .andReturn();
        String tr = r.getResponse()
                .getContentAsString();

        ObjectMapper mapper = new ObjectMapper();
        String er = mapper.writeValueAsString(rtnBalanceList);

        System.out.println(er);
        assertEquals(er,
                tr);
    }
}