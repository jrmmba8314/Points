package local.jrmmba.points.repositories;

import local.jrmmba.points.models.Spend;
import org.springframework.data.repository.CrudRepository;

/**
 * Connects the spends table to the rest of the application
 */
public interface SpendRepository
        extends CrudRepository<Spend, Long>
{

}
