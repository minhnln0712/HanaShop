/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhnln.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import minhnln.dto.ProductDTO;
import minhnln.utils.db;

/**
 *
 * @author Welcome
 */
public class ProductDAO implements Serializable {

    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;

    public void CloseConnection() throws Exception {
        if (rs != null) {
            rs.close();
        }
        if (stm != null) {
            stm.close();
        }
        if (con != null) {
            con.close();
        }
    }

    List<ProductDTO> list;

    public int getAmountofProduct() throws Exception {
        int amount = 0;
        try {
            con = db.openConnection();
            String sql = "SELECT COUNT(ProductID) AS AMOUNTOFPRODUCT "
                    + "FROM tblProduct "
                    + "WHERE Status = 1 ";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                amount = rs.getInt("AMOUNTOFPRODUCT");
            }
        } finally {
            CloseConnection();
            return amount;
        }
    }

    public List<ProductDTO> loadAllProductWithPageNo(int pageNo, int itemPerPage) throws Exception {
        list = new ArrayList<>();
        try {
            con = db.openConnection();
            String sql = "WITH LIST AS( SELECT ROW_NUMBER() OVER(ORDER BY createDate DESC) as STT ,ProductID,Name,Image,Description,Price,CreateDate,CategoryID,Status,Quantity "
                    + "FROM tblProduct "
                    + "WHERE Status = 1 ) "
                    + "SELECT L.ProductID,L.Name,L.Image,L.Description,L.Price,L.CreateDate,L.CategoryID,L.Status,L.Quantity,C.CategoryName "
                    + "FROM LIST L JOIN tblCategory C ON L.CategoryID = C.CategoryID "
                    + "WHERE STT BETWEEN ?*?-(?-1) AND ?*? "
                    + "ORDER BY CreateDate DESC ";
            stm = con.prepareStatement(sql);
            stm.setInt(1, pageNo);
            stm.setInt(2, itemPerPage);
            stm.setInt(3, itemPerPage);
            stm.setInt(4, pageNo);
            stm.setInt(5, itemPerPage);
            rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new ProductDTO(rs.getString("ProductID"), rs.getString("Name"), rs.getString("Image"), rs.getNString("Description"), rs.getString("CategoryID"), rs.getString("CategoryName"), rs.getDate("CreateDate"), rs.getFloat("Price"), rs.getInt("Quantity"), rs.getInt("Quantity"), rs.getBoolean("Status")));
            }
        } finally {
            CloseConnection();
            return list;
        }
    }

    public List<ProductDTO> searchProductWithPageNo(int pageNo, int itemPerPage, String keyword, int minprice, int maxprice, String category, String status) throws Exception {
        list = new ArrayList<>();
        try {
            con = db.openConnection();
            String sql = "WITH LIST AS( SELECT ROW_NUMBER() OVER(ORDER BY createDate DESC) as STT ,ProductID,Name,Image,Description,Price,CreateDate,CategoryID,Status,Quantity "
                    + "FROM tblProduct "
                    + "WHERE Name LIKE ? AND (Price BETWEEN ? AND ? ) AND CategoryID LIKE ? AND Status = ? ) "
                    + "SELECT L.ProductID,L.Name,L.Image,L.Description,L.Price,L.CreateDate,L.CategoryID,L.Status,L.Quantity,C.CategoryName "
                    + "FROM LIST L JOIN tblCategory C ON L.CategoryID = C.CategoryID "
                    + "WHERE STT BETWEEN ?*?-(?-1) AND ?*? "
                    + "ORDER BY CreateDate DESC ";
            stm = con.prepareStatement(sql);
            stm.setString(1, "%" + keyword + "%");
            stm.setInt(2, minprice);
            stm.setInt(3, maxprice);
            stm.setString(4, category);
            stm.setString(5, status);
            stm.setInt(6, pageNo);
            stm.setInt(7, itemPerPage);
            stm.setInt(8, itemPerPage);
            stm.setInt(9, pageNo);
            stm.setInt(10, itemPerPage);
            rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new ProductDTO(rs.getString("ProductID"), rs.getString("Name"), rs.getString("Image"), rs.getNString("Description"), rs.getString("CategoryID"), rs.getString("CategoryName"), rs.getDate("CreateDate"), rs.getFloat("Price"), rs.getInt("Quantity"), rs.getInt("Quantity"), rs.getBoolean("Status")));
            }
        } finally {
            CloseConnection();
            return list;
        }
    }

    public int getAmountofProductAfterSearch(String keyword, int minprice, int maxprice, String category, String status) throws Exception {
        int amount = 0;
        try {
            con = db.openConnection();
            String sql = "SELECT COUNT(ProductID) AS AMOUNTOFPRODUCT "
                    + "FROM tblProduct "
                    + "WHERE Name LIKE ? AND (Price BETWEEN ? AND ? ) AND CategoryID LIKE ? AND Status = ? ";
            stm = con.prepareStatement(sql);
            stm.setString(1, "%" + keyword + "%");
            stm.setInt(2, minprice);
            stm.setInt(3, maxprice);
            stm.setString(4, category);
            stm.setString(5, status);
            rs = stm.executeQuery();
            if (rs.next()) {
                amount = rs.getInt("AMOUNTOFPRODUCT");
            }
        } finally {
            CloseConnection();
            return amount;
        }
    }

    public boolean deleteProduct(String productID) throws Exception {
        boolean success = false;
        try {
            con = db.openConnection();
            String sql = "UPDATE tblProduct "
                    + "SET Status = 0 "
                    + "WHERE ProductID = ? ";
            stm = con.prepareStatement(sql);
            stm.setString(1, productID);
            int row = stm.executeUpdate();
            if (row > 0) {
                success = true;
            }
        } finally {
            CloseConnection();
            return success;
        }
    }

    public boolean updateProduct(String productID, String name, String image, String description, float price, String createDate, String categoryID, String status, int quantity) throws Exception {
        boolean success = false;
        try {
            con = db.openConnection();
            String sql = "UPDATE tblProduct "
                    + "SET Name = ? "
                    + ",Image = ? "
                    + ",Description = ? "
                    + ",Price = ? "
                    + ",CreateDate = ? "
                    + ",CategoryID = ? "
                    + ",Status = ? "
                    + ",Quantity = ? "
                    + "WHERE ProductID = ? ";
            stm = con.prepareStatement(sql);
            stm.setString(1, name);
            stm.setString(2, image);
            stm.setString(3, description);
            stm.setFloat(4, price);
            stm.setString(5, createDate);
            stm.setString(6, categoryID);
            stm.setString(7, status);
            stm.setInt(8, quantity);
            stm.setString(9, productID);
            int row = stm.executeUpdate();
            if (row > 0) {
                success = true;
            }
        } finally {
            CloseConnection();
            return success;
        }
    }

    public boolean createProduct(String productID, String name, String image, String description, float price, String createDate, String categoryID, String status, int quantity) throws Exception {
        boolean success = false;
        try {
            con = db.openConnection();
            String sql = "INSERT INTO tblProduct(ProductID,Name,Image,Description,Price,CreateDate,CategoryID,Status,Quantity) "
                    + "VALUES(?,?,?,?,?,?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, productID);
            stm.setString(2, name);
            stm.setString(3, image);
            stm.setString(4, description);
            stm.setFloat(5, price);
            stm.setString(6, createDate);
            stm.setString(7, categoryID);
            stm.setString(8, status);
            stm.setInt(9, quantity);
            int row = stm.executeUpdate();
            if (row > 0) {
                success = true;
            }
        } finally {
            CloseConnection();
            return success;
        }
    }

    public String getlastProductID() throws Exception {
        String productID = "";
        try {
            con = db.openConnection();
            String sql = "SELECT TOP 1 ProductID "
                    + "FROM tblProduct "
                    + "ORDER BY ProductID DESC ";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                productID = rs.getString("ProductID");
            }
        } finally {
            CloseConnection();
            return productID;
        }
    }

    public boolean setRemainQuantity(String productID, int newquantity) throws Exception {
        boolean success = false;
        try {
            con = db.openConnection();
            String sql = "UPDATE tblProduct "
                    + "SET Quantity = ? "
                    + "WHERE ProductID = ? ";
            stm = con.prepareStatement(sql);
            stm.setInt(1, newquantity);
            stm.setString(2, productID);
            int row = stm.executeUpdate();
            if (row > 0) {
                success = true;
            }
        } finally {
            CloseConnection();
            return success;
        }
    }

    public List<ProductDTO> getAllProduct() throws Exception {
        list = new ArrayList<>();
        try {
            con = db.openConnection();
            String sql = "SELECT ProductID,Name,Image,Description,Price,CreateDate,CategoryID,Status,Quantity "
                    + "FROM tblProduct ";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new ProductDTO(rs.getString("ProductID"), rs.getString("Name"), rs.getString("Image"), rs.getNString("Description"), rs.getString("CategoryID"), "", rs.getDate("CreateDate"), rs.getFloat("Price"), rs.getInt("Quantity"), rs.getInt("Quantity"), rs.getBoolean("Status")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            CloseConnection();
            return list;
        }
    }

    public List<ProductDTO> getProductbyID(String productID) throws Exception {
        list = new ArrayList<>();
        try {
            con = db.openConnection();
            String sql = "SELECT ProductID,Name,Image,Description,Price,CreateDate,CategoryID,Status,Quantity\n"
                    + "FROM tblProduct "
                    + "WHERE ProductID = ? ";
            stm = con.prepareStatement(sql);
            stm.setString(1, productID);
            rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new ProductDTO(rs.getString("ProductID"), rs.getString("Name"), rs.getString("Image"), rs.getNString("Description"), rs.getString("CategoryID"), "", rs.getDate("CreateDate"), rs.getFloat("Price"), rs.getInt("Quantity"), rs.getInt("Quantity"), rs.getBoolean("Status")));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            CloseConnection();
            return list;
        }
    }

    public List<ProductDTO> suggestProductByFirstProductID(String productID) throws Exception {
        list = new ArrayList<>();
        try {
            con = db.openConnection();
            String sql = "Select TOP 2  P.ProductID, count(OD.OrderID), P.Name, P.Image, P.Price\n"
                    + "From tblOrderDetail OD Inner Join tblProduct P On OD.productID = P.productID \n"
                    + "Where OD.OrderID in (Select OD.OrderID \n"
                    + "					From tblOrder O Inner Join tblOrderDetail OD On O.OrderID = OD.OrderID\n"
                    + "					Where OD.ProductID = ? ) And P.Quantity > 0 And P.Status = 1 AND P.ProductID != ?\n"
                    + "Group By P.ProductID, P.Name, P.Image, P.Price\n"
                    + "Order By count(OD.OrderID) DESC";
            stm = con.prepareStatement(sql);
            stm.setString(1, productID);
            stm.setString(2, productID);
            rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new ProductDTO(rs.getString("ProductID"), rs.getString("Name"), rs.getString("Image"), "", "", "", null, rs.getFloat("Price"), 1, 1, true));
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        } finally {
            CloseConnection();
            return list;
        }
    }

    public List<ProductDTO> suggestProductofAnotherUser(String userID) throws Exception {
        list = new ArrayList<>();
        try {
            con = db.openConnection();
            String sql = "SELECT TOP 2 P.ProductID, count(P.ProductID) as Times ,P.Name, P.Image, P.Price\n"
                    + "FROM tblOrder O \n"
                    + "JOIN tblOrderDetail OD ON O.OrderID = OD.OrderID \n"
                    + "JOIN tblProduct P ON OD.productID = P.productID\n"
                    + "WHERE O.UserID != ? AND P.Quantity > 0 \n"
                    + "GROUP BY P.ProductID, P.Name, P.Image, P.Price\n"
                    + "ORDER BY count(P.ProductID) DESC";
            stm = con.prepareStatement(sql);
            stm.setString(1, userID);
            rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new ProductDTO(rs.getString("ProductID"), rs.getString("Name"), rs.getString("Image"), "", "", "", null, rs.getFloat("Price"), 1, rs.getInt("Times"), true));
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            CloseConnection();
            return list;
        }
    }

    public boolean checkStringInput(String input) {
        for (int i = 0; i < input.length(); i++) {
            if (input.substring(i, i + 1).matches("[a-zA-Z]*")) {
            } else if (input.substring(i, i + 1).matches("\\s")) {
            } else {
                return true;
            }
        }
        return false;
    }

    public boolean getExistProductName(String productName) throws Exception {
        boolean exist = false;
        try {
            con = db.openConnection();
            String sql = "SELECT Name "
                    + "FROM tblProduct "
                    + "WHERE Name = ? ";
            stm = con.prepareStatement(sql);
            stm.setString(1, productName);
            rs = stm.executeQuery();
            if (rs.next()) {
                exist = true;
            }
        } finally {
            CloseConnection();
            return exist;
        }
    }
}
