package com.abedafnan;

import com.abedafnan.utils.Helper;
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
    Helper helper;

    public void onProductsButtonPress() {
        helper = Helper.getHelper();
        helper.moveTo(mainPane, "Manage Products", "../products/products_main.fxml");
    }

    public void onCustomersButtonPress() {
        helper = Helper.getHelper();
        helper.moveTo(mainPane, "Manage Customers", "../customers/customers_main.fxml");
    }

    public void onOrdersButtonPress() {
        helper = Helper.getHelper();
        helper.moveTo(mainPane, "Manage Orders", "../orders/orders_main.fxml");
    }

    public void onLogoutButtonPress() {
        helper = Helper.getHelper();
        helper.moveTo(mainPane, "Login", "../login.fxml");
    }

}
