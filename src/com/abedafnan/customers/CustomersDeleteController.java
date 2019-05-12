package com.abedafnan.customers;

import com.abedafnan.models.Customer;
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


public class CustomersDeleteController {
    @FXML
    VBox customersDeletePane;
    @FXML
    TableView<Customer> customersTable;
    @FXML
    TableColumn<Customer,Integer> idCol;
    @FXML
    TableColumn<Customer,String> fnameCol;
    @FXML
    TableColumn<Customer,String> lnameCol;
    @FXML
    TableColumn<Customer,String> emailCol;
    @FXML
    TableColumn<Customer,Integer> mobileCol;
    @FXML
    TableColumn<Customer,String> addressCol;
    @FXML
    TableColumn<Customer,String> genderCol;

    @FXML
    public void initialize() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        fnameCol.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lnameCol.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        mobileCol.setCellValueFactory(new PropertyValueFactory<>("Mobile"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("Gender"));

        customersTable.setItems(getCustomersFromDB());
    }

    public void deleteCustomer() {
        if (!confirm()) return;

        Customer customer = customersTable.getSelectionModel().getSelectedItem();
        if (customer == null) {
            JOptionPane.showMessageDialog(null,
                    "select a customer to delete", "Customer Not Selected", JOptionPane.WARNING_MESSAGE);
            return;
        }
        DBConnection connection = DBConnection.getDbConnection();
        Statement statement = connection.createStatement();
        try {
            int id = customer.getId();
            String query = "DELETE FROM customers WHERE id='" + id + "'";
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
        int choice = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete this customer?",
                "Confirmation", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return JOptionPane.YES_OPTION == choice;
    }

    public void refresh() {
        customersTable.setItems(getCustomersFromDB());
    }

    private ObservableList<Customer> getCustomersFromDB() {
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        DBConnection dbConnection = DBConnection.getDbConnection();
        Statement statement = dbConnection.createStatement();

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

    public void cancel() {
        Helper helper = Helper.getHelper();
        helper.moveTo(customersDeletePane, "Manage Customers", "../customers/customers_main.fxml");
    }
}
