package Main;

import Main.Model.InHouse;
import Main.Model.Inventory;
import Main.Model.Outsourced;
import Main.Model.Product;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/** The Main class of the Inventory Management Program
 *
 * FUTURE ENHANCEMENT
 * A future enhancement that could be added is the ability to restore previously deleted
 * parts or products. For instance, when a part or product was deleted, those objects could
 * be stored in additional arrays that could be accessed if the user wanted to restore that part
 * or product. There could be a limit to how many could be saved in order to conserve space.
 *
 */

public class Main extends Application {

    /**
     * The starting point for the JavaFX application
     * @param primaryStage the primary stage of the application
     * @throws Exception Needed for the FXMLLoader variable
     */
    @Override
    public void start(Stage primaryStage) throws Exception{
        loadTestData();
        Parent root = FXMLLoader.load(getClass().getResource("View_Controller/mainWindow.fxml"));
        primaryStage.setTitle("Inventory Management System");
        primaryStage.setScene(new Scene(root, 1000, 400));
        primaryStage.show();
    }

    /**
     * JavaDocs location is zipped with the main project
     * Launches the application. This is the first method that runs in the application.
     * @param args String array of args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Test data to be loaded for the Inventory Management Program
     */
    public static void loadTestData() {
        Inventory.addPart(new InHouse(Inventory.generatePartID(),"Chains", 2.25d, 5,1,10,789));
        Inventory.addPart(new Outsourced(Inventory.generatePartID(),"Tires", 25.0d, 7,1,10,"BikeTown Inc."));
        Inventory.addPart(new InHouse(Inventory.generatePartID(),"Spokes", 2.0d, 1,1,10,790));
        Inventory.addPart(new Outsourced(Inventory.generatePartID(),"Frames", 75.0d, 5,1,15,"BikeTown Inc."));

        Inventory.addProduct(new Product(Inventory.generateProductID(),"Bikes",199.99d, 20,0,40));
        Inventory.addProduct(new Product(Inventory.generateProductID(),"Unicycles",89.99d, 5,0,20));

    }
}
