/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.inventory.DAO;

import com.inventory.DTO.PaymentDTO;
import com.inventory.Database.ConnectionFactory;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Vector;

/**
 * Data Access Object for Payment operations
 */
public class PaymentDAO  {
    private final Connection conn = new ConnectionFactory().getConn();

    // Insert a new payment record
   public boolean insertPayment(PaymentDTO paymentDTO) {
    String query = "INSERT INTO payment (PaymentID, OrderID, PaymentMode, TransactionDate) VALUES (?, ?, ?, ?)";

    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, paymentDTO.getPaymentID()); // user entered
        pstmt.setInt(2, paymentDTO.getOrderID());   // user entered
        pstmt.setString(3, paymentDTO.getPaymentMode());
        pstmt.setDate(4, new java.sql.Date(paymentDTO.getTransactionDate().getTime()));

        int rows = pstmt.executeUpdate();
        if (rows == 0) {
            System.err.println("No rows affected - insert failed");
            return false;
        }
        return true;
    } catch (SQLException ex) {
        System.err.println("SQL Error inserting payment: " + ex.getMessage());
        ex.printStackTrace();
        return false;
    }
}


   // For update operation
    public boolean updatePayment(PaymentDTO paymentDTO) {
        String query = "UPDATE payment SET OrderID=?, PaymentMode=?, TransactionDate=? WHERE PaymentID=?";
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setInt(1, paymentDTO.getOrderID());
            pstmt.setString(2, paymentDTO.getPaymentMode());
            pstmt.setDate(3, new java.sql.Date(paymentDTO.getTransactionDate().getTime()));
            pstmt.setInt(4, paymentDTO.getPaymentID());
            return pstmt.executeUpdate() > 0;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }

    

    // Delete a payment record
    public boolean deletePayment(int paymentID) {
    String query = "DELETE FROM payment WHERE PaymentID=?"; // Changed 'payment' to 'payments'
    try (PreparedStatement pstmt = conn.prepareStatement(query)) {
        pstmt.setInt(1, paymentID);
        return pstmt.executeUpdate() > 0;
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
    }
}

    // Get all payments for display in table
    public ResultSet getAllPayments() throws SQLException {
        String query = "SELECT * FROM payment";
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(query);
    }

    // Search payments based on search text
    public ResultSet searchPayments(String searchText) throws SQLException {
        String query = "SELECT * FROM payment WHERE PaymentID LIKE ? OR OrderID LIKE ? OR PaymentMode LIKE ?";
        PreparedStatement pstmt = conn.prepareStatement(query);
        String searchParam = "%" + searchText + "%";
        pstmt.setString(1, searchParam);
        pstmt.setString(2, searchParam);
        pstmt.setString(3, searchParam);
        return pstmt.executeQuery();
    }

    // Convert ResultSet to TableModel for JTable
    public DefaultTableModel buildTableModel(ResultSet rs) throws SQLException {
        ResultSetMetaData metaData = rs.getMetaData();

        // Names of columns
        Vector<String> columnNames = new Vector<>();
        int columnCount = metaData.getColumnCount();
        for (int i = 1; i <= columnCount; i++) {
            columnNames.add(metaData.getColumnName(i));
        }

        // Data of the table
        Vector<Vector<Object>> data = new Vector<>();
        while (rs.next()) {
            Vector<Object> vector = new Vector<>();
            for (int i = 1; i <= columnCount; i++) {
                vector.add(rs.getObject(i));
            }
            data.add(vector);
        }

        return new DefaultTableModel(data, columnNames);
    }

    // Get payment by ID
   
    
    public ResultSet getPaymentById(int paymentId) throws SQLException {
    String query = "SELECT OrderID, PaymentMode, TransactionDate FROM payment WHERE PaymentID = ?";
    PreparedStatement pstmt = conn.prepareStatement(query);
    pstmt.setInt(1, paymentId);
    return pstmt.executeQuery();

}
    
}