/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.DAO;

import com.inventory.DTO.CustomerDTO;
import com.inventory.Database.ConnectionFactory;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Locale;
import java.util.Vector;

/**
 * Data Access Object for Customers
 */
public class CustomerDAO {
    Connection conn = null;
    PreparedStatement prepStatement= null;
    Statement statement = null;
    ResultSet resultSet = null;

    public CustomerDAO() {
        try {
            conn = new ConnectionFactory().getConn();
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to add new customer
    public void addCustomerDAO(CustomerDTO customerDTO) {
        try {
            String query = "SELECT * FROM Customer WHERE name='"
                    +customerDTO.getName()
                    + "' AND address='"
                    +customerDTO.getAddress()
                    + "' AND phone='"
                    +customerDTO.getPhone()
                    + "' AND email='"
                    +customerDTO.getEmail()
                    + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                JOptionPane.showMessageDialog(null, "Customer already exists.");
            else
                addFunction(customerDTO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFunction(CustomerDTO customerDTO) {
        try {
            String query = "INSERT INTO Customer VALUES(?,?,?,?,?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setInt(1, customerDTO.getCustomerID());
            prepStatement.setString(2, customerDTO.getName());
            prepStatement.setString(3, customerDTO.getAddress());
            prepStatement.setString(4, customerDTO.getPhone());
            prepStatement.setString(5, customerDTO.getEmail());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "New customer has been added.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to edit existing customer details
    public void editCustomerDAO(CustomerDTO customerDTO) {
        try {
            String query = "UPDATE Customer SET name=?, address=?, phone=?, email=? WHERE customerid=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, customerDTO.getName());
            prepStatement.setString(2, customerDTO.getAddress());
            prepStatement.setString(3, customerDTO.getPhone());
            prepStatement.setString(4, customerDTO.getEmail());
            prepStatement.setInt(5, customerDTO.getCustomerID());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Customer details have been updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete existing customer
    public void deleteCustomerDAO(int customerID) {
     try {
    conn.setAutoCommit(false);
    
    // Delete payments first (reference orders)
    String deletePayments = "DELETE FROM payment WHERE OrderID IN " +
                          "(SELECT OrderID FROM orders WHERE CustomerID = ?)";
    
    // Delete shipments next (reference orders)
    String deleteShipments = "DELETE FROM ShipmentTracking WHERE OrderID IN " +
                           "(SELECT OrderID FROM orders WHERE CustomerID = ?)";
    
    // Then delete orders
    String deleteOrders = "DELETE FROM orders WHERE CustomerID = ?";
    
    // Finally delete customer
    String deleteCustomer = "DELETE FROM Customer WHERE CustomerID = ?";
    
    try (PreparedStatement stmtPay = conn.prepareStatement(deletePayments);
         PreparedStatement stmtShip = conn.prepareStatement(deleteShipments);
         PreparedStatement stmtOrders = conn.prepareStatement(deleteOrders);
         PreparedStatement stmtCust = conn.prepareStatement(deleteCustomer)) {
        
        // Set parameters for all queries
        stmtPay.setInt(1, customerID);
        stmtShip.setInt(1, customerID);
        stmtOrders.setInt(1, customerID);
        stmtCust.setInt(1, customerID);
        
        // Execute in proper order
        stmtPay.executeUpdate();
        stmtShip.executeUpdate();
        stmtOrders.executeUpdate();
        int custDeleted = stmtCust.executeUpdate();
        
        if (custDeleted > 0) {
            conn.commit();
            JOptionPane.showMessageDialog(null,
                "Customer and all related data deleted successfully",
                "Deletion Complete",
                JOptionPane.INFORMATION_MESSAGE);
        } else {
            conn.rollback();
            JOptionPane.showMessageDialog(null,
                "No customer found with ID: " + customerID,
                "Not Found",
                JOptionPane.WARNING_MESSAGE);
        }
    }
} catch (SQLException e) {
    try {
        conn.rollback();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    JOptionPane.showMessageDialog(null,
        "Error during deletion: " + e.getMessage(),
        "Database Error",
        JOptionPane.ERROR_MESSAGE);
    e.printStackTrace();
} finally {
    try {
        conn.setAutoCommit(true);
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
    }

    // Method to retrieve data set to be displayed
    public ResultSet getQueryResult() {
        try {
            String query = "SELECT * FROM Customer";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Method to retrieve search data
    public ResultSet getCustomerSearch(String text) {
        try {
            String query = "SELECT customerid, name, address, phone, email FROM Customer " +
                    "WHERE name LIKE '%" + text + "%' OR address LIKE '%" + text + "%' " +
                    "OR phone LIKE '%" + text + "%' OR email LIKE '%" + text + "%'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getCustByID(int customerID) {
        try {
            String query = "SELECT * FROM customer WHERE customerid=" + customerID;
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Method to display data set in tabular form
    public DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<>();
        int colCount = metaData.getColumnCount();

        for (int col = 1; col <= colCount; col++) {
            columnNames.add(metaData.getColumnName(col).toUpperCase(Locale.ROOT));
        }

        Vector<Vector<Object>> data = new Vector<>();
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<>();
            for (int col = 1; col <= colCount; col++) {
                vector.add(resultSet.getObject(col));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }
}
