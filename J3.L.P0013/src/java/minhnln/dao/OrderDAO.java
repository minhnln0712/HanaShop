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
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import minhnln.dto.OrderDTO;
import minhnln.utils.db;

/**
 *
 * @author Welcome
 */
public class OrderDAO implements Serializable {

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

    public boolean createOrder(String orderID, String userID, String createDate, float totalPrice, String status) throws Exception {
        boolean success = false;
        try {
            con = db.openConnection();
            String sql = "INSERT INTO tblOrder(OrderID,UserID,CreateDate,TotalPrice,Status,CreateDatee)\n"
                    + "VALUES(?,?,?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, orderID);
            stm.setString(2, userID);
            stm.setString(3, createDate);
            stm.setFloat(4, totalPrice);
            stm.setString(5, status);
            stm.setString(6, createDate);
            int row = stm.executeUpdate();
            if (row > 0) {
                success = true;
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
        } finally {
            CloseConnection();
            return success;
        }
    }

    public String getLastOrderID() throws Exception {
        String orderID = "";
        try {
            con = db.openConnection();
            String sql = "SELECT TOP 1 OrderID "
                    + "FROM tblOrder "
                    + "ORDER BY OrderID DESC ";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                orderID = rs.getString("OrderID");
            }
        } finally {
            CloseConnection();
            return orderID;
        }
    }

    public List<OrderDTO> getOrderOfUser(String userID) throws Exception {
        List<OrderDTO> list = new ArrayList<>();
        try {
            con = db.openConnection();
            String sql = "SELECT OrderID,UserID,CreateDate,TotalPrice,Status "
                    + "FROM tblOrder "
                    + "WHERE UserID = ? ";
            stm = con.prepareStatement(sql);
            stm.setString(1, userID);
            rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new OrderDTO(rs.getString("OrderID"), rs.getString("UserID"), rs.getString("Status"), rs.getFloat("TotalPrice"), rs.getString("CreateDate")));
            }
        } finally {
            CloseConnection();
            return list;
        }
    }

    public List<OrderDTO> getAllofOrder() throws Exception {
        List<OrderDTO> list = new ArrayList<>();
        try {
            con = db.openConnection();
            String sql = "SELECT OrderID,UserID,CreateDate,TotalPrice,Status "
                    + "FROM tblOrder ";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new OrderDTO(rs.getString("OrderID"), rs.getString("UserID"), rs.getString("Status"), rs.getFloat("TotalPrice"), rs.getString("CreateDate")));
            }
        } finally {
            CloseConnection();
            return list;
        }
    }

    public List<OrderDTO> searchOrder(String keyword, String startDate, String endDate) throws Exception {
        List<OrderDTO> list = new ArrayList<>();
        try {
            con = db.openConnection();
            String sql = "SELECT OrderID,UserID,CreateDate,TotalPrice,Status,CreateDatee "
                    + "FROM tblOrder "
                    + "WHERE UserID LIKE ? AND CreateDatee BETWEEN ? AND ? ";
            stm = con.prepareStatement(sql);
            stm.setString(1, "%" + keyword + "%");
            stm.setString(2, startDate);
            stm.setString(3, endDate);
            rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new OrderDTO(rs.getString("OrderID"), rs.getString("UserID"), rs.getString("Status"), rs.getFloat("TotalPrice"), rs.getString("CreateDate")));
            }
        } finally {
            CloseConnection();
            return list;
        }
    }
}
