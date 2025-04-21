/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.DAO;

import com.inventory.DTO.VendorDTO;
import com.inventory.Database.ConnectionFactory;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Locale;
import java.util.Vector;

/**
 *
 * @author asjad
 */

// Data Access Object for Suppliers
public class VendorDAO {

     Connection conn = null;
    Statement statement = null;
    PreparedStatement prepStatement = null;
    ResultSet resultSet = null;

    public VendorDAO() {
        try {
            conn = new ConnectionFactory().getConn();
            statement = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Methods to add new vendor
    public void addVendorDAO(VendorDTO vendorDTO) {
        try {
            String query = "SELECT * FROM vendor WHERE name='"
                    + vendorDTO.getName()
                    + "' AND location='"
                    + vendorDTO.getLocation()
                    + "' AND contact='"
                    + vendorDTO.getContact()
                    + "'";
            resultSet = statement.executeQuery(query);
            if (resultSet.next())
                JOptionPane.showMessageDialog(null, "This vendor already exists.");
            else
                addFunction(vendorDTO);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addFunction(VendorDTO vendorDTO) {
        try {
            String query = "INSERT INTO vendor (VendorID ,name, location, contact, rating) VALUES(? ,?, ?, ?, ?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setInt(1, vendorDTO.getVendorID());
            prepStatement.setString(2, vendorDTO.getName());
            prepStatement.setString(3, vendorDTO.getLocation());
            prepStatement.setString(4, vendorDTO.getContact());
            prepStatement.setFloat(5, vendorDTO.getRating());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "New vendor has been added successfully.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to edit existing vendor details
    public void editVendorrDAO(VendorDTO vendorDTO) {
        try {
            String query = "UPDATE vendor SET name=?, location=?, contact=?, rating=? WHERE vendorID=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, vendorDTO.getName());
            prepStatement.setString(2, vendorDTO.getLocation());
            prepStatement.setString(3, vendorDTO.getContact());
            prepStatement.setFloat(4, vendorDTO.getRating());
            prepStatement.setInt(5, vendorDTO.getVendorID());
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Vendor details have been updated.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete existing vendor
    public void deleteSupplierDAO(int vendorID) {
        try {
            String query = "DELETE FROM vendor WHERE vendorID=?";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setInt(1, vendorID);
            prepStatement.executeUpdate();
            JOptionPane.showMessageDialog(null, "Vendor has been removed.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Vendor data set retrieval method
    public ResultSet getQueryResult() {
        try {
            String query = "SELECT * FROM vendor ";
            resultSet = statement.executeQuery(query);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Search method
    public ResultSet getSearchResult(String searchText) {
        try {
            String query = "SELECT vendorID, name, location, contact, rating FROM vendor" +
                    "WHERE vendorID LIKE '%" + searchText + "%' OR location LIKE '%" + searchText + "%' " +
                    "OR name LIKE '%" + searchText + "%' OR contact LIKE '%" + searchText + "%'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    // Method to set/update vendor combo box
    public DefaultComboBoxModel<String> setComboItems(ResultSet resultSet) throws SQLException {
        Vector<String> vendorNames = new Vector<>();
        while (resultSet.next()) {
            vendorNames.add(resultSet.getString("name"));
        }
        return new DefaultComboBoxModel<>(vendorNames);
    }

    // Method to display retrieved data set in tabular form
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