package Main.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Inventory class simulates an inventory of parts and products
 */
public class Inventory {
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();
    private static int partID = 0;
    private static int productID = 0;

    /**
     * Generates a new part ID
     * @return the partID, an even number
     */
    public static int generatePartID() {
        return partID += 2;
    }

    /**
     * Generates a new product ID
     * @return the productID, an odd number
     */
    public static int generateProductID() {
        productID +=1;
        if(productID % 2 == 0) {
            return productID += 1;
        }
        else
            return productID;
    }

    /**
     * Adds a new part to the inventory
     * @param newPart the new part to be added to the inventory
     */
    public static void addPart(Part newPart){
        Inventory.allParts.add(newPart);
    }

    /**
     * Adds a new product to the inventory
     * @param newProduct the new product to be added to the inventory
     */
    public static void addProduct(Product newProduct) {
        Inventory.allProducts.add(newProduct);
    }

    /**
     * Looks up a part based on the part ID
     * @param id the ID of the part requested
     * @return the part that matches the ID
     */
    public static Part lookupPart(int id) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        for(Part searchedPart : allParts)
        {
            if(searchedPart.getId() == id){
                return searchedPart;
            }
        }
        return null;
    }

    /**
     * Looks up a product based on the product ID
     * @param id the ID of the product requested
     * @return the product that matches the ID
     */
    public static Product lookupProduct(int id) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();
        for(Product searchedProduct : allProducts)
        {
            if(searchedProduct.getId() == id){
                return searchedProduct;
            }
        }
        return null;
    }

    /**
     * Returns an ObservableList of parts that match the given String
     * @param partName the name of the part(s) requested
     * @return a list of parts that match the partName
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> searchedParts = FXCollections.observableArrayList();
        ObservableList<Part> allParts = Inventory.getAllParts();

        for(Part searchedPart : allParts){
            if(searchedPart.getName().contains(partName)){
                searchedParts.add(searchedPart);
            }
        }
        return searchedParts;
    }

    /**
     * Returns an ObservableList of products that match the given String
     * @param productName the name of the product(s) requested
     * @return a list of products that match the productName
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> searchedProducts = FXCollections.observableArrayList();
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for(Product searchProduct : allProducts){
            if(searchProduct.getName().contains(productName)){
                searchedProducts.add(searchProduct);
            }
        }
        return searchedProducts;
    }

    /**
     * Sets the given part to the given index position
     * @param index the index position of where to set the part
     * @param selectedPart the part that will be placed at the index
     */
    public static void updatePart(int index, Part selectedPart) {
        Inventory.allParts.set(index,selectedPart);
    }

    /**
     * Sets the given product to the given index position
     * @param index the index position of where to set the given product
     * @param selectedProduct the product that will be places at the index
     */
    public static void updateProduct(int index, Product selectedProduct) {
        Inventory.allProducts.set(index, selectedProduct);
    }

    /**
     * Returns true or false depending on if the selected part was deleted or not
     * @param selectedPart the part that will be deleted
     * @return true if the part was deleted, false if not
     */
    public static boolean deletePart(Part selectedPart) {

        for (int i = 0; i < Inventory.allParts.size(); i++) {
            if (Inventory.allParts.get(i) == selectedPart) {
                Inventory.allParts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns true or false depending on if the selected product was deleted or not
     * @param selectedProduct the product that will be deleted
     * @return true if the product was deleted, false if not
     */
    public static boolean deleteProduct(Product selectedProduct) {

        for (int i = 0; i < Inventory.allProducts.size(); i++) {
            if(Inventory.allProducts.get(i) == selectedProduct) {
                Inventory.allProducts.remove(i);
                return true;
            }
        }
        return false;
    }

    /**
     * returns the static ObservableList allParts
     * @return the allParts ObservableList
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * returns the static ObservableList allProducts
     * @return the allProducts ObservableList
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }

}
