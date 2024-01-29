<%-- 
    Document   : index
    Created on : Oct 31, 2023, 1:32:38 PM
    Author     : Lenovo
--%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="dal.DAO" %>
<!DOCTYPE html>
<html class="no-js" lang="vi">
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <title>My Store</title>
        <!-- Main Style CSS -->
        <link rel="stylesheet" href="assets/css/style.css">
    </head>
    <body>        
        <!--trang tong-->
        <div class="container-fluid">
            <header>
                <jsp:include page="Panner.jsp"/>                
            </header> 
            <%--<c:if test="${sessionScope.user != null}">--%>
                <main>
                    <div class="row">
                        <div class="d-border col-md-3 sidebar">
                            <jsp:include page="Menu.jsp"/>
                        </div>
                        <div class="d-border col-md-9">
                            <jsp:include page="Content.jsp"/>
                        </div>
                    </div>
                </main>
            <%--</c:if>--%>
        </div>        
    </body>
</html>
