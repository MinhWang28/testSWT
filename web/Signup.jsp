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
                    font-size: large;">Đăng ký tài khoản</li>
            </ul>
        </div>         
    </div>
    <!--breadcrumbs area end-->
    <div class="customer_login">
        <div class="container" style="padding-left: 10%;margin-left: unset !important;">
            <div class="row">
                <!--register area start-->
                <div class="col-lg-6 col-md-6">
                    <div class="account_form register">
                        <form action="Users?action=signup" method="POST">
                            <p style="color: red; align-content: center;">
                                ${requestScope.error_pass}
                            </p>
                            <p style="color: blue; align-content: center;">
                                ${requestScope.done}
                            </p>
                            <p style="color: red; align-content: center;">
                                ${requestScope.emailavailable}
                            </p>
                            <p>   
                                <label>Email <span>*</span></label>
                                <input type="email" name="user_email">
                            </p>
                            <p>   
                                <label>Mật khẩu <span>*</span></label>
                                <input type="password" name="user_pass">
                            </p>
                            <p>   
                                <label>Nhập lại mật khẩu <span>*</span></label>
                                <input type="password" name="re_pass">
                            </p>
                            <div class="login_submit">
                                <button type="submit">Đăng ký</button>
                            </div>
                        </form>
                    </div>    
                </div>
                <!--register area end-->
            </div>
        </div>    
    </div>
</body>
</html>
