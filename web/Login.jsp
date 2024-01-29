<%-- 
    Document   : Login
    Created on : Nov 1, 2023, 8:47:26 AM
    Author     : Lenovo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
        <title>Đăng nhập và Đăng ký</title>
        <!-- Main Style CSS -->
        <link rel="stylesheet" href="assets/css/style.css">
    </head>
    <body>
        <!--breadcrumbs area start-->
        <div class="breadcrumb_content">
            <ul style="padding-left: unset;">
                <li><a href="Home" style="font-weight: 700;
                       font-size: large;">home</a></li>
                <li>/</li>
                <li style="font-weight: 700;
                    font-size: large;">Đăng nhập</li>
            </ul>
        </div>         
    </div>
    <!--breadcrumbs area end-->
    <div class="customer_login">
        <div class="container" style="padding-left: 10%;margin-left: unset !important;">
            <div class="row">
                <!--login area start-->
                <div class="col-lg-6 col-md-6">
                    <div class="account_form">
                        <form action="Users?action=checkLogin" method="POST">
                            
                            <p style="color: red; align-content: center;">
                                ${requestScope.error}
                            </p>
                            
                            <p>   
                                <label>Email <span>*</span></label>
                                <input name="user_email" type="text" >
                            </p>
                            <p>   
                                <label>Mật khẩu <span>*</span></label>
                                <input name="user_pass" type="password" >
                            </p> 
                            <div class="login_submit">
                                <button type="submit">Đăng nhập</button>
                            </div>
                        </form>
                        <div class="login_submit"><a href="Users?action=signup_new" style="float: right;">Tạo tài khoản mới</a></div>
                    </div>    
                </div>
                <!--login area start-->
            </div>
        </div>    
    </div>
</body>
</html>
