package com.inventory.DAO;

import com.inventory.DTO.ProductDTO;
import com.inventory.Database.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Vector;
import javax.swing.table.DefaultTableModel;

public class ProductDAO {

    // Method to get a connection to the database
    private Connection getConnection() throws SQLException {

        return new ConnectionFactory().getConn();
    }

    // Method to insert a new product into the database
    public boolean insertProduct(ProductDTO productDTO) {
        String query = "INSERT INTO product (productID, name, category, price, stock) VALUES (?, ?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, productDTO.getProductID());
            ps.setString(2, productDTO.getName());
            ps.setString(3, productDTO.getCategory());
            ps.setDouble(4, productDTO.getPrice());
            ps.setInt(5, productDTO.getStock());

            int rowsInserted = ps.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to update an existing product
    public boolean updateProduct(ProductDTO productDTO) {
        String query = "UPDATE product SET name = ?, category = ?, price = ?, stock = ? WHERE productID = ?";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, productDTO.getName());
            ps.setString(2, productDTO.getCategory());
            ps.setDouble(3, productDTO.getPrice());
            ps.setInt(4, productDTO.getStock());
            ps.setInt(5, productDTO.getProductID());

            int rowsUpdated = ps.executeUpdate();
            return rowsUpdated > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to delete a product from the database
    public boolean deleteProduct(int productID) {
        String query = "DELETE FROM product WHERE productID = ?";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, productID);

            int rowsDeleted = ps.executeUpdate();
            return rowsDeleted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to get a product by its ID
    public ProductDTO getProductByID(int productID) {
        String query = "SELECT * FROM product WHERE productID = ?";
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setInt(1, productID);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setProductID(rs.getInt("productID"));
                productDTO.setName(rs.getString("name"));
                productDTO.setCategory(rs.getString("category"));
                productDTO.setPrice(rs.getDouble("price"));
                productDTO.setStock(rs.getInt("stock"));
//                productDTO.setWarehouseID(rs.getInt("warehouseID"));
//                productDTO.setVendorID(rs.getInt("vendorID"));
//                productDTO.setOrderID(rs.getInt("orderID"));
                return productDTO;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    
     public ResultSet getQueryResult() {
         ResultSet resultSet = null ;
        try {
            Connection connection = getConnection(); Statement stmt = connection.createStatement();
            String query = "SELECT * FROM product";
          resultSet  = stmt.executeQuery(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

//    // Method to get all products from the database
//    public List<ProductDTO> getAllProducts() {
//        String query = "SELECT * FROM products";
//        List<ProductDTO> productList = new ArrayList<>();
//        try (Connection connection = getConnection(); Statement stmt = connection.createStatement()) {
//
//            ResultSet rs = stmt.executeQuery(query);
//
//            while (rs.next()) {
//                ProductDTO productDTO = new ProductDTO();
//                productDTO.setProductID(rs.getInt("productID"));
//                productDTO.setName(rs.getString("name"));
//                productDTO.setCategory(rs.getString("category"));
//                productDTO.setPrice(rs.getDouble("price"));
//                productDTO.setStock(rs.getInt("stock"));
////                productDTO.setWarehouseID(rs.getInt("warehouseID"));
////                productDTO.setVendorID(rs.getInt("vendorID"));
////                productDTO.setOrderID(rs.getInt("orderID"));
//                productList.add(productDTO);
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return productList;
//    }

    // Method to get products by category
    public List<ProductDTO> getProductsByCategory(String category) {
        String query = "SELECT * FROM product WHERE category = ?";
        List<ProductDTO> productList = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setString(1, category);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setProductID(rs.getInt("productID"));
                productDTO.setName(rs.getString("name"));
                productDTO.setCategory(rs.getString("category"));
                productDTO.setPrice(rs.getDouble("price"));
                productDTO.setStock(rs.getInt("stock"));

                productList.add(productDTO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
    }
   public ResultSet getProductbyCode(int x){
       String Query = " select * from product where ProductID = ? ";
       try (Connection connection = getConnection(); 
               PreparedStatement ps = connection.prepareStatement(Query)) {

            ps.setInt(1, x);
           
            ResultSet rs = ps.executeQuery();
          
            return rs ;
            
       }
       catch (SQLException e) {
            e.printStackTrace();
        }
           
       return null ;
       
   }
    // Method to get products by price range
    public List<ProductDTO> getProductsByPriceRange(double minPrice, double maxPrice) {
        String query = "SELECT * FROM product WHERE price BETWEEN ? AND ?";
        List<ProductDTO> productList = new ArrayList<>();
        try (Connection connection = getConnection(); PreparedStatement ps = connection.prepareStatement(query)) {

            ps.setDouble(1, minPrice);
            ps.setDouble(2, maxPrice);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ProductDTO productDTO = new ProductDTO();
                productDTO.setProductID(rs.getInt("productID"));
                productDTO.setName(rs.getString("name"));
                productDTO.setCategory(rs.getString("category"));
                productDTO.setPrice(rs.getDouble("price"));
                productDTO.setStock(rs.getInt("stock"));

                productList.add(productDTO);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return productList;
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
