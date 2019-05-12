package com.abedafnan.products;

import com.abedafnan.utils.Helper;
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
    Helper helper;

    public void addProduct() {
        helper = Helper.getHelper();
        helper.moveTo(customersMainPane, "Add Product", "../products/products_add.fxml");
    }

    public void updateProduct() {
        helper = Helper.getHelper();
        helper.moveTo(customersMainPane, "Update Product", "../products/products_update.fxml");
    }

    public void deleteProduct() {
        helper = Helper.getHelper();
        helper.moveTo(customersMainPane, "Delete Product", "../products/products_delete.fxml");
    }

    public void searchProduct() {
        helper = Helper.getHelper();
        helper.moveTo(customersMainPane, "Search Products", "../products/products_search.fxml");
    }

    public void backToMainMenu() {
        helper = Helper.getHelper();
        helper.moveTo(customersMainPane, "Main Menu", "../main.fxml");
    }

}
