/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package minhnln.DAO;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import minhnln.DTO.RegistrationDTO;
import minhnln.utils.db;

/**
 *
 * @author Welcome
 */
public class RegistrationDAO implements Serializable {

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

    List<RegistrationDTO> list;

    public RegistrationDTO login(String userID, String password) throws Exception {
        RegistrationDTO dto = null;
        try {
            con = db.openConnection();
            String sql = "SELECT * "
                    + "FROM tblRegistration "
                    + "WHERE UserID = ? AND Password = ? ";
            stm = con.prepareStatement(sql);
            stm.setString(1, userID);
            stm.setString(2, password);
            rs = stm.executeQuery();
            if (rs.next()) {
                dto = new RegistrationDTO(userID, password, rs.getNString("Fullname"), rs.getString("Role"));
            }
        } finally {
            CloseConnection();
            return dto;
        }
    }

    public String getPasswordByUserID(String userID) throws Exception {
        String password = "";
        try {
            con = db.openConnection();
            String sql = "SELECT Password "
                    + "FROM tblRegistration "
                    + "WHERE UserID = ? ";
            stm = con.prepareStatement(sql);
            stm.setString(1, userID);
            rs = stm.executeQuery();
            if (rs.next()) {
                password = rs.getString("Password");
            }
        } finally {
            CloseConnection();
            return password;
        }
    }

    public boolean checkUserByUserID(String userID) throws Exception {
        boolean exist = false;
        try {
            con = db.openConnection();
            String sql = "SELECT * "
                    + "FROM tblRegistration "
                    + "WHERE UserID = ? ";
            stm = con.prepareStatement(sql);
            stm.setString(1, userID);
            rs = stm.executeQuery();
            if (rs.next()) {
                exist = true;
            }
        } finally {
            CloseConnection();
            return exist;
        }
    }

    public boolean signupforGoogle(String userID, String fullname) throws Exception {
        boolean success = false;
        try {
            con = db.openConnection();
            String sql = "INSERT INTO tblRegistration(UserID,Password,Fullname,Role) "
                    + "VALUES(?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, userID);
            stm.setString(2, "");
            stm.setString(3, fullname);
            stm.setString(4, "USER");
            int row = stm.executeUpdate();
            if (row > 0) {
                success = true;
            }
        } finally {
            CloseConnection();
            return success;
        }
    }

    public boolean signup(String userID, String password, String fullname) throws Exception {
        boolean success = false;
        try {
            con = db.openConnection();
            String sql = "INSERT INTO tblRegistration(UserID,Password,Fullname,Role) "
                    + "VALUES(?,?,?,?)";
            stm = con.prepareStatement(sql);
            stm.setString(1, userID);
            stm.setString(2, password);
            stm.setString(3, fullname);
            stm.setString(4, "USER");
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
