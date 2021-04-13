<%-- 
    Document   : create
    Created on : Mar 21, 2021, 9:40:12 AM
    Author     : Welcome
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<style><%@include file="/WEB-INF/css/header.css"%></style>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous"></script>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Create Page</title>
    </head>
    <body style="background: linear-gradient(to top, #ffccff 1%, #ffffff 100%);">
        <c:set var="LISTCATEGORY" value="${sessionScope.LISTCATEGORY}"/>
        <div class="container-fulid" style="font-weight: 600">
            <div class="row">
                <div class="position-static">
                    <div class="topnav" id="myTopnav">
                        <a href="DisplayProductServlet" class="active">Home</a>
                        <c:if test="${ROLE == NULL}">
                            <div class="logintopnav">
                                <a href="login.jsp">Login</a>
                            </div>
                        </c:if>
                        <c:if test="${ROLE != NULL}">
                            <a href="DisplayHistoryServlet">History</a>
                            <a href="DisplayHistoryActionServlet">Action History</a>
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
        <div class="container-fulid" style="margin-left: 5%;">
            <form action="DispatchServlet">
                <c:if test="${requestScope.ERROR!= null}"><font color="red">${requestScope.ERROR}</font></c:if><br/>
                    Product Name:<br/>
                    <input type="text" name="txtName" value="${product.name}" required="" style="width: 90%"/><br/>
                Product Image Link:<br/>
                <input type="text" name="txtImage" value="${product.image}" required="" style="width: 90%"/><br/>
                Product Description:<br/>
                <textarea rows="5" cols="230" name="txtDescription" style="resize: none;" required="">${product.description}</textarea><br/>
                Price:<br/>
                <input type="number" name="txtPrice" value="${product.price}" required="" style="width: 90%" min="1"/><br/>
                Category: <br/>
                <select name="cbbCategory" required>
                    <option value="">NONE</option>
                    <c:forEach var="cate" items="${LISTCATEGORY}">
                        <option value="${cate.categoryID}">${cate.categoryName}</option>    
                    </c:forEach>
                </select><br/>
                Quantity:<br/>
                <input type="number" name="txtQuantity" value="${product.quantity}" required=""  style="width: 90%" min="0"/><br/>
                <br/>
                <input type="submit" value="Create" onclick="return confirm('Are u sure about that?')" name="btAction"/>
            </form>
        </div>
    </body>
</html>
