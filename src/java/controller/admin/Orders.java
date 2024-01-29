/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.admin;

import com.oracle.wls.shaded.org.apache.xpath.operations.Or;
import dal.DAOOrder;
import entity.Order;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "Orders", urlPatterns = {"/Orders"})
public class Orders extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");
        DAOOrder daoo = new DAOOrder();
        Vector<Order> list_orders = daoo.getAllOrder();
        
        for (Order item : list_orders) {
            item.setTotal(daoo.getTotalBill(item.getOrder_id()));
        }

        String BtnSearchBill = request.getParameter("BtnSearchBill");
        if (BtnSearchBill != null && BtnSearchBill.equals("Search")) {
            String bill_id_str = request.getParameter("search_bill_id");
            if (convertToNum(bill_id_str)) {
                int bill_id = Integer.parseInt(bill_id_str);
                Vector<Order> list_orders_domain = new Vector<Order>();
                for (Order o : list_orders) {
                    if (o.getOrder_id() == bill_id) {
                        list_orders_domain.add(o);
                    }
                }
                list_orders = list_orders_domain;
            }
        }

        String submitButton = request.getParameter("submitButton");
        if (submitButton != null) {
            if (submitButton.equals("Change Status")) {
                int id = Integer.parseInt((String) request.getParameter("bill_id"));
                daoo.setStatusBillID(id, (String) request.getParameter("bill_status"));
                list_orders.clear();
                Order o = daoo.getByID(id);
                float total = daoo.getTotalBill(id);
                o.setTotal(total);
                list_orders.add(o);
            }
        } else {
            String service = request.getParameter("Service");
            String bid = request.getParameter("bid");
            if (service != null && service.equals("detail")) {
                list_orders.clear();
                Order o = daoo.getByID(Integer.parseInt(bid));
                float total = daoo.getTotalBill(Integer.parseInt(bid));
                o.setTotal(total);
                list_orders.add(o);
                request.setAttribute("bill_change", o);
            }
        }
        //if choose is action find acording status
        String choose = request.getParameter("choose");
        if(choose != null) {
            //get status in url 
                String status = request.getParameter("status");
                Vector<Order> list_orders_domain = new Vector<Order>();
                for (Order o : list_orders) {
                    if (o.getOrder_status().equals(status)) {
                        list_orders_domain.add(o);
                    }
                }
                list_orders = list_orders_domain;
        }
        
        HttpSession session = request.getSession(true); 
        String currentURL = request.getRequestURL().toString();
        request.setAttribute("current_action", "Orders");
        request.setAttribute("list_orders", list_orders);
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private boolean convertToNum(String bill_id_str) {
        try {
            int n = Integer.parseInt(bill_id_str);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

}
