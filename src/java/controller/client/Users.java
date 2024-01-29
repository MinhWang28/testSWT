package controller.client;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import dal.DAOCustomer;
import dal.DAOOrder;
import dal.DAOUser;
import entity.Customer;
import entity.Order;
import entity.OrderItem;
import entity.Product;
import entity.User;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.Date;

/**
 *
 * @author Lenovo
 */
@WebServlet(urlPatterns = {"/Users"})
public class Users extends HttpServlet {

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
        String action = request.getParameter("action");
        if (action.equals("login")) {
            request.getRequestDispatcher("Login.jsp").forward(request, response);
        }
        
        HttpSession session = request.getSession(true); 

        if (action.equals("logout") && session.getAttribute("user") != null) {
            Random random = new Random();         
            // ghi du lieu don hang 
            User u = (User) session.getAttribute("user");
            DAOCustomer dao_c = new DAOCustomer();           
            Customer c = dao_c.getCustomerByEmail(u.getUser_email());;
            if (c == null){
                c = new Customer();
                c.setEmail(u.getUser_email());
                c.setCustomer_id(random.nextInt(10000));
                dao_c.signup(c);
            }
            Order o = new Order();
            o.setCustomer_id(c.getCustomer_id());
            o.setOrder_id(random.nextInt(10000) + random.nextInt(100));
            o.setOrder_status("Wait");
            o.setOrder_date(new Date());
            
            ArrayList<OrderItem> orderItems = new ArrayList<>();
            java.util.Enumeration em = session.getAttributeNames();
            while (em.hasMoreElements()) {
                String id = em.nextElement().toString(); //get key
                if (id.startsWith("cart")) {
                    Product p = (Product) session.getAttribute(id);
                    OrderItem oi = new OrderItem();
                    oi.setOrder_id(o.getOrder_id());
                    oi.setProduct_id(p.getProduct_id());
                    oi.setQuantity(p.getQuantity());
                    oi.setList_price(p.getList_price());
                    oi.setDiscount(0);
                    orderItems.add(oi);
                }
            }
            DAOOrder dao_o = new DAOOrder();
            if (orderItems.size() > 0){
                dao_o.signup(o);
                dao_o.signupDetail(orderItems);
            }            
            session.removeAttribute("isAdmin");
            session.removeAttribute("user");
            response.sendRedirect("Home");
        }
        
        if (action.equals("signup_new")) {
            request.getRequestDispatcher("Signup.jsp").forward(request, response);
        }

        if (action.equals("checkLogin")) {
            String user_email = request.getParameter("user_email");
            String user_pass = request.getParameter("user_pass");
            String remember = request.getParameter("remember");
            
            DAOUser dao = new DAOUser();
            User user = dao.checkUser(user_email, user_pass);
            if (user == null) {
                request.setAttribute("error", "Tài khoản không tồn tại !");
                request.getRequestDispatcher("Users?action=login").forward(request, response);
            } else {
                session.setAttribute("user", user);
                if (user.getIsAdmin() == 1)
                    session.setAttribute("isAdmin", user);
                    response.sendRedirect("Home");
            }
        }
        if (action.equals("signup")) {
            String user_email = request.getParameter("user_email");
            String user_pass = request.getParameter("user_pass");
            String re_pass = request.getParameter("re_pass");
            if (!user_pass.equals(re_pass)) {
                request.setAttribute("error_pass", "Mật khẩu không trùng khớp. Hãy nhập lại...");
                request.getRequestDispatcher("user?action=login").forward(request, response);
            } else {
                DAOUser dao = new DAOUser();
                User a = dao.checkAcc(user_email);
                if (a == null) {
                    dao.signup(user_email, user_pass);
                    request.setAttribute("done", "ok");
                    request.getRequestDispatcher("Home").forward(request, response);
                } else {
                    request.setAttribute("emailavailable", "Email đã tồn tại!");
                    request.getRequestDispatcher("Users?action=login").forward(request, response);
                }
            }
        }
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
