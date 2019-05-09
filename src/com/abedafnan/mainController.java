package com.abedafnan;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class mainController {
    @FXML
    VBox mainPane;
    @FXML
    Button productsButton;
    @FXML
    Button ordersButton;
    @FXML
    Button customersButton;
    @FXML
    Button logoutButton;

    public void onProductsButtonPress() {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setTitle("Manage Products");
        stage.setScene(loadScene("products/products_main.fxml"));
    }

    public void onCustomersButtonPress() {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setTitle("Manage Customers");
        stage.setScene(loadScene("customers/customers_main.fxml"));
    }

    public void onOrdersButtonPress() {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setTitle("Manage Orders");
        stage.setScene(loadScene("orders/orders_main.fxml"));
    }

    public void onLogoutButtonPress() {
        Stage stage = (Stage) mainPane.getScene().getWindow();
        stage.setTitle("Login");
        stage.setScene(loadScene("login.fxml"));
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
