package Main.View_Controller;

import Main.Model.Inventory;
import Main.Model.Part;
import Main.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The AddProductController is the controller for the AddProduct window.
 * This controller handles the addition of associated parts to the product
 * and the addition of the product to the inventory itself
 */
public class AddProductController {

    public TableView<Part> partsTableView;
    public TableColumn<Part, Integer> partIDColumn;
    public TableColumn<Part, String> partNameColumn;
    public TableColumn<Part, Integer> partInvColumn;
    public TableColumn<Part, Double> partPriceColumn;

    public TableView<Part> associatedPartsTableView;
    public TableColumn<Part, Integer> associatedPartIDColumn;
    public TableColumn<Part, String> associatedPartNameColumn;
    public TableColumn<Part, Integer> associatedPartInvColumn;
    public TableColumn<Part, Double> associatedPartCostColumn;

    public TextField productIDTextField;
    public TextField productNameTextField;
    public TextField productInvTextField;
    public TextField productPriceTextField;
    public TextField productMaxTextField;
    public TextField productMinTextField;

    public Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    public Alert inputAlert = new Alert(Alert.AlertType.ERROR);

    private final ObservableList<Part> associatedPartsList = FXCollections.observableArrayList();
    public TextField partSearchField;

    /**
     * Sets up and displays the tables and columns needed to display the Parts
     * table and the associated parts table
     */
    public void initialize() {
        partIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        partsTableView.setItems(Inventory.getAllParts());

        associatedPartIDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        associatedPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        associatedPartInvColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        associatedPartCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        associatedPartsTableView.setItems(associatedPartsList);
    }

    /**
     * Creates a new product and adds it to the inventory. Also utilizes the associatedPartsPrice
     * method to do error checking on the price of the created product.
     * @param event clicking the "save" button triggers the method
     * @throws IOException needed to call the close() method
     */
    public void saveNewProduct(MouseEvent event) throws IOException {

        if(!isAnError() && (Double.parseDouble(productPriceTextField.getText()) >= associatedPartsPrice()) ) {

            int id = Inventory.generateProductID();
            Product newProduct = new Product(id,
                    productNameTextField.getText(),
                    Double.parseDouble(productPriceTextField.getText()),
                    Integer.parseInt(productInvTextField.getText()),
                    Integer.parseInt(productMinTextField.getText()),
                    Integer.parseInt(productMaxTextField.getText()));
            for (Part part : associatedPartsList) {
                newProduct.addAssociatedPart(part);
            }
            Inventory.addProduct(newProduct);
            close(event);
        }
        else {
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Associated Parts Cost More Than Product");
            errorAlert.setContentText("Please adjust product price to be equal or above total price of associated parts.");
            errorAlert.show();
        }
    }

    /**
     * Adds the selected part to the associated parts table.
     * If the no selection is made, shows an error dialog box indicating that
     * a selection must be made
     */
    public void addAssociatedPart() {

        if (partsTableView.getSelectionModel().getSelectedItem() == null){
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("No Part Selected");
            errorAlert.setContentText("Please select a part to add");
            errorAlert.show();
        }
        else {
            associatedPartsList.add(partsTableView.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Removes a part from the associated parts table.
     * If no selection is made, shows an error dialog box indicating that
     * a selection must be made
     */
    public void removeAssociatedPart() {
        if (associatedPartsTableView.getSelectionModel().getSelectedItem() == null){
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("No Part Selected");
            errorAlert.setContentText("Please select a part to disassociate");
            errorAlert.show();
        }
        else {
            associatedPartsList.remove(associatedPartsTableView.getSelectionModel().getSelectedItem());
        }
    }

    /**
     * Allows the user to search for a part by ID or name.
     * Includes an alert if pops up if a searched part is not found.
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
            partsTableView.setItems(searchedPartsList);
        }
    }

    /**
     * Does error checking for the various text fields in the add product form and creates descriptive
     * error boxes if an error is encountered
     * @return true if an error is encountered, false if otherwise
     */
    private boolean isAnError() {

        if (productNameTextField.getText().isEmpty() || productInvTextField.getText().isEmpty() || productPriceTextField.getText().isEmpty()
                || productMaxTextField.getText().isEmpty() || productMinTextField.getText().isEmpty())
        {
            inputAlert.setTitle("Error");
            inputAlert.setHeaderText("Invalid Input");
            inputAlert.setContentText("All fields must have values");
            inputAlert.show();
            return true;

        } else if (productPriceTextField.getText().matches(".*[a-zA-Z].*") || productMaxTextField.getText().matches(".*[a-zA-Z].*")
                || productMinTextField.getText().matches(".*[a-zA-Z].*") || productInvTextField.getText().matches(".*[a-zA-Z].*")){

            inputAlert.setTitle("Error");
            inputAlert.setHeaderText("Invalid Input");
            inputAlert.setContentText("Price, Inventory, Max, and Min must be a number");
            inputAlert.show();
            return true;
        }
        else if (Integer.parseInt(productMinTextField.getText()) > Integer.parseInt(productMaxTextField.getText())) {
            inputAlert.setTitle("Error");
            inputAlert.setHeaderText("Invalid Input");
            inputAlert.setContentText("Minimum must be less than maximum.");
            inputAlert.show();
            return true;
        } else if (Integer.parseInt(productInvTextField.getText()) < Integer.parseInt(productMinTextField.getText())
                || Integer.parseInt(productInvTextField.getText()) > Integer.parseInt(productMaxTextField.getText())) {
            inputAlert.setTitle("Error");
            inputAlert.setHeaderText("Invalid Input");
            inputAlert.setContentText("Inventory must be between minimum and maximum values.");
            inputAlert.show();
            return true;
        }
        return false;
    }

    /**
     * Gets the sum of price of the parts in the associated parts table
     * @return the sum of the price of the parts in the associated parts table
     */
    private double associatedPartsPrice() {
        double sum = 0;
        for(Part part: associatedPartsList){
            sum += part.getPrice();
        }
        return sum;
    }

    /**
     * Closes the add product window and reloads the main inventory management window
     * @param event Clicking the "Cancel" button triggers the method
     * @throws IOException FXMLLoader can throw the IOException
     */
    public void close(MouseEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();

        Parent root = FXMLLoader.load(getClass().getResource("mainWindow.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Inventory Management System");
        stage.setScene(new Scene(root, 1000, 400));

        stage.show();
    }
}
