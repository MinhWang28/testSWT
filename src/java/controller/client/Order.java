package controller.client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import dal.DAO;
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

/**
 *
 * @author Lenovo
 */
@WebServlet(urlPatterns = {"/Order"})
public class Order extends HttpServlet {

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
        response.setContentType("text/html;charset=UTF-8");
        String service = request.getParameter("Service");
        String pid_raw = request.getParameter("pid");
        String key = "cart-" + pid_raw;
        DAO d = new DAO();
        
        if (service.equals("addToCart")) {
            HttpSession session = request.getSession(true);
            Product p = (Product) session.getAttribute(key);
            if (p == null) {
                Product p_root = d.getProductById(Integer.parseInt(pid_raw));
                Product pAdd = new Product(p_root.getProduct_name(), p_root.getList_price(), 1);
                pAdd.setProduct_id(p_root.getProduct_id());
                session.setAttribute(key, pAdd);
            } else {
                int quantity = p.getQuantity() + 1;
                p.setQuantity(quantity);
                Product pAdd = new Product(p.getProduct_name(), p.getList_price(), quantity);
                pAdd.setProduct_id(p.getProduct_id());
                session.setAttribute(key, pAdd);
            }
        }
        if (service.equals("deleteFromCart")) {
            HttpSession session = request.getSession(true);
            session.removeAttribute(key);
        }
        response.sendRedirect("OrderItem");
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
