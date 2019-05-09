package com.abedafnan.orders;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class OrdersMainController {
    @FXML
    VBox ordersMainPane;
    @FXML
    ComboBox customersCombo;
    @FXML
    ComboBox productsCombo;
    @FXML
    TextField dateField;
    @FXML
    ComboBox quantityField;

    public void insertOrder() {

    }

    public void showOrders() {

    }

    public void cancel() {
        Stage stage = (Stage) ordersMainPane.getScene().getWindow();
        stage.setTitle("Main Menu");
        stage.setScene(loadScene("../main.fxml"));
    }

    /**
     * @param resource of the fxml file to be loaded
     * @return the new scene to be displayed
     */
    public Scene loadScene(String resource) {
        Scene scene = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(resource));
            Parent parent = loader.load();
            scene = new Scene(parent);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return scene;
    }
}
