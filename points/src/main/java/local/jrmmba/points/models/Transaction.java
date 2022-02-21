package local.jrmmba.points.models;

import javax.persistence.*;

/**
 * The entity allowing interactions with the Transaction table
 */
@Entity
@Table(name = "transactions")
public class Transaction
    extends Auditable
{
    /**
     * The primary key (long) of this transaction type
     */
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long transactionId;
}
