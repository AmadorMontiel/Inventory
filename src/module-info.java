module Inventory {

    requires javafx.fxml;
    requires javafx.controls;

    opens Main;
    opens Main.Model;
    opens Main.View_Controller;

}