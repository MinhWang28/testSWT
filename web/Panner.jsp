<%-- 
    Document   : Panner
    Created on : Oct 31, 2023, 2:00:50 PM
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <title>JSP Panner</title>
    </head>
    <body>
        <%
                String branch = "*";
                if (request.getAttribute("branch") != null) {
                    branch = (String) request.getAttribute("branch");
                }                
        %>
        <nav class="navbar navbar-expand-lg navbar-light bg-light">            
            <div class="info-website">                
                <div class="user-info">
                    <div class="rollNumber">
                        <label>Roll Number:</label>
                        <c:if test="${sessionScope.user != null}">
                            <span class="f-w-500">${sessionScope.user.user_email}</span>
                        </c:if>                        
                    </div>
                    <div class="userName">
                        <label>Welcome:</label>
                        <c:if test="${sessionScope.user != null}">
                            <span class="f-w-500">${sessionScope.user.user_name}</span>
                        </c:if>                        
                    </div>           
                </div>
            </div>            
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav">
                    <c:if test="${sessionScope.user == null}">
                        <li class="nav-item">
                            <a class="nav-link" href="Users?action=login">Login</a>
                        </li>
                    </c:if>
                        
                    <c:if test="${sessionScope.user != null}">
                        <li class="nav-item">
                            <a class="nav-link" href="Users?action=logout">Logout</a>
                        </li>
                        <% if(request.getAttribute("current_action") != null && request.getAttribute("current_action").equals("Orders")) { %>
                        <li class="nav-item" style="display: contents;">
                            <form action="Orders">
                                <input type="text" id="search_bill_id" name="search_bill_id" 
                                       placeholder="Enter your id">
                                <input type="submit" name="BtnSearchBill" value="Search">
                            </form>
                        </li>
                        <% }%>
                    </c:if>
                      
                    <c:if test="${sessionScope.user != null && sessionScope.isAdmin == null}">
                        <!--                        <li class="nav-item">
                                                    <a class="nav-link" href="#">Register</a>
                                                </li>-->
                    <li class="nav-item">
                            <a class="nav-link" style="color: red" href="OrderItem">
                                Show cart:   
                            </a>
                        </li>
                                          <!--${sessionScope.sizeOrder}-->
                        <li class="nav-item" style="display: contents;">
                            <form action="Products">
                                <input type="hidden" name="branch" value="<%=branch%>">
                                <input type="text" id="search_name_project" name="search_name_project" 
                                       placeholder="Enter your name">
                                <input type="submit" name="submitButton" value="Search">
                            </form>
                        </li>
                    </c:if>                   
                </ul>
            </div>
        </nav>                
    </body>
</html>
