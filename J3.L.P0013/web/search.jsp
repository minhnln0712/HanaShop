<%-- 
    Document   : search
    Created on : Mar 17, 2021, 9:34:44 AM
    Author     : Welcome
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<style><%@include file="/WEB-INF/css/paging.css"%></style>
<style><%@include file="/WEB-INF/css/header.css"%></style>
<style><%@include file="/WEB-INF/css/table.css"%></style>
<style><%@include file="/WEB-INF/css/btn.css"%></style>
<style><%@include file="/WEB-INF/css/image.css"%></style>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl" crossorigin="anonymous">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js" integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0" crossorigin="anonymous"></script>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Search Page</title>
    </head>
    <body style="background: linear-gradient(to top, #ffccff 1%, #ffffff 100%);">
        <c:set var="LISTPRODUCT" value="${requestScope.LISTPRODUCT}"/>
        <c:set var="ATTACHLIST" value="${requestScope.ATTACHLIST}"/>
        <c:set var="LISTCATEGORY" value="${sessionScope.LISTCATEGORY}"/>
        <c:set var="SUGGESTLIST" value="${sessionScope.SUGGESTLIST}"/>
        <c:set var="ROLE" value="${sessionScope.ROLE}"/>
        <c:set var="NAME" value="${sessionScope.NAME}"/>
        <c:set var="MAXPAGE" value="${sessionScope.MAXPAGE}"/>
        <c:set var="PAGENO" value="${requestScope.PAGENO}"/>
        <c:set var="ERROR" value="${requestScope.ERROR}"/>
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
                                <a href="LogoutServlet">Logout</a>
                                <c:if test="${ROLE == 'USER'}">
                                    <a href="cart.jsp">CART</a>
                                </c:if>
                            </div>
                        </c:if>
                    </div>
                </div>
            </div>
        </div>
        <div class="container-fulid">
            <h1 style="font-family: 'Comic Sans MS', cursive, sans-serif;font-size: 40px;letter-spacing: 2px;word-spacing: 2px;color: #ff4da6;font-weight: 700;text-decoration: none solid rgb(68, 68, 68);font-style: normal;font-variant: small-caps;text-transform: capitalize; text-align:  center;">
                🌸Hana Shop🌸
            </h1>
        </div>
        <div class="container-fulid" style="text-align: center;">
            <form action="DispatchServlet" style="font-weight: 600">
                Name: <input type="text" name="txtSearchName" value="${requestScope.SEARCHNAME}" placeholder="Product Name"/>
                Price
                <input type="number" name="txtMinPrice" value="${requestScope.MINPRICE}" min="0" max="999999999" placeholder="Min"/>
                -<input type="number" name="txtMaxPrice" value="${requestScope.MAXPRICE}" min="0" max="999999999" placeholder="Max"/>
                Category:
                <select name="cbbCategory" required>
                    <c:if test="${requestScope.CATEGORY == null}">
                        <option value="%" selected>ALL</option>
                        <c:forEach var="cate" items="${LISTCATEGORY}">
                            <option value="${cate.categoryID}">${cate.categoryName}</option>    
                        </c:forEach>
                    </c:if>
                    <c:if test="${requestScope.CATEGORY != null}">
                        <option value="%">ALL</option>
                        <c:forEach var="cate" items="${LISTCATEGORY}">
                            <c:if test="${requestScope.CATEGORY != cate.categoryID}">
                                <option value="${cate.categoryID}">${cate.categoryName}</option>    
                            </c:if>
                            <c:if test="${requestScope.CATEGORY == cate.categoryID}">
                                <option value="${cate.categoryID}" selected="">${cate.categoryName}</option>    
                            </c:if>
                        </c:forEach>
                    </c:if>
                </select>
                <c:if test="${ROLE == 'ADMIN'}">
                    <select name="cbbStatus" required>
                        <c:if test="${requestScope.STATUS == null}">
                            <option value="1" selected>ACTIVATED</option>
                            <option value="0">DEACTIVATED</option>
                        </c:if>
                        <c:if test="${requestScope.STATUS != null}">
                            <c:if test="${requestScope.STATUS == '1'}">
                                <option value="1" selected>ACTIVATED</option>
                                <option value="0">DEACTIVATED</option>
                            </c:if>
                            <c:if test="${requestScope.STATUS == '0'}">
                                <option value="1">ACTIVATED</option>
                                <option value="0" selected>DEACTIVATED</option>
                            </c:if>
                        </c:if>
                    </select>
                </c:if>
                <input type="submit" value="Search" name="btAction"/>
            </form>
        </div>
        <c:if test="${ERROR != null}">
            <div class="container-fulid">
                <h1 style="text-align: center;color: red;">${ERROR}</h1>
            </div>
        </c:if>
        <div class="container-fulid" style="margin-left: 2%">
            <div class="row">
                <div class="col-10" style="height: auto;">
                    <c:if test="${ROLE == 'ADMIN'}">
                        <a href="create.jsp" class="buttonlink">CREATE</a>
                    </c:if>
                    <form action="DispatchServlet">
                        <c:forEach var="product" items="${LISTPRODUCT}" varStatus="counter">
                            <div class="col-sm-6" style="position: relative;float: left;font-family: 'Times New Roman', Times, serif; background-color: white;border-style: inset; height: 350px;width: 300px;background: linear-gradient(to bottom right, #ffccff -9%, #ffffff 100%);">
                                <input type="hidden" name="txtProductID" value="${product.productID}" />
                                <div class="container">
                                    <div style="display: flex;justify-content: center;">
                                        <img src="${product.image}" alt="${product.name}" style="width: 80%;max-height: auto;"/>
                                    </div>
                                    <div class="middle">
                                        <c:if test="${ROLE == null}">
                                            <div class="text">Login to use</div>
                                        </c:if>
                                        <c:if test="${ROLE == 'ADMIN'}">
                                            <a href="DisplayUpdateProduct?pageNo=${PAGENO}&txtProductID=${product.productID}&txtSearchName=${requestScope.SEARCHNAME}&txtMinPrice=${requestScope.MINPRICE}&txtMaxPrice=${requestScope.MAXPRICE}&cbbCategory=${requestScope.CATEGORY}&cbbStatus=${requestScope.STATUS}" class="buttonlink">UPDATE</a>
                                            <c:if test="${requestScope.STATUS != '0'}">
                                                <a href="DeleteProductServlet?pageNo=${PAGENO}&txtProductID=${product.productID}&txtSearchName=${requestScope.SEARCHNAME}&txtMinPrice=${requestScope.MINPRICE}&txtMaxPrice=${requestScope.MAXPRICE}&cbbCategory=${requestScope.CATEGORY}&cbbStatus=${requestScope.STATUS}" class="buttonlink" onclick="return confirm('Are u sure about that?')">DELETE</a>
                                            </c:if>
                                        </c:if>
                                        <c:if test="${ROLE == 'USER'}">
                                            <a href="AddToCartServlet?pageNo=${PAGENO}&txtProductID=${product.productID}&txtSearchName=${requestScope.SEARCHNAME}&txtMinPrice=${requestScope.MINPRICE}&txtMaxPrice=${requestScope.MAXPRICE}&cbbCategory=${requestScope.CATEGORY}&cbbStatus=${requestScope.STATUS}" class="buttonlink">ADD</a>
                                        </c:if>
                                    </div>
                                </div>
                                <div style="position: absolute; bottom: 5%; left: 3%; width: 100%">
                                    Name: ${product.name}<br/>
                                    Description: ${product.description}<br/>
                                    Price: ${product.price} USD<br/>
                                    Quantity: ${product.quantity}<br/>
                                </div>
                            </div>
                        </c:forEach>
                    </form>
                </div>
                <div class="col-2">
                    <c:if test="${ROLE == 'USER'}">
                        <div class="position-absolute" style="top: 8%">
                            <div class="position-fixed" style="width: 100%;">
                                <div class="row" style="height: 100%;width: 15%;margin-right: 1%;border-style: solid;font-weight: 600;background-color: white;background: linear-gradient(to bottom right, #ffccff -9%, #ffffff 100%);">
                                    <p style="text-align: center">
                                        🐧<font color="#73264d">Favorites</font> Product🐧
                                    </p>
                                    <c:forEach var="suggest" items="${SUGGESTLIST}">
                                        <form action="DispatchServlet">
                                            <div style="text-align: center;border-style: inset;">
                                                ${suggest.name}<br/>
                                                <div class="container">
                                                    <div style="display: flex;justify-content: center;">
                                                        <img src="${suggest.image}" alt="${suggest.name}" style="max-width: 70%;height: auto;"/>
                                                    </div>
                                                    <div class="middle">
                                                        <a href="AddToCartServlet?pageNo=${PAGENO}&txtProductID=${suggest.productID}&txtSearchName=${requestScope.SEARCHNAME}&txtMinPrice=${requestScope.MINPRICE}&txtMaxPrice=${requestScope.MAXPRICE}&cbbCategory=${requestScope.CATEGORY}&cbbStatus=${requestScope.STATUS}" class="buttonlink">ADD</a>
                                                    </div>
                                                </div>
                                                ${suggest.price} USD<br/>
                                                Times: ${suggest.maxquantity} ❤️‍🔥<br/>
                                            </div>
                                        </form>
                                    </c:forEach>
                                </div>
                                <div class="row" style="height: 100%;width: 15%;margin-right: 1%;border-style: solid;font-weight: 600;background-color: white;background: linear-gradient(to top left, #ffccff -9%, #ffffff 100%);">
                                    <c:if test="${ATTACHLIST != null}">
                                        <p style="text-align: center;width:100%;">
                                            <font color="green">RECOMMEND</font> Product👍👍👍
                                        </p>
                                        <c:forEach var="attach" items="${ATTACHLIST}">
                                            <form action="DispatchServlet">
                                                <div style="text-align: center;border-style: inset;">
                                                    ${attach.name}<br/>
                                                    <div class="container">
                                                        <div style="display: flex;justify-content: center;">
                                                            <img src="${attach.image}" alt="${attach.name}" style="max-width: 70%;height: auto;"/>
                                                        </div>
                                                        <div class="middle">
                                                            <a href="AddToCartServlet?pageNo=${PAGENO}&txtProductID=${attach.productID}&txtSearchName=${requestScope.SEARCHNAME}&txtMinPrice=${requestScope.MINPRICE}&txtMaxPrice=${requestScope.MAXPRICE}&cbbCategory=${requestScope.CATEGORY}&cbbStatus=${requestScope.STATUS}" class="buttonlink">ADD</a>
                                                        </div>
                                                    </div>
                                                    ${attach.price} USD<br/>                                                    
                                                </div>
                                            </form>
                                        </c:forEach>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
        <div class="container-fulid">
            <div class="row">
                <div style="text-align: center;">
                    <c:if test="${requestScope.SEARCHNAME == null && requestScope.MINPRICE == null && requestScope.MAXPRICE == null && requestScope.CATEGORY == null && requestScope.STATUS == null}">
                        <c:forEach var="maxpage" begin="1" end="${MAXPAGE}">
                            <a href="DisplayProductServlet?pageNo=${maxpage}" class="myButton">${maxpage}</a>
                        </c:forEach>
                    </c:if>
                    <c:if test="${requestScope.SEARCHNAME != null || requestScope.MINPRICE != null || requestScope.MAXPRICE != null || requestScope.CATEGORY != null || requestScope.STATUS != null}">
                        <c:forEach var="maxpage" begin="1" end="${MAXPAGE}">
                            <a href="SearchProductServlet?pageNo=${maxpage}&txtSearchName=${requestScope.SEARCHNAME}&txtMinPrice=${requestScope.MINPRICE}&txtMaxPrice=${requestScope.MAXPRICE}&cbbCategory=${requestScope.CATEGORY}&cbbStatus=${requestScope.STATUS}" class="myButton">${maxpage}</a>
                        </c:forEach>
                    </c:if>
                </div>
            </div>
        </div>
    </body>
</html>
