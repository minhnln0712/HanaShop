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
import minhnln.dto.ActionDTO;
import minhnln.utils.db;

/**
 *
 * @author Welcome
 */
public class ActionDAO implements Serializable {

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

    public boolean createAction(String actionID, String productID, String userID, String actionType, Date createDate) throws Exception {
        boolean success = false;
        try {
            con = db.openConnection();
            String sql = "INSERT INTO tblAction(ActionID,ProductID,UserID,ActionType,CreateDate) "
                    + "VALUES(?,?,?,?,?) ";
            stm = con.prepareStatement(sql);
            stm.setString(1, actionID);
            stm.setString(2, productID);
            stm.setString(3, userID);
            stm.setString(4, actionType);
            stm.setDate(5, createDate);
            int row = stm.executeUpdate();
            if (row > 0) {
                success = true;
            }
        } finally {
            CloseConnection();
            return success;
        }
    }

    public List<ActionDTO> getAllAction() throws Exception {
        List<ActionDTO> list = new ArrayList<>();
        try {
            con = db.openConnection();
            String sql = "SELECT ActionID,ProductID,UserID,ActionType,CreateDate "
                    + "FROM tblAction "
                    + "ORDER BY ActionID DESC";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new ActionDTO(rs.getString("ActionID"), rs.getString("ProductID"), rs.getString("UserID"), rs.getString("ActionType"), rs.getDate("CreateDate")));
            }
        } finally {
            CloseConnection();
            return list;
        }
    }

    public String getLastActionID() throws Exception {
        String actionID = "";
        try {
            con = db.openConnection();
            String sql = "SELECT TOP 1 ActionID "
                    + "FROM tblAction "
                    + "ORDER BY ActionID DESC ";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            if (rs.next()) {
                actionID = rs.getString("ActionID");
            }
        } finally {
            CloseConnection();
            return actionID;
        }
    }
}
