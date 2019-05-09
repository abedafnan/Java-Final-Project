package com.abedafnan.customers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class CustomersMainController {
    @FXML
    VBox customersMainPane;
    @FXML
    Button addCustomersButton;
    @FXML
    Button updateCustomersButton;
    @FXML
    Button deleteCustomersButton;
    @FXML
    Button menuCustomersButton;

    public void addCustomer() {

    }

    public void updateCustomer() {

    }

    public void deleteCustomer() {

    }

    public void backToMainMenu() {
        Stage stage = (Stage) customersMainPane.getScene().getWindow();
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
