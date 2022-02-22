package local.jrmmba.points.services;

import local.jrmmba.points.models.ReportSpends;
import local.jrmmba.points.models.Spend;

import java.util.List;

/**
 * The service that works with the spend model
 */
public interface SpendService
{
    /**
     * Applies the points being spent to the transactions in order of oldest to newest
     *
     * @param spend the total amount requesting to be spent.
     * @return A summary ReportSpends listing the payer and how many points where spent against them
     */
    List<ReportSpends> spend(Spend spend);
}
