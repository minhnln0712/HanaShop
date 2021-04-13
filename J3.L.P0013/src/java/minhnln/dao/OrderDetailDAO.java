/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhnln.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import minhnln.dto.OrderDetailDTO;
import minhnln.utils.db;

/**
 *
 * @author Welcome
 */
public class OrderDetailDAO implements Serializable {

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

    public List<OrderDetailDTO> getDetailbyOrderID(String orderID) throws Exception {
        List<OrderDetailDTO> list = new ArrayList<>();
        try {
            con = db.openConnection();
            String sql = "SELECT OrderDetailID,OrderID,ProductID,Quantity,Price "
                    + "FROM tblOrderDetail "
                    + "WHERE OrderID = ? ";
            stm = con.prepareStatement(sql);
            stm.setString(1, orderID);
            rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new OrderDetailDTO(rs.getString("OrderDetailID"), orderID, rs.getString("ProductID"), rs.getInt("Quantity"), rs.getFloat("Price")));
            }
        } finally {
            CloseConnection();
            return list;
        }
    }

    public String getLastOrderDetailID() throws Exception {
        String orderDetailID = "";
        try {
            con = db.openConnection();
            String sql = "SELECT TOP 1 OrderDetailID "
                    + "FROM tblOrderDetail "
                    + "ORDER BY OrderDetailID DESC ";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                orderDetailID = rs.getString("OrderDetailID");
            }
        } finally {
            CloseConnection();
            return orderDetailID;
        }
    }

    public boolean createOrderDetail(String orderDetailID, String orderID, String productID, int quantity, float price) throws Exception {
        boolean success = false;
        try {
            con = db.openConnection();
            String sql = "INSERT INTO tblOrderDetail(OrderDetailID,OrderID,ProductID,Quantity,Price)\n"
                    + "VALUES(?,?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, orderDetailID);
            stm.setString(2, orderID);
            stm.setString(3, productID);
            stm.setInt(4, quantity);
            stm.setFloat(5, price);
            int row = stm.executeUpdate();
            if (row > 0) {
                success = true;
            }
        } finally {
            CloseConnection();
            return success;
        }
    }
}
