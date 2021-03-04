package Main.Model;
/**
 * The Outsourced class extends the Part class and simulates a Part
 * created by an outside company.
 */

public class Outsourced extends Part {

    private String companyName;
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Sets the company name
     * @param companyName the company name to set
     */
    public void setCompanyName(String companyName) { this.companyName = companyName; }

    /**
     * Returns the company name
     * @return the company name
     */
    public String getCompanyName() { return this.companyName; }
}
