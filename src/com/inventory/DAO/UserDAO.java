/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.inventory.DAO;

import com.inventory.DTO.UserDTO;
import com.inventory.Database.ConnectionFactory;
import com.inventory.UI.UsersPage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import java.util.Locale;
import java.util.Vector;

/**
 *
 * @author asjad
 */

// Data Access Object class for Users

public class UserDAO {
    Connection conn = null;
    PreparedStatement prepStatement = null;
    Statement statement = null;
    ResultSet resultSet = null;

    // Constructor method
    public UserDAO() {
        try {
            conn = new ConnectionFactory().getConn();
            statement = conn.createStatement();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    // Methods to add new user
    public void addUserDAO(UserDTO userDTO) {
        try {
            String query = "SELECT * FROM Login WHERE username='"
                    +userDTO.getUsername()
                    +"'";
            resultSet = statement.executeQuery(query);
            if(resultSet.next())
                JOptionPane.showMessageDialog(null, "Thank You");
            else
                addFunction(userDTO);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    public void addFunction(UserDTO userDTO) {
        try {
            String username = null;
            String password = null;
  
            String resQuery = "SELECT * FROM Login";
            resultSet = statement.executeQuery(resQuery);

            if(!resultSet.next()){
                username = "root";
                password = "root";
            }
//            else {
//                String resQuery2 = "SELECT * FROM users ORDER BY id DESC";
//                resultSet = statement.executeQuery(resQuery2);
//
//                if(resultSet.next()){
//                    oldUsername = resultSet.getString("username");
//                    Integer uCode = Integer.parseInt(oldUsername.substring(4));
//                    uCode++;
//                    username = "user" + uCode;
//                    password = "user" + uCode;
//                }
//            }

            String query = "INSERT INTO Login (username , password , role) " +
                    "VALUES(?,?,?)";
            prepStatement = conn.prepareStatement(query);
            prepStatement.setString(1, userDTO.getUsername());
            prepStatement.setString(2, userDTO.getPassword());
            prepStatement.setString(3, userDTO.getRole());
            prepStatement.executeUpdate();

            JOptionPane.showMessageDialog(null, "New member added.");
            

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }
//
//    // Method to edit existing user
////    public void editUserDAO(UserDTO userDTO) {
////
////        try {
////            String query = "UPDATE Login SET password=?,role=? WHERE username=?";
////            prepStatement = conn.prepareStatement(query);
////            prepStatement.setString(1, userDTO.getPassword());
////            prepStatement.setString(2, userDTO.getRole());
////            prepStatement.setString(3, userDTO.getUsername());
////            prepStatement.executeUpdate();
////            JOptionPane.showMessageDialog(null, "Updated Successfully.");
////
////        } catch (SQLException throwables) {
////            throwables.printStackTrace();
//     }
//    }

    // Method to delete existing user
    public boolean deleteUserDAO(String username) {
    try {
        String query = "DELETE FROM Login WHERE username=?";
        try (PreparedStatement prepStatement = conn.prepareStatement(query)) {
            prepStatement.setString(1, username);
            int rowsAffected = prepStatement.executeUpdate();
            
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "User Deleted.");
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "User not found.", "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
    } catch (SQLException throwables) {
        JOptionPane.showMessageDialog(null, "Error deleting user: " + throwables.getMessage(), 
                                     "Database Error", JOptionPane.ERROR_MESSAGE);
        throwables.printStackTrace();
        return false;
    }
}
    // Method to retrieve data set to display in table
    public ResultSet getQueryResult() {
        try {
            String query = "SELECT * FROM Login";
            resultSet = statement.executeQuery(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return resultSet;
    }

    public ResultSet getUserDAO(String username) {
        try {
            String query = "SELECT * FROM Login WHERE username='" +username+ "'";
            resultSet = statement.executeQuery(query);
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return resultSet;
    }
    


    // Method to display data set in tabular form
    public DefaultTableModel buildTableModel(ResultSet resultSet) throws SQLException {
        ResultSetMetaData metaData = resultSet.getMetaData();
        Vector<String> columnNames = new Vector<String>();
        int colCount = metaData.getColumnCount();

        for (int col=1; col <= colCount; col++){
            columnNames.add(metaData.getColumnName(col).toUpperCase(Locale.ROOT));
        }

        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
        while (resultSet.next()) {
            Vector<Object> vector = new Vector<Object>();
            for (int col=1; col<=colCount; col++) {
                vector.add(resultSet.getObject(col));
            }
            data.add(vector);
        }
        return new DefaultTableModel(data, columnNames);
    }

}
