package com.abedafnan.customers;

import com.abedafnan.models.Product;
import com.abedafnan.utils.DBConnection;
import com.abedafnan.utils.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductsUpdateController {
    @FXML
    VBox productsUpdatePane;
    @FXML
    TableView<Product> productsTable;
    @FXML
    TableColumn<Product,Integer> nameCol;
    @FXML
    TableColumn<Product,String> catCol;
    @FXML
    TableColumn<Product,String> quantCol;
    @FXML
    TableColumn<Product,Integer> priceCol;
    @FXML
    TableColumn<Product,String> descCol;
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
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        catCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        productsTable.setItems(getFromDB());

        categoryCombo.getItems().removeAll(categoryCombo.getItems());
        categoryCombo.getItems().addAll("Electronics", "Furniture", "Clothing", "Food", "Books", "General");
        categoryCombo.getSelectionModel().select("General");
    }

    public void confirmUpdate() {
        Product selected = productsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            JOptionPane.showMessageDialog(null,
                    "You haven't selected a product", "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (areFull()) {
                String name = nameField.getText();
                String category = categoryCombo.getSelectionModel().getSelectedItem();
                double price = Double.parseDouble(priceField.getText());
                int quantity = Integer.parseInt(quantityField.getText());
                String desc = descArea.getText();


                updateTheDB(name, category, quantity, price, desc, selected);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Must enter numeric values in Price and Quantity fields", "Wrong Input", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateTheDB(String name, String category, int quantity, double price, String desc, Product selected) {
        DBConnection dbConnection = DBConnection.getDbConnection();
        Statement statement = dbConnection.createStatement();
        try {
            String query = "UPDATE products SET name='" + name + "', category='" + category + "', quantity='" + quantity
                    + "', price='" + price + "', description='" + desc + "' WHERE id='" + selected.getId() + "'";
            statement.execute(query);
            JOptionPane.showMessageDialog(null,
                    "Successfully Updated", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            System.out.println("Error when updating product");
        }
    }


    public void updateProduct() {
        Product product = productsTable.getSelectionModel().getSelectedItem();

        nameField.setText(product.getName());
        categoryCombo.getSelectionModel().select(product.getCategory());
        priceField.setText(String.valueOf(product.getPrice()));
        quantityField.setText(String.valueOf(product.getQuantity()));
        descArea.setText(product.getDescription());
    }

    public void refresh() {
        productsTable.setItems(getFromDB());
    }

    public void cancel() {
        Helper helper = Helper.getHelper();
        helper.moveTo(productsUpdatePane, "Manage Products", "../products/products_main.fxml");
    }

    private ObservableList<Product> getFromDB() {
        ObservableList<Product> products = FXCollections.observableArrayList();
        DBConnection dbConnection = DBConnection.getDbConnection();
        Statement statement = dbConnection.createStatement();

        try {
            String query = "SELECT * FROM products";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Product customer = new Product(
                        resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getString("category"),
                        resultSet.getInt("quantity"),
                        resultSet.getDouble("price"),
                        resultSet.getString("description")
                );
                products.add(customer);
            }
            return products;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
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
