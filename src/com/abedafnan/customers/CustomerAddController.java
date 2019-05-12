package com.abedafnan.customers;

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

public class CustomerAddController {
    DBConnection dbConnection;
    Statement statement;
    @FXML
    VBox customersAddPane;
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

    public void addCustomer() {
        dbConnection = DBConnection.getDbConnection();
        try {
            if (areFull() && notDuplicatePrimary("id", idField.getText())) {
                int id = Integer.parseInt(idField.getText());
                int mobile = Integer.parseInt(mobileField.getText());
                String fname = firstNameField.getText();
                String lname = lastNameField.getText();
                String address = addressField.getText();
                String email = emailField.getText();
                String gender = getGender();

                insertIntoDB(id, fname, lname, mobile, email, address, gender);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null,
                    "Must enter numeric values in ID and Mobile fields", "Wrong Input", JOptionPane.WARNING_MESSAGE);
        }

    }

    private void insertIntoDB(int id, String fn, String ln, int mob, String email, String addrs, String gender) {
        dbConnection = DBConnection.getDbConnection();
        statement = dbConnection.createStatement();
        try {
            String query = "INSERT INTO customers VALUES('" + id + "','" + fn + "','" + ln + "','"
                    + mob + "','" + email + "','" + addrs + "','" + gender + "')";
            statement.execute(query);
            JOptionPane.showMessageDialog(null,
                    "Successfully inserted a new customer", "Success", JOptionPane.INFORMATION_MESSAGE);

            Helper helper = Helper.getHelper();
            helper.writeIntoLog("Inserted a new customer with the id " + id);

        } catch (SQLException ex) {
            System.out.println("Error when inserting customer");
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

    public void clear() {
        idField.setText("");
        firstNameField.setText("");
        lastNameField.setText("");
        mobileField.setText("");
        emailField.setText("");
        addressField.setText("");
        maleRadio.setSelected(false);
        femaleRadio.setSelected(false);
    }

    public void cancel() {
        Helper helper = Helper.getHelper();
        helper.moveTo(customersAddPane, "Manage Customers", "../customers/customers_main.fxml");
    }

    /**
     *
     * @param primaryColumn the column to search in for the value
     * @param value is the value to search for
     * @return true if the value isn't duplicated
     */
    public boolean notDuplicatePrimary(String primaryColumn, String value) {
        dbConnection = DBConnection.getDbConnection();
        statement = dbConnection.createStatement();
        try {
            String query = "SELECT * FROM customers WHERE " + primaryColumn + "='" + value + "'";
            ResultSet resultSet = statement.executeQuery(query);
            if (resultSet.next()) {
                JOptionPane.showMessageDialog(null,
                        "ID already exists!", "Duplicate ID", JOptionPane.WARNING_MESSAGE);
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}
