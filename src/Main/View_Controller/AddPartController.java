package Main.View_Controller;

import Main.Model.InHouse;
import Main.Model.Inventory;
import Main.Model.Outsourced;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * The Add Part Controller which handles the addition of a part to the inventory
 */

public class AddPartController {


    public Label part;
    public Label sourceLabel;
    public TextField partNameTextField;
    public TextField partInvTextField;
    public TextField partPriceTextField;
    public TextField partMaxTextField;
    public TextField partMinTextField;
    public TextField partIDTextField;
    public RadioButton inhouse;
    public RadioButton outsourced;

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
     * Creates a new part (either InHouse or OutSourced depending on the button selected) and
     * saves the new part to the inventory
     * @param event Clicking the "Save" button triggers the method
     */
    public void saveNewPart(MouseEvent event) {
        boolean isPartCreated = false;

        if(!isAnError()) {
            if (inhouse.isSelected()) {
                int id = Inventory.generatePartID();
                Inventory.addPart(new InHouse(id,
                        partNameTextField.getText(),
                        Double.parseDouble(partPriceTextField.getText()),
                        Integer.parseInt(partInvTextField.getText()),
                        Integer.parseInt(partMinTextField.getText()),
                        Integer.parseInt(partMaxTextField.getText()),
                        Integer.parseInt(partIDTextField.getText())));
                isPartCreated = true;
            } else if (outsourced.isSelected()) {
                int id = Inventory.generatePartID();
                Inventory.addPart(new Outsourced(id,
                        partNameTextField.getText(),
                        Double.parseDouble(partPriceTextField.getText()),
                        Integer.parseInt(partInvTextField.getText()),
                        Integer.parseInt(partMinTextField.getText()),
                        Integer.parseInt(partMaxTextField.getText()),
                        partIDTextField.getText()));
                isPartCreated = true;
            }
            if (isPartCreated) {
                close(event);
            }
        }
    }

    /**
     * Closes the add part window and re-opens the main inventory management window
     * @param event Clicking the "Cancel" button triggers the method
     */
    public void close(MouseEvent event) {
        Node node = (Node) event.getSource();
        Stage thisStage = (Stage) node.getScene().getWindow();
        thisStage.close();
    }

    /**
     * Does error checking for the various text fields in the add part form and creates descriptive
     * error boxes if an error is encountered
     * @return true if an error is encountered, false if otherwise
     */
    public boolean isAnError() {

            if (partNameTextField.getText().isEmpty() || partInvTextField.getText().isEmpty() || partPriceTextField.getText().isEmpty()
                    || partMaxTextField.getText().isEmpty() || partMinTextField.getText().isEmpty() || partIDTextField.getText().isEmpty())
            {
                inputAlert.setTitle("Error");
                inputAlert.setHeaderText("Invalid Input");
                inputAlert.setContentText("All fields must have values");
                inputAlert.show();
                return true;

            } else if (partPriceTextField.getText().matches(".*[a-zA-Z].*") || partMaxTextField.getText().matches(".*[a-zA-Z].*")
                        || partMinTextField.getText().matches(".*[a-zA-Z].*") || partInvTextField.getText().matches(".*[a-zA-Z].*")){

                inputAlert.setTitle("Error");
                inputAlert.setHeaderText("Invalid Input");
                inputAlert.setContentText("Price, Inventory, Max, and Min must be a number");
                inputAlert.show();
                return true;
            }
            else if (Integer.parseInt(partMinTextField.getText()) > Integer.parseInt(partMaxTextField.getText())) {
                inputAlert.setTitle("Error");
                inputAlert.setHeaderText("Invalid Input");
                inputAlert.setContentText("Minimum must be less than maximum.");
                inputAlert.show();
                return true;
            } else if (Integer.parseInt(partInvTextField.getText()) < Integer.parseInt(partMinTextField.getText())
                    || Integer.parseInt(partInvTextField.getText()) > Integer.parseInt(partMaxTextField.getText())) {
                inputAlert.setTitle("Error");
                inputAlert.setHeaderText("Invalid Input");
                inputAlert.setContentText("Inventory must be between minimum and maximum values.");
                inputAlert.show();
                return true;
            }
            return false;
    }
}
