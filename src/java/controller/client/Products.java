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
import java.util.Vector;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 *
 * @author Lenovo
 */
public class Products extends HttpServlet {

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
        DAO d = new DAO();
        Vector<Product> products = new Vector<>();
        products = d.getAll();

        String action = request.getParameter("action");
        if (action != null && action.equalsIgnoreCase("listByBranch")) {
            String branch = request.getParameter("branch");
            request.setAttribute("branch", branch);
            products = d.getAllByBranch(branch);
        }
        String submitButton = request.getParameter("submitButton");
        if (submitButton != null && submitButton.equals("Search")) {
            String name = request.getParameter("search_name_project");
            String branch = request.getParameter("branch");
            if (!branch.equals("*")) {
                products = d.getAllByBranch(branch);
                request.setAttribute("branch", branch);
            }
            if (!name.equals("")) {
                Vector<Product> ans_domain = new Vector<>();
                for (Product item : products) {
                    if (item.getProduct_name().contains(name)) {
                        ans_domain.add(item);
                    }
                }
                products = ans_domain;
            }
        }
        request.setAttribute("current_action", "Products");
        request.setAttribute("products", products);
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

}
