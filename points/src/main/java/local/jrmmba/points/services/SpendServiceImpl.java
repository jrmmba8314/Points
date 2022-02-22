package local.jrmmba.points.services;

import local.jrmmba.points.exceptions.InsufficientFundsException;
import local.jrmmba.points.models.ReportSpends;
import local.jrmmba.points.models.Spend;
import local.jrmmba.points.models.Transaction;
import local.jrmmba.points.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Implements the SpendService interface
 */
@Service(value = "spendService")
public class SpendServiceImpl
        implements SpendService
{
    @Autowired
    private TransactionRepository transactionRepo;

    @Override
    public List<ReportSpends> spend(Spend spend)
    {
        int sumRemainingPoints = transactionRepo.sumRemainingPoints();
        if (spend.getPoints() > sumRemainingPoints)
        {
            throw new InsufficientFundsException("Remaining balance of " + sumRemainingPoints +
                    " is less than the amount you are trying to spend " + spend.getPoints());
        }

        LinkedHashMap<String, Integer> map = new LinkedHashMap<>();
        int remainingToSpend = spend.getPoints();
        for (Transaction t : transactionRepo.findAllByRemainingpointsNotOrderByTimestampAsc(0))
        {
            int transactionSpent;
            if (t.getRemainingpoints() >= remainingToSpend)
            {
                t.setRemainingpoints(t.getRemainingpoints() - remainingToSpend);
                transactionSpent = remainingToSpend;
                remainingToSpend = 0;
            } else
            {
                remainingToSpend = remainingToSpend - t.getRemainingpoints();
                transactionSpent = t.getRemainingpoints();
                t.setRemainingpoints(0);
            }

            if (!map.containsKey(t.getPayer()))
            {
                map.put(t.getPayer(), transactionSpent);
            } else
            {
                map.put(t.getPayer(), map.get(t.getPayer()) + transactionSpent);
            }

            transactionRepo.save(t);
            if (remainingToSpend == 0)
            {
                break;
            }
        }

        // convert HashMap to POJO for reporting to client
        List<ReportSpends> rtnReportSpends = new ArrayList<>();

        map.entrySet().forEach(entry -> rtnReportSpends.add(new ReportSpends(entry.getKey(), -1 * entry.getValue())));
        return rtnReportSpends;
    }
}
