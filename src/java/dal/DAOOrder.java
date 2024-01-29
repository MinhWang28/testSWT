/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dal;

import entity.Customer;
import entity.Order;
import entity.OrderItem;
import entity.Product;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

/**
 *
 * @author Lenovo
 */
public class DAOOrder extends DBConnect {

    public Vector<Order> getAllOrder() {
        String sql = "Select * from [dbo].[Order]";
        Vector<Order> list = new Vector<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Order od = new Order(
                        rs.getInt("order_id"), rs.getInt("customer_id"),
                        rs.getString("order_status"), rs.getDate("order_date"),
                        rs.getDate("required_date"), rs.getDate("shipped_date"),
                        rs.getInt("store_id"), rs.getInt("staff_id")
                );
                System.out.println(od.getOrder_id());
                list.add(od);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public Vector<OrderItem> ListItemFromOrderId(int order_id) {
        String sql = "Select * from order_items where order_id = ?";
        Vector<OrderItem> list = new Vector<>();
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, order_id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                OrderItem oi = new OrderItem(
                        rs.getInt("order_id"), rs.getInt("item_id"),
                        rs.getInt("product_id"), rs.getInt("quantity"),
                        rs.getDouble("list_price"), rs.getDouble("discount")
                );
                list.add(oi);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return list;
    }

    public void signup(Order o) {
        try {
            String sql = "insert into [dbo].[Order](order_id,customer_id,order_status,order_date) values (?,?,?,?)";
            PreparedStatement st = connection.prepareStatement(sql);
            st.setString(1, String.valueOf(o.getOrder_id()));
            st.setString(2, String.valueOf(o.getCustomer_id()));
            st.setString(3, o.getOrder_status());
            java.sql.Date sqlDate = new java.sql.Date(o.getOrder_date().getTime());
            st.setDate(4, sqlDate);
            st.executeUpdate();
        } catch (Exception e) {
            System.out.println("errrr" + e);
        };
    }

    public void signupDetail(ArrayList<OrderItem> orderItems) {
        try {
            Random random = new Random();
            String sql = "insert into OrderItem(item_id, order_id,product_id,quantity,list_price,discount) values (?,?,?,?,?,?)";
            for (OrderItem item : orderItems) {
                System.out.println(item.getOrder_id() + "," + item.getProduct_id());
                PreparedStatement st = connection.prepareStatement(sql);
                st.setInt(1, random.nextInt(100000));
                st.setString(2, String.valueOf(item.getOrder_id()));
                st.setString(3, String.valueOf(item.getProduct_id()));
                st.setInt(4, item.getQuantity());
                st.setDouble(5, item.getList_price());
                st.setDouble(6, item.getDiscount());
                st.executeUpdate();
            }
        } catch (Exception e) {
            System.out.println(e);
        };
    }

    public void setStatusBillID(int id, String string) {
        try {
            String sql = String.format("update [dbo].[Order] set order_status = '%s' where order_id = %d", string, id);
            PreparedStatement st = connection.prepareStatement(sql);
            st.executeUpdate();
        } catch (Exception e) {
        };
    }

    public Order getByID(int id) {
        String sql = "Select * from [dbo].[Order] where order_id = ?";
        try {
            PreparedStatement st = connection.prepareStatement(sql);
            st.setInt(1, id);
            ResultSet rs = st.executeQuery();
            while (rs.next()) {
                Order od = new Order(
                        rs.getInt("order_id"), rs.getInt("customer_id"),
                        rs.getString("order_status"), rs.getDate("order_date"),
                        rs.getDate("required_date"), rs.getDate("shipped_date"),
                        rs.getInt("store_id"), rs.getInt("staff_id")
                );
                return od;
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return null;
    }

    public float getTotalBill(int id) {
        try {
            String sql = String.format("select ISNULL(sum(list_price * quantity * (100 - discount) / 100), 0) as total from OrderItem\n"
                    + "where order_id = %d", id);
            System.out.println("aloaloalo" + sql);
            PreparedStatement st = connection.prepareStatement(sql);
            ResultSet rs = st.executeQuery();
            float total = 0;
            while (rs.next()) {
                total += rs.getFloat("total");
            }
            return total;
        } catch (Exception e) {
            System.out.println(e);
        };
        return 0;
    }
}
    
//    public void checkcount(Account acc, Vector<Product> listItem) {
//        LocalDateTime myDateObj = LocalDateTime.now();  
//        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");  
//        String formattedDate = myDateObj.format(myFormatObj); 
//        int newOrderId = getAllOrder().get(getAllOrder().size() - 1).getOrder_id() + 1;
//        try {
//            String sql1 = "INSERT INTO [dbo].[Orders]\n"
//                    + "           ([order_id]\n"
//                    + "           ,[account_id]\n"
//                    + "           ,[order_date]\n"
//                    + "           ,[status])\n"
//                    + "     VALUES(?, ?, ?, ?)\n";
//            PreparedStatement st = connection.prepareStatement(sql1);
//            st.setInt(1, newOrderId);
//            st.setInt(2, acc.getAccount_id());
//            st.setTimestamp(3, Timestamp.valueOf(formattedDate));
//            st.setString(4, "wait");
//            st.executeUpdate();
//            //insert into order item
//            String sql2 = "SELECT TOP 1 order_id FROM [Orders] ORDER BY order_id DESC";
//            PreparedStatement st2 = connection.prepareStatement(sql2);
//            ResultSet rs = st2.executeQuery();
//            while (rs.next()) {
//                int orderId = rs.getInt("order_id");
//                DAOOrderItem Dod = new DAOOrderItem();
//                //get last order Item
//                int newOrderItemId = Dod.getAllOrderItem().get(Dod.getAllOrderItem().size() - 1).getItem_id() + 1;
//                //add all order in cart to database
//                for (Product item : listItem) {
//                    String sql3 = "INSERT INTO [dbo].[Order_items]\n"
//                            + "           ([item_id]\n"
//                            + "           ,[order_id]\n"
//                            + "           ,[product_id]\n"
//                            + "           ,[price_import]\n"
//                            + "           ,[price_sell]\n"
//                            + "           ,[discount]\n"
//                            + "           ,[quantity])\n"
//                            + "     VALUES(?, ?, ?, ?, ?, ?, ?)";
//                    PreparedStatement st3 = connection.prepareStatement(sql3);
//                    st3.setInt(1, newOrderItemId);
//                    st3.setInt(2, orderId);
//                    st3.setInt(3, item.getProduct_id());
//                    st3.setDouble(4, item.getPrice_import());
//                    st3.setDouble(5, item.getPrice_sell());
//                    st3.setInt(6, item.getDiscount());
//                    st3.setInt(7, item.getQuantity());
//                    st3.executeUpdate();
//                    newOrderItemId++;
//                }
//            }
//        } catch (SQLException e) {
//            System.out.println(e);
//        }
//    }
//}
