package local.jrmmba.points.models;

/**
 * A POJO used in reporting how an expenditure was assigned to earned points transactions
 */
public class ReportSpends
{
    /**
     * The payer
     */
    private String payer;

    /**
     * Points spent against the given payer
     */
    private int points;

    /**
     * Creates a new object using the given payer and points spent
     *
     * @param payer  the payer
     * @param points the points spent
     */
    public ReportSpends(String payer, int points)
    {
        this.payer = payer;
        this.points = points;
    }

    /**
     * Getter for the payer
     *
     * @return the payer
     */
    public String getPayer()
    {
        return payer;
    }

    /**
     * Setter for the payer
     *
     * @param payer the new payer for this object
     */
    public void setPayer(String payer)
    {
        this.payer = payer;
    }

    /**
     * Getter for the points spent against the given payer
     *
     * @return points spent against the given payer
     */
    public int getPoints()
    {
        return points;
    }

    /**
     * Setter for the points spent against the give payer
     *
     * @param points the new amount of points spent against the given payer
     */
    public void setPoints(int points)
    {
        this.points = points;
    }
}
