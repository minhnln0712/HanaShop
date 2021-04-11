<%-- 
    Document   : update
    Created on : Mar 20, 2021, 8:05:53 AM
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
        <title>Update Product Page</title>
    </head>
    <body>
        <c:set var="UPDATEPRODUCT" value="${requestScope.UPDATEPRODUCT}"/>
        <c:set var="LISTCATEGORY" value="${sessionScope.LISTCATEGORY}"/>
        <c:set var="PRODUCTID" value="${param.txtProductID}"/>
        <c:set var="PAGENO" value="${requestScope.PAGENO}"/>
        <c:set var="SEARCHNAME" value="${requestScope.SEARCHNAME}"/>
        <c:set var="MINPRICE" value="${requestScope.MINPRICE}"/>
        <c:set var="MAXPRICE" value="${requestScope.MAXPRICE}"/>
        <c:set var="CATEGORYID" value="${requestScope.CATEGORYID}"/>
        <c:set var="STATUS" value="${requestScope.STATUS}"/>
        <div class="container-fulid" style="font-weight: 600">
            <div class="row">
                <div class="position-static">
                    <div class="topnav" id="myTopnav">
                        <a class="active" href="DisplayProductServlet">Home</a>
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
                                <a href="Cart" class="active">CART</a>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fulid">
            <form action="DispatchServlet">
                <c:forEach var="product" items="${UPDATEPRODUCT}">
                    <input type="hidden" name="txtProductID" value="${product.productID}" required="" style="width: 84%"/><br/>
                    Product Name:<br/>
                    <input type="text" name="txtName" value="${product.name}" required="" style="width: 84%"/><br/>
                    Product Image Link:<br/>
                    <input type="text" name="txtImage" value="${product.image}" required="" style="width: 84%"/><br/>
                    Product Description:<br/>
                    <textarea rows="5" cols="200" name="txtDescription" style="resize: none;" required="">${product.description}</textarea><br/>
                    Price:<br/>
                    <input type="number" name="txtPrice" value="${product.price}" min="1" required="" style="width: 84%"/><br/>
                    Create Date: <br/>
                    <input type="date" name="txtCreateDate" value="${product.createDate}" required="" style="width: 84%"/><br/>
                    Category:<br/>
                    <select name="cbbCategory" required>
                        <option value="">NONE</option>
                        <c:forEach var="cate" items="${LISTCATEGORY}">
                            <c:if test="${cate.categoryID != product.categoryID}">
                                <option value="${cate.categoryID}">${cate.categoryName}</option>    
                            </c:if>
                            <c:if test="${cate.categoryID == product.categoryID}">
                                <option value="${cate.categoryID}" selected="">${cate.categoryName}</option>    
                            </c:if>
                        </c:forEach>
                    </select><br/>
                    Status:<br/>
                    <select name="cbbStatus">
                        <c:if test="${product.status == true}">
                            <option value="1" selected>ACTIVATED</option>
                            <option value="0">DEACTIVATED</option>
                        </c:if>
                        <c:if test="${product.status == false}">
                            <option value="1">ACTIVATED</option>
                            <option value="0" selected>DEACTIVATED</option>
                        </c:if>
                    </select><br/>
                    Quantity:<br/>
                    <input type="number" name="txtQuantity" value="${product.quantity}" min="0" required=""  style="width: 84%"/><br/>
                    <br/>
                    <input type="hidden" name="pageNo" value="${PAGENO}" />
                    <input type="hidden" name="txtSearchName" value="${SEARCHNAME}" />
                    <input type="hidden" name="txtMinPrice" value="${MINPRICE}" />
                    <input type="hidden" name="txtMaxPrice" value="${MAXPRICE}" />
                    <input type="hidden" name="cbbCategoryy" value="${CATEGORYID}" />
                    <input type="hidden" name="cbbStatuss" value="${STATUS}" />
                    <input type="submit" value="Update" onclick="return confirm('Are u sure about that?')" name="btAction"/>
                </c:forEach>
            </form>
        </div>
    </body>
</html>
