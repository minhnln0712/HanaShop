<%-- 
    Document   : cart
    Created on : Mar 21, 2021, 1:44:02 PM
    Author     : Welcome
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style><%@include file="/WEB-INF/css/header.css"%></style>
<style><%@include file="/WEB-INF/css/btn.css"%></style>
<style><%@include file="/WEB-INF/css/table.css"%></style>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous"></script>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Cart Page</title>
    </head>
    <body>
        <c:set var="CART" value="${sessionScope.CART.cart.values()}"/>
        <c:set var="USERID" value="${sessionScope.USERID}"/>
        <c:set var="TOTAL" value="${sessionScope.TOTAL}"/>
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
                                    <a href="Cart" class="active">CART</a>    
                                </c:if>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fulid">
            <c:if test="${CART == null}">
                <h1 style="color: red">CART EMPTY!GO GET SOME THING!!</h1>
            </c:if>
            <c:if test="${CART != null}">
                <form action="DispatchServlet">
                    <table border="1" class="styled-table">
                        <thead>
                            <tr>
                                <th>No.</th>
                                <th>Name</th>
                                <th>Image</th>
                                <th>Quantity</th>
                                <th>Price</th>
                                <th>Remove</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="list" items="${CART}" varStatus="counter">
                                <tr>
                                    <td>${counter.count}</td>
                                    <td>
                                        <input type="text" name="txtName" value="${list.name}" />
                                        <input type="hidden" name="txtProductID" value="${list.productID}" />
                                    </td>
                                    <td>
                                        <img src="${list.image}" alt="${list.name}" style="max-width: 30%;height: auto;"/>
                                    </td>
                                    <td>
                                        <input type="number" name="txtQuantity" value="${list.quantity}" max="${list.maxquantity}" min="0"/>
                                        <input type="hidden" name="txtQuantityRemain" value="${list.maxquantity - list.quantity}"/>
                                    </td>
                                    <td>
                                        <input type="number" name="txtPrice" value="${list.price}" readonly=""/>
                                    </td>
                                    <td>
                                        <a href="DeleteCartServlet?txtProductID=${list.productID}" class="buttonlink" onclick="return confirm('Are u sure about that?')">Delete</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            <tr>
                                <td colspan="3"></td>
                                <td>
                                    <input class="btn btn-light" type="submit" value="Update Price" name="btAction" />
                                </td>
                                <td>TOTAL: ${TOTAL} USD</td>
                                <td>
                                    <input class="btn btn-dark" type="submit" value="Check out" name="btAction" onclick="return confirm('Are u sure about that?');"/>
                                </td>
                            </tr>
                        </tbody>
                    </table>
                </form>
            </c:if>
        </div>
    </body>
</html>
