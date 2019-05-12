package com.abedafnan.products;

import com.abedafnan.models.Product;
import com.abedafnan.utils.DBConnection;
import com.abedafnan.utils.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ProductsSearchController {
    @FXML
    VBox productsSearchPane;
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
    ComboBox<String> catCombo;

    @FXML
    public void initialize() {
        catCombo.getItems().removeAll(catCombo.getItems());
        catCombo.getItems().addAll("Electronics", "Furniture", "Clothing", "Food", "Books", "General");
        catCombo.getSelectionModel().select("General");
    }

    public void search() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        catCol.setCellValueFactory(new PropertyValueFactory<>("category"));
        quantCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        descCol.setCellValueFactory(new PropertyValueFactory<>("description"));

        productsTable.setItems(getFromDB());
    }

    public void  cancel() {
        Helper helper = Helper.getHelper();
        helper.moveTo(productsSearchPane, "Manage Products", "../products/products_main.fxml");
    }

    private ObservableList<Product> getFromDB() {
        ObservableList<Product> products = FXCollections.observableArrayList();
        DBConnection dbConnection = DBConnection.getDbConnection();
        Statement statement = dbConnection.createStatement();

        try {
            String selection = catCombo.getSelectionModel().getSelectedItem();
            String query = "SELECT * FROM products WHERE category='" + selection + "'";
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
            helper.writeIntoLog("Getting products of the category '" + selection + "'");
            return products;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
