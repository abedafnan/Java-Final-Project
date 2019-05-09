package com.abedafnan.products;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class ProductsMainController {
    @FXML
    VBox customersMainPane;
    @FXML
    Button addProductButton;
    @FXML
    Button updateProductsButton;
    @FXML
    Button deleteProductsButton;
    @FXML
    Button searchProductsButton;
    @FXML
    Button menuProductsButton;

    public void addProduct() {

    }

    public void updateProduct() {

    }

    public void deleteProduct() {

    }

    public void searchProduct() {

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
