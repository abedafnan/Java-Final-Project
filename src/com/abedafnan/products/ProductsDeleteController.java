package com.abedafnan.products;

import com.abedafnan.models.Product;
import com.abedafnan.utils.DBConnection;
import com.abedafnan.utils.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductsDeleteController {
    @FXML
    VBox productsDeletePane;
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
    public void initialize() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        catCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        productsTable.setItems(getFromDB());
    }

    public void deleteProduct() {
        if (!confirm()) return;

        Product product = productsTable.getSelectionModel().getSelectedItem();
        if (product == null) {
            JOptionPane.showMessageDialog(null,
                    "select a product to delete", "Product Not Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }

        DBConnection connection = DBConnection.getDbConnection();
        Statement statement = connection.createStatement();
        try {
            int id = product.getId();
            String query = "DELETE FROM products WHERE id='" + id + "'";
            statement.execute(query);
            JOptionPane.showMessageDialog(null,
                    "Customer Successfully Deleted", "Success", JOptionPane.INFORMATION_MESSAGE);

            Helper helper = Helper.getHelper();
            helper.writeIntoLog("Deleted the customer with id " + id);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return true if the deletion was confirmed
     */
    public boolean confirm() {
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this product?",
                "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return JOptionPane.YES_OPTION == choice;
    }

    public void refresh() {
        productsTable.setItems(getFromDB());
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

            Helper helper = Helper.getHelper();
            helper.writeIntoLog("Retrieved all products from the database");
            return products;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void cancel() {
        Helper helper = Helper.getHelper();
        helper.moveTo(productsDeletePane, "Manage Products", "../products/products_main.fxml");
    }
}
