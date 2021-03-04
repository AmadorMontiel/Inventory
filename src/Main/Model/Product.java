package Main.Model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The product class simulates a product that exists in the inventory
 * and can be associated with a none or many parts
 */
public class Product {

    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Returns the id of the product
     * @return the id
     */
    public int getId() { return this.id; }

    /**
     * Returns the name of the product
     * @return the name
     */
    public String getName() { return this.name; }

    /**
     * Returns the price of the product
     * @return the price
     */
    public double getPrice() { return this.price; }

    /**
     * Returns the stock of the product
     * @return the stock
     */
    public int getStock() { return this.stock; }

    /**
     * Returns the minimum number of products
     * @return the min
     */
    public int getMin() { return this.min; }

    /**
     * Returns the maximum number of products
     * @return the max
     */
    public int getMax() { return this.max; }

    /**
     * Sets the id of the product
     * @param id the id to set
     */
    public void setId(int id) { this.id = id; }

    /**
     * Sets the name of the product
     * @param name the name to set
     */
    public void setName(String name) { this.name = name; }

    /**
     * Sets the price of the product
     * @param price the price to set
     */
    public void setPrice(double price) { this.price = price; }

    /**
     * Sets the stock of the product
     * @param stock the stock to set
     */
    public void setStock(int stock) { this.stock = stock; }

    /**
     * Sets the minimum of the product
     * @param min the min to set
     */
    public void setMin(int min) { this.min = min; }

    /**
     * Sets the maximum of the product
     * @param max the max to set
     */
    public void setMax(int max) { this.max = max; }

    /**
     * Adds the given part to the ObservableList associatedParts
     * @param part the part to add
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Returns true or false if the associated part is deleted
     * @param selectedAssociatedPart the part to delete
     * @return true if the part is deleted, otherwise false
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {

        for (int i = 0; i < associatedParts.size(); i++) {
            if(associatedParts.get(i) == selectedAssociatedPart){
                associatedParts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the ObservableList associatedParts
     * @return associatedParts ObservableList
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }
}
