/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import entity.Customer;
import entity.Product;

/**
 *
 * @author Lenovo
 */
public class DAO extends DBConnect {

    public DAO() {
        super();
    }    
    
    //getData vector
    public Vector<Product> getAll() {
        String sql = "Select * from Product where status=1";
        Vector<Product> list = new Vector<Product>();
        try {
            //xu ly thread safe
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("product_id"),
                        rs.getInt("model_year"),
                        rs.getString("product_name"),
                        rs.getString("brand_name"),
                        rs.getString("category_name"),
                        rs.getDouble("list_price")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
    public Vector<Product> getAllByBranch(String branch) {
        String sql = "Select * from Product where status=1";
        Vector<Product> list = new Vector<Product>();
        System.out.println("hereee");
        try {
            //xu ly thread safe
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("product_id"),
                        rs.getInt("model_year"),
                        rs.getString("product_name"),
                        rs.getString("brand_name"),
                        rs.getString("category_name"),
                        rs.getDouble("list_price")
                );
                System.out.println(p.getBrand_name() + " " + branch);
                System.out.println(p.getBrand_name().compareToIgnoreCase(branch));
                if (p.getBrand_name().compareToIgnoreCase(branch) == 0)
                    list.add(p);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
    //getData vector
    public Vector<String> getAllBranch() {
        String sql = "Select brand_name from Product group by brand_name";
        Vector<String> list = new Vector<String>();
        try {
            //xu ly thread safe
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("brand_name"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public Product getProductById(int id) {
        String sql = "Select * from Product where product_id = ?";
        Product p = null;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                p = new Product(
                        rs.getInt("product_id"),
                        rs.getInt("model_year"),
                        rs.getString("product_name"),
                        rs.getString("brand_name"),
                        rs.getString("category_name"),
                        rs.getDouble("list_price")
                );
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return p;
    }

    //create customer
    public void addProduct(Product pro) {
        String sql = "INSERT INTO [dbo].[products]\n"
                + "           ([product_id]\n"
                + "           ,[product_name]\n"
                + "           ,[model_year]\n"
                + "           ,[list_price]\n"
                + "           ,[brand_name]\n"
                + "           ,[category_name])\n"
                + "     VALUES(?, ?, ?, ?, ?, ?)";
        //Statement
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, pro.getProduct_id());
            st.setString(2, pro.getProduct_name());
            st.setInt(3, pro.getModel_year());
            st.setDouble(4, pro.getList_price());
            st.setString(5, pro.getBrand_name());
            st.setString(6, pro.getCategory_name());
            st.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    //update product
    public void updateProduct(Product p) throws SQLException {
        String sql = "UPDATE [dbo].[products]\n"
                + "   SET [product_name] = ?"
                + "      ,[model_year] = ?"
                + "      ,[list_price] = ? "
                + "      ,[brand_name] = ?"
                + "      ,[category_name] = ?"
                + " WHERE [product_id] = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, p.getProduct_name());
            st.setInt(2, p.getModel_year());
            st.setDouble(3, p.getList_price());
            st.setString(4, p.getBrand_name());
            st.setString(5, p.getCategory_name());
            st.setInt(6, p.getProduct_id());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    public int deleteProduct(int id) {
        String sql = "DELETE FROM products WHERE product_id = ?";
        int n = 0;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            n = st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return n;
    }

    
    public Vector<String> getInfo(String name) {
            String sql = "Select distinct "+ name +" from products";
            Vector<String> list = new Vector<>();
            try {
                PreparedStatement st = connection.prepareStatement(sql);
                ResultSet rs = st.executeQuery();
                while (rs.next()) {
                 list.add(rs.getString(name));
                }
            } catch (Exception e) {
                System.out.println(e);
            }
            return list;
    }

    public Vector<Product> search(String name) {
        String sql = "SELECT * FROM products where 1=1";
        if(name != null) {
         sql+=" AND product_name LIKE '%"+name+"%'";
        }
        Vector<Product> list = new Vector<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("product_id"),
                        rs.getInt("model_year"),
                        rs.getString("product_name"),
                        rs.getString("brand_name"),
                        rs.getString("category_name"),
                        rs.getDouble("list_price")
                );
                list.add(p);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
    
    public Vector<Product> getProductByPrice(String minPrice, String maxPrice) {
        String sql = "SELECT * FROM products where 1=1";
        if(minPrice!=null) {
             sql+=" and list_price >= "+minPrice;
            }
        if(maxPrice!=null) {
             sql+=" and list_price <= "+maxPrice;
        }
        Vector<Product> list = new Vector<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("product_id"),
                        rs.getInt("model_year"),
                        rs.getString("product_name"),
                        rs.getString("brand_name"),
                        rs.getString("category_name"),
                        rs.getDouble("list_price")
                );
                list.add(p);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return list;
    }
}
