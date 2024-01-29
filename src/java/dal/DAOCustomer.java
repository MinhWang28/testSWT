/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Customer;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class DAOCustomer extends DBConnect {
    
    public Customer getCustomerByEmail(String email) {
        String sql = "Select * from Customer where email = ?";
        Customer cus = null;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, email);            
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                cus = new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("street"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("zip_code"));
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return cus;
    }
    public Customer getCustomerById(int id) {
        String sql = "Select * from customers where customer_id = ?";
        Customer cus = null;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                cus = new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("street"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("zip_code"));
                return cus;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return cus;
    }
    
    public Vector<Customer> getAllCustomer() {
        String sql = "Select * from Customer";
        Vector<Customer> list = new Vector<Customer>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Customer c = new Customer(
                        rs.getInt("customer_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("phone"),
                        rs.getString("email"),
                        rs.getString("street"),
                        rs.getString("city"),
                        rs.getString("state"),
                        rs.getString("zip_code")
                );
                list.add(c);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
    public Vector<String> getListProperties(String name) {
            String sql = "Select distinct "+ name +" from customers";
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
    
    public Vector<Customer> addCustomers(Customer cus) {
        String sql = "INSERT INTO [dbo].[customers]\n"
                + "           ([customer_id]\n"
                + "           ,[first_name]\n"
                + "           ,[last_name]\n"
                + "           ,[phone]\n"
                + "           ,[email]\n"
                + "           ,[street]\n"
                + "           ,[city]\n"
                + "           ,[state]\n"
                + "           ,[zip_code])\n"
                + "     VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        Vector<Customer> list = new Vector<Customer>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, cus.getCustomer_id());
            st.setString(2, cus.getFirst_name());
            st.setString(3, cus.getLast_name());
            st.setString(4, cus.getPhone());
            st.setString(5, cus.getEmail());
            st.setString(6, cus.getStreet());
            st.setString(7, cus.getCity());
            st.setString(8, cus.getState());
            st.setString(9, cus.getZip_code());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }
    
    public int deleteCustomers(int id) {
        String sql = "DELETE FROM [dbo].[customers]\n" +
                     "WHERE customer_id = ?";
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

    public void updateCustomers(Customer cus) {
        String sql = "UPDATE [dbo].[customers]\n"
                + "   SET [first_name] = ?\n"
                + "      ,[last_name] = ?\n"
                + "      ,[phone] = ?\n"
                + "      ,[email] = ?\n"
                + "      ,[street] = ?\n"
                + "      ,[city] = ?\n"
                + "      ,[state] = ?\n"
                + "      ,[zip_code] = ?\n"
                + " WHERE [customer_id] = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, cus.getFirst_name());
            st.setString(2, cus.getLast_name());
            st.setString(3, cus.getPhone());
            st.setString(4, cus.getEmail());
            st.setString(5, cus.getStreet());
            st.setString(6, cus.getCity());
            st.setString(7, cus.getState());
            st.setString(8, cus.getZip_code());
            st.setInt(9, cus.getCustomer_id());
            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
     public String getZipCodeByCity(String city) {
         String sql = "Select top 1 zip_code from customers\n"
                 + "where city = ?";
         Vector<Customer> list = new Vector<Customer>();
         String zip_code = null;
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, city);
            ResultSet rs = st.executeQuery();
            if (rs.next()) {                
                zip_code = rs.getString("zip_code");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return zip_code;
    }
     
     
    public String getTableData(int customerID) {
        String sql = "Select P.product_name, OI.quantity, OI.list_price, OI.discount, (OI.quantity * OI.list_price * (1-OI.discount)) AS SubTotal from customers CU\n"
                + "join orders O on O.customer_id = CU.customer_id\n"
                + "join order_items OI on OI.order_id = O.order_id\n"
                + "join products P on P.product_id = OI.product_id\n"
                + "where CU.customer_id = ?";
        
        String results = "<table border=\"1px\" style='position: relative'>\n"
                + "<tr>\n"
                + " <th>No</th>\n"
                + " <th>Product</th>\n"
                + " <th>Quantity</th>\n"
                + " <th>List Price</th>\n"
                + " <th>Discount</th>\n"
                + " <th>Sub Total</th>\n"
                + "</tr>\n";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, customerID);
            ResultSet rs = st.executeQuery();
            int index = 1;
            double totalBill = 0;
            while (rs.next()) {
                results += "<tr>"
                        + " <td>" + index + "</td>\n"
                        + " <td>" + rs.getString("product_name") + "</td>\n"
                        + " <td>" + rs.getInt("quantity") + "</td>\n"
                        + " <td>" + rs.getDouble("list_price") + "</td>\n"
                        + " <td>" + rs.getDouble("discount") + "</td>\n"
                        + " <td>" + rs.getDouble("SubTotal") + "</td>\n"
                        + "</tr>";
                index++;
                totalBill += rs.getDouble("SubTotal");
            }
            String totalBillFormatted = String.format("%.4f", totalBill);
            results += "<tr><td style='position: absolute; right: 0; border: 0'>Total bill: " + totalBillFormatted + "</td></tr>";
            results += "</table>";
        } catch (Exception e) {
            System.out.println(e);
        }
        return results;
    }

    public void signup(Customer c) {
        try {
            String sql = "insert into [dbo].[Customer](customer_id,email) values (?, ?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, String.valueOf(c.getCustomer_id()));
            st.setString(2, String.valueOf(c.getEmail()));
            st.executeUpdate();
        } catch (Exception e) {
        };
    }

}
