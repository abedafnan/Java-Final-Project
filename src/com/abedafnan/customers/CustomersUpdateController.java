package com.abedafnan.customers;

import com.abedafnan.models.Customer;
import com.abedafnan.utils.DBConnection;
import com.abedafnan.utils.Helper;
import javafx.fxml.FXML;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class CustomersUpdateController {
    DBConnection dbConnection;
    Statement statement;
    ArrayList<Customer> customers;
    @FXML
    VBox customersUpdatePane;
    @FXML
    TextField searchField;
    @FXML
    TextField idField;
    @FXML
    TextField firstNameField;
    @FXML
    TextField lastNameField;
    @FXML
    TextField mobileField;
    @FXML
    TextField addressField;
    @FXML
    TextField emailField;
    @FXML
    RadioButton maleRadio;
    @FXML
    RadioButton femaleRadio;
    @FXML
    ToggleGroup radioGroup;

    public void search() {
        if (searchField.getText().equals("")) {
            JOptionPane.showMessageDialog(null,
                    "You must enter an ID to search for", "Empty Input", JOptionPane.WARNING_MESSAGE);
            return;
        }

        dbConnection = DBConnection.getDbConnection();
        statement = dbConnection.createStatement();
        String searchID = searchField.getText().trim();
        try {
            String query = "SELECT * FROM customers WHERE id = '" + searchID + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                Customer customer = new Customer(
                        resultSet.getInt("id"),
                        resultSet.getString("firstName"),
                        resultSet.getString("lastName"),
                        resultSet.getInt("mobile"),
                        resultSet.getString("address"),
                        resultSet.getString("email"),
                        resultSet.getString("gender")
                );
                fillFields(customer);

            } else {
                JOptionPane.showMessageDialog(null,
                        "Customer doesn't exist", "Customer Not Found", JOptionPane.WARNING_MESSAGE);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillFields(Customer customer) {
        idField.setText(String.valueOf(customer.getId()));
        mobileField.setText(String.valueOf(customer.getMobile()));
        firstNameField.setText(customer.getFirstName());
        lastNameField.setText(customer.getLastName());
        addressField.setText(customer.getAddress());
        emailField.setText(customer.getEmail());

        if (customer.getGender().equals("male")) {
            maleRadio.setSelected(true);
        } else if (customer.getGender().equals("female")) {
            femaleRadio.setSelected(true);
        }
    }

    public void updateCustomer() {
        if (searchField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "You haven't selected a customer", "Empty Search", JOptionPane.WARNING_MESSAGE);
            return;
        }

        dbConnection = DBConnection.getDbConnection();
        try {
            if (areFull()) {
                int id = Integer.parseInt(idField.getText());
                int mobile = Integer.parseInt(mobileField.getText());
                String fname = firstNameField.getText();
                String lname = lastNameField.getText();
                String address = addressField.getText();
                String email = emailField.getText();
                String gender = getGender();

                updateTheDB(id, fname, lname, mobile, email, address, gender);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Must enter numeric values in ID and Mobile fields", "Wrong Input", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void updateTheDB(int id, String fn, String ln, int mob, String email, String addrs, String gender) {
        dbConnection = DBConnection.getDbConnection();
        statement = dbConnection.createStatement();
        try {
            /*
            UPDATE customers SET id = 123, firstName = "aaa", lastName = "bbb", mobile = 12345, email = "any@email.com",
address = "anyAddress", gender = "male" WHERE id = 123;
             */
            String query = "UPDATE customers SET id='" + id + "', firstName='" + fn + "', lastName='" + ln + "', mobile='"
                    + mob + "', email='" + email + "', address='" + addrs + "',gender='" + gender
                    + "' WHERE id='" + searchField.getText() + "'";
            statement.execute(query);
            JOptionPane.showMessageDialog(null,
                    "Successfully Updated", "Success", JOptionPane.INFORMATION_MESSAGE);
            cancel();

        } catch (SQLException ex) {
            System.out.println("Error when updating customer");
        }
    }

    private String getGender() {
        if (radioGroup.getSelectedToggle().equals(maleRadio)) {
            return "male";
        } else if (radioGroup.getSelectedToggle().equals(femaleRadio)) {
            return "female";
        }
        return "";
    }

    public void cancel() {
        Helper helper = Helper.getHelper();
        helper.moveTo(customersUpdatePane, "Manage Customers", "../customers/customers_main.fxml");
    }

    /**
     * @return true if all fields are full
     */
    private boolean areFull() {
        if (idField.getText().isEmpty() || firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty()
                || mobileField.getText().isEmpty() || addressField.getText().isEmpty()
                || emailField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Please Enter all fields", "Empty Fields", JOptionPane.WARNING_MESSAGE);
            return false;
        } else if (!maleRadio.isSelected() && !femaleRadio.isSelected()) {
            JOptionPane.showMessageDialog(null,
                    "Gender not Selected", "Gender Selection", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

}
