package Main.View_Controller;

import Main.Model.Inventory;
import Main.Model.Part;
import Main.Model.Product;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The MainWindowController is the controller for the main
 * inventory management screen. This controller handles the product and part searches
 * along with the buttons to open the rest of the program's screens.
 */

public class MainWindowController {


    public TableView<Part> partListView;
    public TableColumn<Part, Integer> partIDColumn;
    public TableColumn<Part, String> partNameColumn;
    public TableColumn<Part, Integer> partInventoryCountColumn;
    public TableColumn<Part, Double> partCostColumn;

    public TableView<Product> productsListView;
    public TableColumn<Product, Integer> productIDColumn;
    public TableColumn<Product, String> productNameColumn;
    public TableColumn<Product, Integer> productInvColumn;
    public TableColumn<Product, Double> productCostColumn;


    public Alert deletionAlert = new Alert(Alert.AlertType.CONFIRMATION);
    public Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    public Stage stage;
    public Scene scene;

    public TextField partSearchField;
    public TextField productSearchField;

    /**
     * Sets up and displays the tables and columns needed to display the Parts table
     * and the products table
     */
    public void initialize() {

        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryCountColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        productIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));


        partListView.setItems(Inventory.getAllParts());
        productsListView.setItems(Inventory.getAllProducts());
    }

    /**
     * This function sends the selected part data to the "Modify Part" screen
     * for presentation
     * @param event Clicking the "Modify Part" button
     * @throws IOException needed for the FXMLLoader variable
     */
    public void sendPartData(MouseEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("modifyPart.fxml"));
        loader.load();

        ModifyPartController MPController = loader.getController();
        if(partListView.getSelectionModel().getSelectedItem() != null) {
            MPController.receivePart(partListView.getSelectionModel().getSelectedItem());
            loadNewScene(event, loader);
        }
        else {
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("No Part Selected");
            errorAlert.setContentText("Please select a part to modify");
            errorAlert.show();
        }
    }

    /**
     * Opens the "Add Part" screen
     * @throws IOException needed for the FXMLLoader variable
     */
    public void addPartButtonClicked() throws IOException {
        Parent root;

        root = FXMLLoader.load(getClass().getResource("addPart.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Add New Part");
        stage.setScene(new Scene(root, 600, 600));

        stage.show();
    }

    /**
     * Deletes the selected part from the inventory
     * Includes a confirmation for the user that they want the part deleted
     * Also includes a check to make sure a part is selected
     */
    public void deletePartButtonClicked() {

        if(partListView.getSelectionModel().getSelectedItem() == null) {
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("No selection made");
            errorAlert.setContentText("Please select a part to delete");
            errorAlert.show();
        }
        else{
            deletionAlert.setContentText("Are you sure you want to delete this part?");
            deletionAlert.showAndWait();
            if (deletionAlert.getResult() == ButtonType.OK) {
                partListView.getItems().remove(partListView.getSelectionModel().getSelectedItems());
                Inventory.deletePart(partListView.getSelectionModel().getSelectedItem());
            }
        }
    }

    /** RUNTIME ERROR
     * When the modify product button is clicked and there is no selection in the tableview
     * the program creates a stack trace dump from a NullPointerException.
     * This was solved by adding in a check to make sure a product has been selected, prior to
     * attempting to pass the data to the modify product controller.
     * A similar issue was also fixed for the sendPartData method.
     *
     * This methods sends the selected product data to the "Modify Product" screen
     * for presentation
     * @param event Clicking the "Modify Product" button
     * @throws IOException needed for the FXMLLoader variable
     */
    public void sendProductModifyData(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("modifyProduct.fxml"));
        loader.load();

        try {
            ModifyProductController MPController = loader.getController();
            MPController.receiveProduct(productsListView.getSelectionModel().getSelectedItem());
            loadNewScene(event, loader);
        }
        catch(NullPointerException e){
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("No Product Selected");
            errorAlert.setContentText("Please select a product to modify");
            errorAlert.show();
        }
    }
    /**
     * Opens the "Add Product" screen
     * @param event Clicking the "Add Product" button
     * @throws IOException needed for the FXMLLoader variable
     */
    public void addProductButtonClicked(MouseEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("addProduct.fxml"));
        loader.load();

        loadNewScene(event, loader);
    }

    /**
     * Calls the method sendProductModifyData which in turn opens the "Product Modify" screen
     * @param event Clicking the "Modify Product" button
     * @throws IOException needed because the sendProductModifyData needs it for the FXMLLoader variable
     */
    public void modifyProductButtonClicked(MouseEvent event) throws IOException {
        sendProductModifyData(event);
    }

    /**
     * Deletes the selected product from the inventory
     * Includes a confirmation for the user that they want the product deleted
     * Also includes a check to make sure a part is selected
     */
    public void deleteProductButtonClicked() {

        if(productsListView.getSelectionModel().getSelectedItem() == null) {
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("No selection made");
            errorAlert.setContentText("Please select a product to delete");
            errorAlert.show();
        }
        else if(productsListView.getSelectionModel().getSelectedItem().getAllAssociatedParts().isEmpty()) {
            deletionAlert.setContentText("Are you sure you want to delete this product?");
            deletionAlert.showAndWait();

            if (deletionAlert.getResult() == ButtonType.OK) {
                productsListView.getItems().remove(productsListView.getSelectionModel().getSelectedItems());
                Inventory.deleteProduct(productsListView.getSelectionModel().getSelectedItem());
            }
        }
        else {
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Unable to delete product");
            errorAlert.setContentText("Associated parts must be removed prior to deletion of this product");
            errorAlert.show();
        }
    }

    /**
     * Closes the application
     */
    public void exitButtonClicked() {
        Platform.exit();
    }

    /**
     * Includes the logic for the search part box. Includes errors if no parts are found.
     */
    public void searchPart() {
        String searchedPart = partSearchField.getText();
        ObservableList<Part> searchedPartsList = Inventory.lookupPart(searchedPart);
        if(searchedPartsList.size() == 0) {
            try {
                int partID = Integer.parseInt(searchedPart);
                Part sPart = Inventory.lookupPart(partID);
                if(sPart != null) {
                    searchedPartsList.add(sPart);
                }
                else {
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("No Part Found");
                    errorAlert.setContentText("Please try another search");
                    errorAlert.show();
                }
            } catch (NumberFormatException ignored){

            }
        }
       if(searchedPartsList.isEmpty()){
                errorAlert.setTitle("Error");
                errorAlert.setHeaderText("No Part Found");
                errorAlert.setContentText("Please try another search");
                errorAlert.show();
        }
        else {
            partListView.setItems(searchedPartsList);
        }
    }

    /**
     * Includes the logic for the search product box. Includes errors if no parts are found.
     */
    public void searchProduct(){
        String searchedProduct = productSearchField.getText();
        ObservableList<Product> searchedProductsList = Inventory.lookupProduct(searchedProduct);

        if(searchedProductsList.size() == 0){
            try {
                int productID = Integer.parseInt(searchedProduct);
                Product sProduct = Inventory.lookupProduct(productID);
                if(sProduct != null) {
                    searchedProductsList.add(sProduct);
                }
                else {
                    errorAlert.setTitle("Error");
                    errorAlert.setHeaderText("No Product Found");
                    errorAlert.setContentText("Please try another search");
                    errorAlert.show();
                }
            } catch (NumberFormatException ignored){

            }
        }
        if(searchedProductsList.isEmpty()){
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("No Product Found");
            errorAlert.setContentText("Please try another search");
            errorAlert.show();
        }
        else {
            productsListView.setItems(searchedProductsList);
        }
    }

    /**
     * Helper method to load new scenes
     * @param event event that triggered the method
     * @param loader the loader of the scene being loaded
     */
    private void loadNewScene(MouseEvent event, FXMLLoader loader) {
        Node node = (Node) event.getSource();
        stage = (Stage) node.getScene().getWindow();
        Parent scene = loader.getRoot();
        stage.setScene(new Scene(scene));
        stage.show();
    }
}
