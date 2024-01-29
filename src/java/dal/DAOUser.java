/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dal;

import entity.Order;
import entity.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;

/**
 *
 * @author Lenovo
 */
public class DAOUser extends DBConnect {

    //check have exist
    public User checkUser(String user_email, String user_pass) {
        String sql = "select * from [User] where user_email = ? and user_pass = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, user_email);
            st.setString(2, user_pass);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                System.out.println(rs.getString(2));
                System.out.println(rs.getInt(5));
                //khoi tao user tu gia tri tra ve tu sql
                User user = new User(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4), 
                        rs.getInt(5));
                return user;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public User checkAcc(String user_email) {
        String sql = "select * from [User] where user_email = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, user_email);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                User a = new User(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getInt(5));
                return a;
            }
        } catch (Exception e) {
        }
        return null;
    }

    public void signup(String user_email, String user_pass) {
        try {
            String sql = "insert into [dbo].[User] values(?,?,?,?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, "");
            st.setString(2, user_email);
            st.setString(3, user_pass);
            st.setInt(4, 0);
            st.executeUpdate();
        } catch (Exception e) {
        };
    }

}
