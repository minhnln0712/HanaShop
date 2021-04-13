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
import minhnln.dto.CategoryDTO;
import minhnln.utils.db;

/**
 *
 * @author Welcome
 */
public class CategoryDAO implements Serializable {

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

    public List<CategoryDTO> getAllCategory() throws Exception {
        List<CategoryDTO> list = new ArrayList<>();
        try {
            con = db.openConnection();
            String sql = "SELECT CategoryID,CategoryName\n"
                    + "FROM tblCategory "
                    + "ORDER BY CategoryID ASC ";
            stm = con.prepareStatement(sql);
            rs = stm.executeQuery();
            while (rs.next()) {
                list.add(new CategoryDTO(rs.getString("CategoryID"), rs.getString("CategoryName")));
            }
        } finally {
            CloseConnection();
            return list;
        }
    }
    
}
