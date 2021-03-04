package Main.View_Controller;

import Main.Model.InHouse;
import Main.Model.Inventory;
import Main.Model.Outsourced;
import Main.Model.Part;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * The Modify Part Controller which handles the modification of parts that already
 * exist in the inventory
 */

public class ModifyPartController {

    public Label part;
    public Label sourceLabel;
    public TextField idTextField;
    public TextField partNameTextField;
    public TextField partInventoryTextField;
    public TextField partPriceTextField;
    public TextField partMaxTextField;
    public TextField partMinTextField;
    public TextField partIDTextField;
    public RadioButton inhouseButton;
    public RadioButton outsourcedButton;
    private Part tempPart;

    public Alert inputAlert = new Alert(Alert.AlertType.ERROR);

    /**
     * When the RadioButton inhouse is selected, sourceLabel is changed to "Machine ID"
     */
    public void inHouseSelection() {
        sourceLabel.setText("Machine ID");
    }

    /**
     * When the RadioButton outsourced is selected, sourceLabel is changed to "Company Name"
     */
    public void outsourceSelection() {
        sourceLabel.setText("Company Name");
    }

    /**
     * Closes the add part window and re-opens the main inventory management window
     * @param event Clicking the "Cancel" button triggers the method
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

    /**
     * Receives the selected part from the Main Window and fills in the values
     * from that part
     * @param selectedPart the part selected on the Main Window
     */
    public void receivePart(Part selectedPart) {
        tempPart = selectedPart;

        if(selectedPart instanceof InHouse) {
            inhouseButton.setSelected(true);
            idTextField.setText(String.valueOf(selectedPart.getId()));
            partNameTextField.setText(selectedPart.getName());
            partInventoryTextField.setText(String.valueOf(selectedPart.getStock()));
            partPriceTextField.setText(String.valueOf(selectedPart.getPrice()));
            partMaxTextField.setText(String.valueOf(selectedPart.getMax()));
            partMinTextField.setText(String.valueOf(selectedPart.getMin()));
            partIDTextField.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));

        }
        else if (selectedPart instanceof Outsourced) {
            outsourcedButton.setSelected(true);
            idTextField.setText(String.valueOf(selectedPart.getId()));
            partNameTextField.setText(selectedPart.getName());
            partInventoryTextField.setText(String.valueOf(selectedPart.getStock()));
            partPriceTextField.setText(String.valueOf(selectedPart.getPrice()));
            partMaxTextField.setText(String.valueOf(selectedPart.getMax()));
            partMinTextField.setText(String.valueOf(selectedPart.getMin()));
            partIDTextField.setText(String.valueOf(((Outsourced) selectedPart).getCompanyName()));
        }
    }

    /**
     * Saves the modified part, keeping the same ID number and updating the rest of
     * the values to the new values
     * @param event Clicking the "Save" button triggers the method
     * @throws IOException Used for the call to close()
     */
    public void savePart(MouseEvent event) throws IOException {
        if(!isAnError()) {
            if (inhouseButton.isSelected()) {
                for (int i = 0; i < Inventory.getAllParts().size(); i++) {
                    if (Inventory.getAllParts().get(i).getId() == tempPart.getId()) {
                        Inventory.updatePart(i, new InHouse(tempPart.getId(),
                                partNameTextField.getText(),
                                Double.parseDouble(partPriceTextField.getText()),
                                Integer.parseInt(partInventoryTextField.getText()),
                                Integer.parseInt(partMinTextField.getText()),
                                Integer.parseInt(partMaxTextField.getText()),
                                Integer.parseInt(partIDTextField.getText())));
                        break;
                    }
                }
                close(event);
            } else if (outsourcedButton.isSelected()) {
                for (int i = 0; i < Inventory.getAllParts().size(); i++) {
                    if (Inventory.getAllParts().get(i).getId() == tempPart.getId()) {
                        Inventory.updatePart(i, new Outsourced(tempPart.getId(),
                                partNameTextField.getText(),
                                Double.parseDouble(partPriceTextField.getText()),
                                Integer.parseInt(partInventoryTextField.getText()),
                                Integer.parseInt(partMinTextField.getText()),
                                Integer.parseInt(partMaxTextField.getText()),
                                partIDTextField.getText()));
                        break;
                    }
                }
                close(event);
            }
        }
    }

    /**
     * Does error checking for the various text fields in the add part form and creates descriptive
     * error boxes if an error is encountered
     * @return true if an error is encountered, false if otherwise
     */
    public boolean isAnError() {

        if (partNameTextField.getText().isEmpty() || partInventoryTextField.getText().isEmpty() || partPriceTextField.getText().isEmpty()
                || partMaxTextField.getText().isEmpty() || partMinTextField.getText().isEmpty() || partIDTextField.getText().isEmpty()) {
            inputAlert.setTitle("Error");
            inputAlert.setHeaderText("Invalid Input");
            inputAlert.setContentText("All fields must have values");
            inputAlert.show();
            return true;

        } else if (partPriceTextField.getText().matches(".*[a-zA-Z].*") || partMaxTextField.getText().matches(".*[a-zA-Z].*")
                || partMinTextField.getText().matches(".*[a-zA-Z].*") || partInventoryTextField.getText().matches(".*[a-zA-Z].*")) {

            inputAlert.setTitle("Error");
            inputAlert.setHeaderText("Invalid Input");
            inputAlert.setContentText("Price, Inventory, Max, and Min must be a number");
            inputAlert.show();
            return true;
        } else if (Integer.parseInt(partMinTextField.getText()) > Integer.parseInt(partMaxTextField.getText())) {
            inputAlert.setTitle("Error");
            inputAlert.setHeaderText("Invalid Input");
            inputAlert.setContentText("Minimum must be less than maximum.");
            inputAlert.show();
            return true;
        } else if (Integer.parseInt(partInventoryTextField.getText()) < Integer.parseInt(partMinTextField.getText())
                || Integer.parseInt(partInventoryTextField.getText()) > Integer.parseInt(partMaxTextField.getText())) {
            inputAlert.setTitle("Error");
            inputAlert.setHeaderText("Invalid Input");
            inputAlert.setContentText("Inventory must be between minimum and maximum values.");
            inputAlert.show();
            return true;
        }
        return false;
    }
}
