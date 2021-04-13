<%-- 
    Document   : historydetail
    Created on : Mar 21, 2021, 11:20:16 PM
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
        <title>JSP Page</title>
    </head>
    <body>
        <c:set var="LISTHISTORYDETAIL" value="${sessionScope.LISTHISTORYDETAIL}"/>
        <c:set var="ALLPRODUCT" value="${sessionScope.ALLPRODUCT}"/>
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
                            <a href="DisplayHistoryServlet" class="active">History</a>
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
                        <th>Name</th>
                        <th>Image</th>
                        <th>Price</th>
                        <th>Quantity</th>
                    </tr>
                </thead>
                <tbody style="text-align: center">
                    <c:forEach var="historydetail" items="${LISTHISTORYDETAIL}" varStatus="counter">
                        <c:forEach var="product" items="${ALLPRODUCT}">
                            <c:if test="${product.productID == historydetail.productID}">
                                <tr>
                                    <td>${counter.count}</td>
                                    <td>
                                        ${product.name}
                                    </td>
                                    <td>
                                        <img src="${product.image}" alt="${product.name}" style="max-width: 30%;height: auto;"/>
                                    </td>
                                    <td>${product.price} USD</td>
                                    <td>${historydetail.quantity}</td>
                                </tr>
                            </c:if>
                        </c:forEach>
                    </c:forEach>
                </tbody>
            </table>
        </div>
    </body>
</html>
