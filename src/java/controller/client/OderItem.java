/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller.client;

import entity.Product;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Vector;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "OrderItem", urlPatterns = {"/OrderItem"})
public class OderItem extends HttpServlet {

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
        String submitButton = request.getParameter("submitButton");
        System.out.println("");
        if (submitButton != null) {
            if (submitButton.equals("Xóa tất")) {
                HttpSession session = request.getSession(true);
                ArrayList<String> keys = new ArrayList<>();
                java.util.Enumeration em = session.getAttributeNames();
                while (em.hasMoreElements()) {
                    String id = em.nextElement().toString(); //get key
                    if (id.startsWith("cart")) {
                        keys.add(id);
                    }
                }
                for (String item : keys) {
                    session.removeAttribute(item);
                }
            }
            //sua           
            if (submitButton.equals("Cập nhật tất cả")) {
                HttpSession session = request.getSession(true);
                ArrayList<String> keys = new ArrayList<>();
                java.util.Enumeration em = session.getAttributeNames();
                while (em.hasMoreElements()) {
                    String id = em.nextElement().toString(); //get key
                    if (id.startsWith("cart")) {
                        keys.add(id);
                    }
                }
                for (String item : keys) {
                    Product p = (Product) session.getAttribute(item);
                    System.out.println(item + " : " + request.getParameter(item));                  
                    int quan = Integer.parseInt(request.getParameter(item));
                    if(quan > 0) {
                      p.setQuantity(quan);
                    }
                    session.setAttribute(item, p);
                }
            }
            if (submitButton.equals("checkout")) {
                HttpSession session = request.getSession(true);
                int totalOrder = 0;
                Vector<Product> list = new Vector<>();
                java.util.Enumeration em = session.getAttributeNames();
                while (em.hasMoreElements()) {
                    String id = em.nextElement().toString();
                    if (id.startsWith("cart")) {
                        Product p = (Product) session.getAttribute(id);
                        list.add(p);
                        session.removeAttribute(id);
                    }
                }
            }
        }
        
        if (submitButton == null) {
            HttpSession session = request.getSession(true);
            int totalOrder = 0;
            java.util.Enumeration em = session.getAttributeNames();
            while (em.hasMoreElements()) {
                String id = em.nextElement().toString(); //get key
                if (id.startsWith("cart")) {
                    Product p = (Product) session.getAttribute(id);
                    totalOrder+=p.getQuantity();
                }
            }
                session.setAttribute("sizeOrder", totalOrder);
        }
        request.getRequestDispatcher("OrderItem.jsp").forward(request, response);
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

}
