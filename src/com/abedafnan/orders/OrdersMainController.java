package com.abedafnan.orders;

import com.abedafnan.models.Customer;
import com.abedafnan.models.Order;
import com.abedafnan.models.Product;
import com.abedafnan.utils.DBConnection;
import com.abedafnan.utils.Helper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.stream.Stream;

public class OrdersMainController {
    Helper helper;
    DBConnection dbConnection;
    Statement statement;
    @FXML
    VBox ordersMainPane;
    @FXML
    ComboBox<Customer> customersCombo;
    @FXML
    ComboBox<Product> productsCombo;
    @FXML
    TextField dateField;
    @FXML
    TextField quantityField;
    @FXML
    TableView<Order> ordersTable;
    @FXML
    TableColumn<Order,Customer> customerCol;
    @FXML
    TableColumn<Order,Product> productCol;
    @FXML
    TableColumn<Order,Integer> quantityCol;
    @FXML
    TableColumn<Order,Double> priceCol;
    @FXML
    TableColumn<Order,Double> totalPriceCol;

    @FXML
    public void initialize() {
        customerCol.setCellValueFactory(new PropertyValueFactory<>("customer"));
        productCol.setCellValueFactory(new PropertyValueFactory<>("product"));
        quantityCol.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        totalPriceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));

        ordersTable.setItems(getOrdersFromDB());

        prepareComboBoxes();
    }

    private void prepareComboBoxes() {
        ObservableList<Product> products = getProductsFromDB();
        products.forEach(product -> productsCombo.getItems().add(product));
        ObservableList<Customer> customers = getCustomersFromDB();
        customers.forEach(customer -> customersCombo.getItems().add(customer));
    }

    public void insertOrder() {
        if (areFull()) {
            try {
                Customer customer = customersCombo.getSelectionModel().getSelectedItem();
                Product product = productsCombo.getSelectionModel().getSelectedItem();
                int customerId = customer.getId();
                int productId = product.getId();
                String date = dateField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                double price = product.getPrice();
                double totalPrice = quantity * price;

                if (checkQuantity(quantity, product))
                    insertIntoDB(customerId, productId, date, quantity, price, totalPrice);

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null,
                        "Quantity Must be an integer", "Wrong Input", JOptionPane.WARNING_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    private void insertIntoDB(int cusID, int prodID, String date, int quantity, double price, double totPrice) {
        dbConnection = DBConnection.getDbConnection();
        statement = dbConnection.createStatement();
        try {
            String query = "INSERT INTO orders VALUES('" + cusID + "', '" + prodID + "', '" + date + "', '"
                    + quantity + "', '" + totPrice + "', '" + price + "')";
            statement.execute(query);
            JOptionPane.showMessageDialog(null,
                    "Order Successfully Added", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void showOrders() {
        ordersTable.setItems(getOrdersFromDB());
    }

    public void cancel() {
        helper = Helper.getHelper();
        helper.moveTo(ordersMainPane, "Main Menu", "../main.fxml");
    }

    /**
     * @param quantity is the quantity of the product to be inserted into the database
     * @param product is the product which its quantity is to be checked
     * @return true if the quantity is correct
     */
    private boolean checkQuantity(int quantity, Product product) {
        if (quantity <= 0) {
            JOptionPane.showMessageDialog(null,
                    "Quantity can't be 0 or negative", "Wrong Input", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (quantity > product.getQuantity()) {
            JOptionPane.showMessageDialog(null,
                    "Unavailable Quantity!", "Wrong Input", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * @return true if all fields are filled
     */
    private boolean areFull() {
        if (productsCombo.getSelectionModel().getSelectedItem() == null ||
                productsCombo.getSelectionModel().getSelectedItem() == null) {
            JOptionPane.showMessageDialog(null, "Make sure you've selected " +
                    "the customer and the product", "No selection", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (quantityField.getText().isEmpty() || dateField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please fill all fields",
                    "Empty Input", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    /**
     * @return ObservableList of all orders in the orders table
     */
    private ObservableList<Order> getOrdersFromDB() {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        dbConnection = DBConnection.getDbConnection();
        statement = dbConnection.createStatement();

        try {
            String query = "SELECT * FROM orders";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                int cusID = resultSet.getInt("customer_id");
                Customer customer = getCustomer(cusID);
                int prodID = resultSet.getInt("product_id");
                Product product = getProduct(prodID);
                String date = resultSet.getString("orderDate");
                int quantity = resultSet.getInt("quantity");
                double price = resultSet.getDouble("price");
                double totalPrice = resultSet.getDouble("totalPrice");

                Order order = new Order(customer, product, date, quantity, price, totalPrice);
                orders.add(order);
            }
            return orders;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Customer getCustomer(int id) {
        Optional<Customer> result = getCustomersFromDB().stream()
                .filter(customer -> customer.getId() == id).findFirst();
        return result.get();
    }

    public Product getProduct(int id) {
        Optional<Product> result = getProductsFromDB().stream()
                .filter(product -> product.getId() == id).findFirst();
        return result.get();
    }

    /**
     * @return ObservableList of all products in the products table
     */
    private ObservableList<Product> getProductsFromDB() {
        ObservableList<Product> products = FXCollections.observableArrayList();
        dbConnection = DBConnection.getDbConnection();
        statement = dbConnection.createStatement();

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
     * @return ObservableList of all customers in the customers table
     */
    private ObservableList<Customer> getCustomersFromDB() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        dbConnection = DBConnection.getDbConnection();
        statement = dbConnection.createStatement();

        try {
            String query = "SELECT * FROM customers";
            ResultSet resultSet = statement.executeQuery(query);
            while (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getInt("mobile"),
                        resultSet.getString("address"),
                        resultSet.getString("email"),
                        resultSet.getString("gender")
                );
                customers.add(customer);
            }
            return customers;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
