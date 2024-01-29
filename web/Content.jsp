<%@page import="dal.DAOCustomer"%>
<%@page import="entity.Customer"%>
<%@page import="entity.Order"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%@page import="entity.Product" %>
<%@page import="java.util.Vector" %>
<%@page import="dal.DAO" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Content</title>
    </head>
    <body>
        <div class="container">
            <%
                Vector<Product> products;
                //--admin quan ly
                Vector<Customer> customers;
                DAOCustomer dao_c = new DAOCustomer();
                customers = dao_c.getAllCustomer();
                int bill_change_id = -1;  
                //                
                if (request.getAttribute("products") != null) {
                    products = (Vector<Product>) request.getAttribute("products");
                } else {
                    DAO d = new DAO();
                    products = d.getAll();
                }
                if (request.getAttribute("list_orders") != null) {
                    if (request.getAttribute("bill_change") != null) {
                        bill_change_id = ((Order) request.getAttribute("bill_change")).getOrder_id();
                    }
                }
            %>
            <c:if test="${sessionScope.user != null && sessionScope.isAdmin != null}">

                <% if(request.getAttribute("current_action") != null && request.getAttribute("current_action").equals("Products")) { %>
                <table class="w-100 border-col">
                    <tr>                        
                        <th>Tên sản phẩm</th>
                        <th>Giá thành</th>
                        <th>Hãng sản xuất</th>                        
                        <th>Sản xuất năm</th>
                        <th>Phân loại</th>  
                    </tr>            
                    <%for (Product product : products) {%>
                    <tr>
                        <td><%=product.getProduct_name()%></td>
                        <td><%=product.getList_price()%></td>
                        <td><%=product.getBrand_name()%></td>
                        <td><%=product.getModel_year()%></td>
                        <td><%=product.getCategory_name()%></td>      
                    </tr>         
                    <%}%>
                </table>
                <% } %>
            </c:if>
<!--//hien thi danh sach san pham-->
            <c:if test="${sessionScope.user != null && requestScope.list_orders != null}">
                <% if(request.getAttribute("current_action") != null && request.getAttribute("current_action").equals("Products")) { %>
                <table class="w-100 border-col">
                    <tr>                        
                        <th>Tên sản phẩm</th>
                        <th>Giá thành</th>
                        <th>Hãng sản xuất</th>                        
                        <th>Sản xuất năm</th>
                        <th>Phân loại</th>    
                    </tr>            
                    <%for (Product product : products) {%>
                    <tr>
                        <td><%=product.getProduct_name()%></td>
                        <td><%=product.getList_price()%></td>
                        <td><%=product.getBrand_name()%></td>
                        <td><%=product.getModel_year()%></td>
                        <td><%=product.getCategory_name()%></td>  
                    </tr>         
                    <%}%>
                </table>
                <% } %>
                
                <!--hien thi danh sach order-->
                <% if(request.getAttribute("current_action") != null && request.getAttribute("current_action").equals("Orders")) { %>
                <div>
                    <a href="Orders?choose=filter&status=1">Wait</a>
                    <a href="Orders?choose=filter&status=2">Proccess</a>
                    <a href="Orders?choose=filter&status=3">Done</a>
                </div>
                
                
                <form action="Orders">
                    <table class="w-100 border-col">
                        <tr>                        
                            <th>Bill ID</th>
                            <th>Customer Name</th>
                            <th>Date</th>                        
                            <th>Total</th>
                            <th>Status</th>                        
                            <th>Views</th>
                        </tr>  
                        <%for (Order o : (Vector<Order>) request.getAttribute("list_orders")) {%>
                        <tr> 
                            <td><%=o.getOrder_id()%></td>
                            <td>
                                <%for (Customer c : customers)
                                        if (o.getCustomer_id() == c.getCustomer_id()) {
                                %>
                                <%=c.getEmail()%>
                                <%}%>
                            </td>
                            <td><%=o.getOrder_date()%></td>
                            <td><%=o.getTotal()%></td>
                            <td>
                                <c:if test="${requestScope.bill_change != null}">
                                    <input type="hidden" name="bill_id" value="<%=o.getOrder_id()%>">
                                    <select name="bill_status" id="bill_status">
                                        <option value="Waiting">Waiting</option>
                                        <option value="Process">Process</option>
                                        <option value="Done">Done</option>
                                    </select>
                                </c:if>                            
                                <c:if test="${requestScope.bill_change == null}"><%=o.getOrder_status()%></c:if>
                                </td>
                                <td>
                                <c:if test="${requestScope.bill_change != null}">
                                    <input type="submit" name="submitButton" value="Change Status">
                                </c:if>
                                <c:if test="${requestScope.bill_change == null}">
                                    <a href="Orders?Service=detail&bid=<%=o.getOrder_id()%>">detail</a>
                                </c:if>                                
                            </td>
                        </tr>
                        <%}%>
                    </table>
                </form>
                <%}%>
            </c:if>

            <!--hien thi danh sach san pham cho cus-->
            <c:if test="${sessionScope.user != null && sessionScope.isAdmin == null}">
                <table class="w-100 border-col">
                    <tr>                        
                        <th>Tên sản phẩm</th>
                        <th>Giá thành</th>
                        <th>Hãng sản xuất</th>                        
                        <th>Sản xuất năm</th>
                        <th>Phân loại</th>                        
                        <th>Add</th>
                    </tr>            
                    <%for (Product product : products) {%>
                    <tr>
                        <td><%=product.getProduct_name()%></td>
                        <td><%=product.getList_price()%></td>
                        <td><%=product.getBrand_name()%></td>
                        <td><%=product.getModel_year()%></td>
                        <td><%=product.getCategory_name()%></td>                       
                        <td>
                            <a href="Order?Service=addToCart&pid=<%=product.getProduct_id()%>">Add</a>
                        </td>
                    </tr>         
                    <%}%>
                </table>
            </c:if>
        </div>
    </body>
</html>
