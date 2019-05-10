package com.abedafnan.customers;

import com.abedafnan.utils.Helper;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class CustomersMainController {
    Helper helper;
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
        helper = Helper.getHelper();
        helper.moveTo(customersMainPane, "Add Customer", "../customers/customer_add.fxml");
    }

    public void updateCustomer() {
        helper = Helper.getHelper();
        helper.moveTo(customersMainPane, "Update Customer", "../customers/customer_update.fxml");
    }

    public void deleteCustomer() {
        helper = Helper.getHelper();
        helper.moveTo(customersMainPane, "Delete Customer", "../customers/customer_delete.fxml");
    }

    public void backToMainMenu() {
        helper = Helper.getHelper();
        helper.moveTo(customersMainPane, "Main Menu", "../main.fxml");
    }

}
