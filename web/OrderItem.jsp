<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="java.util.Vector" %>
<%@page import="entity.Product" %>
<%@page import="dal.DAO" %>
<%@page import="entity.Product" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <title>JSP My Cart</title>
        <!-- Main Style CSS -->
        <link rel="stylesheet" href="assets/css/style.css">
    </head>
    <body>
        <!--breadcrumbs area start-->
        <div class="breadcrumb_content">
            <ul style="padding-left: unset;">
                <li><a href="Home" style="font-weight: 700;
                       font-size: large;">home</a></li>
<!--                <li class="nav-item">
                            <a class="nav-link" style="color: red" href="OrderItem">
                                Show cart:   ${sessionScope.sizeOrder}
                            </a>
                </li>-->
            </ul>
        </div>
        <form action="OrderItem" style="padding-left: 10%;
              padding-right: 10%;">
            <!--breadcrumbs area end-->
            <table class="d-border border-col w-100 table-cart">
                <thead>
                    <tr>
                        <th>ID</th>
                        <th>Tên sản phẩm</th>
                        <th>Số lượng</th>
                        <th>Giá thành</th>
                        <th>Thành tiền</th>
                        <th>Xóa</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        //get all key in session
                        java.util.Enumeration em = session.getAttributeNames();
                        //        getkeys()
                        double t = 0;
                        while (em.hasMoreElements()) {
                            String id = em.nextElement().toString(); //get key
                            if (id.startsWith("cart")) {
                                Product pro = (Product) session.getAttribute(id);
                                t += pro.getList_price() * pro.getQuantity();
                                out.println("<tr>");
                                out.println("<td>" + id.substring(5) + "</td>");
                                out.println("<td>" + pro.getProduct_name() + "</td>");
                                out.println("<td>" + "<input name=\"" + id + "\"" + "type=\"text\" value=\"" + pro.getQuantity() + "\">" + "</td>");
                                out.println("<td>" + pro.getList_price() + "</td>");
                                out.println("<td>" + pro.getQuantity() * pro.getList_price() + "</td>");
                                out.println("<td>" + "<a href=\"Order?Service=deleteFromCart&pid=" + id.substring(5) + "\">Xóa</a>" + "</td>");
                                out.println("</tr>");
                            }
                        }
                    %>
                </tbody>
                <tr>
                    <td></td>
                    <td></td>
                    <td><input type="submit" name="submitButton" value="Cập nhật tất cả"></td>
                    <td><input type="submit" name="submitButton" value="checkout"></td>
                    <td>Tổng thành tiền</td>
                    <td><%=t%></td>
                    <td><input type="submit" name="submitButton" value="Xóa tất"></td>
                </tr>
            </table>
        </form>
    </body>
</html>

