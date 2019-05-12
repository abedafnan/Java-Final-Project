package com.abedafnan.products;

import com.abedafnan.utils.DBConnection;
import com.abedafnan.utils.Helper;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductsAddController {
    DBConnection dbConnection;
    Statement statement;
    @FXML
    VBox productsAddPane;
    @FXML
    TextField nameField;
    @FXML
    TextField quantityField;
    @FXML
    TextField priceField;
    @FXML
    ComboBox<String> categoryCombo;
    @FXML
    TextArea descArea;

    @FXML
    public void initialize() {
        categoryCombo.getItems().removeAll(categoryCombo.getItems());
        categoryCombo.getItems().addAll("Electronics", "Furniture", "Clothing", "Food", "Books", "General");
        categoryCombo.getSelectionModel().select("General");
    }

    public void addProduct() {
        dbConnection = DBConnection.getDbConnection();
        try {
            if (areFull()) {
                String name = nameField.getText();
                String category = categoryCombo.getSelectionModel().getSelectedItem();
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                String desc = descArea.getText();

                insertIntoDB(name, category, price, quantity, desc);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Must enter numeric values in Price and Quantity", "Wrong Input", JOptionPane.WARNING_MESSAGE);
        }

    }

    public void cancel() {
        Helper helper = Helper.getHelper();
        helper.moveTo(productsAddPane, "Manage Products", "../products/products_main.fxml");
    }

    private void insertIntoDB(String name, String category, double price, int quantity, String desc) {
        dbConnection = DBConnection.getDbConnection();
        statement = dbConnection.createStatement();
        try {
            String query = "INSERT INTO products(name, category, quantity, price, description)" +
                    " VALUES('" + name + "','" + category + "','" + quantity + "','"
                    + price + "','" + desc + "')";
            statement.execute(query);
            JOptionPane.showMessageDialog(null,
                    "Successfully inserted a new product", "Success", JOptionPane.INFORMATION_MESSAGE);

            Helper helper = Helper.getHelper();
            helper.writeIntoLog("Inserted a new product customer with the name " + name);

        } catch (SQLException ex) {
            System.out.println("Error when inserting product");
            ex.printStackTrace();
        }
    }

    /**
     * @return true if all fields are full
     */
    private boolean areFull() {
        if (nameField.getText().isEmpty() || priceField.getText().isEmpty() || quantityField.getText().isEmpty()
                || descArea.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Please Enter all fields", "Empty Fields", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

}
