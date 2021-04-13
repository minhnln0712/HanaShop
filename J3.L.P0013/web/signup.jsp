<%-- 
Document   : signup
Created on : Mar 23, 2021, 7:41:53 PM
Author     : Welcome
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style><%@include file="/WEB-INF/css/login.css"%></style>
<script src="https://www.google.com/recaptcha/api.js"></script>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign up Page</title>
    </head>
    <body>
        <div class="wrapper fadeInDown">
            <div id="formContent">
                <div class="fadeIn first"><h1>Sign Up Form</h1></div>
                <form action="DispatchServlet" class=".form-horizontal">
                    <c:if test="${requestScope.ERROR!= null}"><font color="red">${requestScope.ERROR}</font></c:if><br/>
                    Username: <input type="text" name="txtUserID" value="${param.txtUserID}" required="" class="fadeIn second" placeholder="Username"/><br/>
                    Password:<input type="password" name="txtPassword" value="${param.txtPassword}" required="" class="fadeIn second" placeholder="Password"/><br/>
                    Confirm:<input type="password" name="txtConfirmPassword" value="${param.txtConfirmPassword}" required="" class="fadeIn second" placeholder="Confirm Password"/><br/>
                    Full name: <input type="text" name="txtFullname" value="${param.txtFullname}" required="" class="fadeIn third" placeholder="Fullname"/><br/>
                    <input type="submit" value="Sign Up" name="btAction"/>
                    <a href="login.jsp">Cancel</a>
                </form>
            </div>
        </div>
    </body>
</html>
