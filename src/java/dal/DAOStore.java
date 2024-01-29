/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Order;
import entity.Store;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

/**
 *
 * @author Lenovo
 */
public class DAOStore extends DBConnect {
    
    public Vector<Store> getAllStore() {
     String sql = "Select * from stores";
     Vector<Store> list = new Vector<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
               Store store = new Store(
                        rs.getInt("store_id"), 
                        rs.getString("store_name"),
                        rs.getString("phone"), 
                        rs.getString("email"), 
                        rs.getString("street"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("zip_code"));
               list.add(store);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
}