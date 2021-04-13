<%-- 
    Document   : historyaction
    Created on : Mar 20, 2021, 8:57:31 AM
    Author     : Welcome
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<style><%@include file="/WEB-INF/css/header.css"%></style>
<style><%@include file="/WEB-INF/css/table.css"%></style>
<style><%@include file="/WEB-INF/css/btn.css"%></style>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous"></script>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Action History Page</title>
    </head>
    <body>
        <c:set var="LISTACTION" value="${sessionScope.LISTACTION}"/>
        <div class="container-fulid" style="font-weight: 600">
            <div class="row">
                <div class="position-static">
                    <div class="topnav" id="myTopnav">
                        <a href="DisplayProductServlet">Home</a>
                        <c:if test="${ROLE == NULL}">
                            <div class="logintopnav">
                                <a href="login.jsp">Login</a>
                            </div>
                        </c:if>
                        <c:if test="${ROLE != NULL}">
                            <a href="DisplayHistoryServlet">History</a>
                            <c:if test="${ROLE == 'ADMIN'}">
                            <a href="DisplayHistoryActionServlet">Action History</a>    
                            </c:if>
                            <div class="logintopnav">
                                <h3>Hello,<font color="#66ff33">${NAME}</font></h3>
                                <a href="LogoutServlet" >Logout</a>
                                <c:if test="${ROLE == 'USER'}">
                                <a href="Cart">CART</a>    
                                </c:if>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fulid">
            <table border="1" class="styled-table">
                <thead>
                    <tr>
                        <th>No.</th>
                        <th>Action ID</th>
                        <th>Product ID</th>
                        <th>User ID</th>
                        <th>Action Type</th>
                        <th>Create Date</th>
                    </tr>
                </thead>
                <tbody style="text-align: center">
                    <c:forEach var="action" items="${LISTACTION}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>${action.actionID}</td>
                            <td>${action.productID}</td>
                            <td>${action.userID}</td>
                            <td>${action.actionType}</td>
                            <td>
                                <input type="date" name="" value="${action.createDate}" readonly=""/>
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
