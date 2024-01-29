<%-- 
    Document   : Menu
    Created on : Oct 31, 2023, 2:01:23 PM
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<%--<%@page import="java.util.Vector" %>--%>
<%@page import="java.util.Vector" %>
<%@page import="dal.DAO" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Menu</title>
        <link rel="stylesheet" href="assets/css/style.css">
    </head>
    <body>      
        <nav class="navbar navbar-expand-lg navbar-light">           
            <ul class="navbar-nav flex-column">       
                <c:if test="${sessionScope.user == null}">                    
                </c:if>
                <!--ktra co ton tai tk khong-->
                <c:if test="${sessionScope.user != null}">
                    <!--neu la admin-->
                    <c:if test="${sessionScope.user.isAdmin == 1}">
                        <li class="nav-item">
                            <a class="nav-link" href="#">Customer Manager</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="Products">Product Manager</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="Orders">Bill Manager</a>
                        </li>
                    </c:if>
                        <!--khong phai la admin-->
                    <c:if test="${sessionScope.user.isAdmin != 1}">
                        <%
                            DAO d = new DAO();
                            Vector<String> all_branchs = d.getAllBranch();
                        %>
                        <%for (String branch : all_branchs) {%>
                        <li class="nav-item">
                            <a class="nav-link" href="Products?action=listByBranch&branch=<%=branch%>"><%=branch%></a>
                        </li>
                        <%}%>
                    </c:if>  
                        
                        
                </c:if>
            </ul>
        </nav>
    </body>
</html>
