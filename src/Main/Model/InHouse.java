package Main.Model;

/**
 * The InHouse class extends the Part class and simulates a part
 * created by a company itself.
 */
public class InHouse extends Part {
    private int machineId;
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     *
     * @param machineId the machineId to set
     */
    public void setMachineId(int machineId) { this.machineId = machineId; }

    /**
     *
     * @return the machineId
     */
    public int getMachineId() { return this.machineId; }
}
