<%-- 
    Document   : login
    Created on : Mar 4, 2021, 8:42:39 PM
    Author     : Welcome
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style><%@include file="/WEB-INF/css/login.css"%></style>
<style><%@include file="/WEB-INF/css/btn.css"%></style>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous"/>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous"></script>
<script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://apis.google.com/js/platform.js" async defer></script>
<meta name="google-signin-client_id" content="560502075057-0shipr9vq68eb0lab3rrkdsafiuuiou4.apps.googleusercontent.com">
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login Page</title>
    </head>
    <body>
        <div class="wrapper fadeInDown">
            <div id="formContent">
                <div class="fadeIn first"><h1>Login</h1></div>
                <form action="DispatchServlet" class=".form-horizontal">
                    <c:if test="${requestScope.ERROR!= null}"><font color="red">${requestScope.ERROR}</font></c:if><br/>
                    <input type="hidden" name="txtFullname" value="${param.txtFullname}" id="fullname" />
                    <input type="hidden" name="logingoogle" value="" id="logingoogle" />
                    Username:<input type="text" name="txtUserID" id="userID" class="fadeIn second" placeholder="Username" value="${param.txtUserID}" required/><br/>
                    Password:<input type="password" name="txtPassword" id="password" class="fadeIn third"  placeholder="Password" required/><br/>
                    <input type="submit" name="btAction" class="btn btn-dark" id="Login" value="Login" class="fadeIn sixth"/><br/>
                    <a href="#" onclick="signOut();" id="signout" style="display: none">Sign out</a>
                    <script>
                        function onSignIn(googleUser) {
                            var profile = googleUser.getBasicProfile();
                            document.getElementById("userID").value = profile.getEmail();
                            document.getElementById("fullname").value = profile.getName();
                            document.getElementById("logingoogle").value = "logingoogle";
                            document.getElementById("Login").click();
                            if (document.getElementById("signout").style.display = "none") {
                                document.getElementById("signout").style.display = "block"
                            } else {
                                document.getElementById("signout").style.display = "none"
                            }
                            signOut();
                        }

                    </script>
                    <script>
                        function signOut() {
                            var auth2 = gapi.auth2.getAuthInstance();
                            auth2.signOut().then(function () {
                                console.log('User signed out.');
                                if (document.getElementById("signout").style.display = "block") {
                                    document.getElementById("signout").style.display = "none"
                                }
                                document.getElementById("userID").value = null;
                                document.getElementById("password").value = null;
                            });
                        }
                    </script>
                </form>
                <div id="formFooter">
                    OR : <div class="fadeIn fitht" style="margin-left: 42%;"><div class="g-signin2" data-onsuccess="onSignIn"></div></div>
                    <form action="SignUpPage" class="fadeIn sixth">
                        Don't have account?<a href="signup.jsp" class="buttonlink"> SignUP</a><br/>
                        <a href="DisplayProductServlet" class="buttonlink">Cancel!!</a>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
