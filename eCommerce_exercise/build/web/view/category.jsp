<%-- 
    Document   : category
    Created on : 12/03/2019, 12:15:41
    Author     : enric
--%>

<%@page import="cart.ShoppingCart"%>
<%@page import="entity.Category"%>
<%@page import="entity.Product"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="windows-1252"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Products of <%=((Category) request.getAttribute("category")).getName()%></h1>

        <p>
            <img src="img/cart.gif">
            <%
                if (null == session.getAttribute("cart")) {%>
                0 Items
            <%} else {%>
            <%=((ShoppingCart) session.getAttribute("cart")).getNumberOfItems()%> Items
                <a href="${pageContext.request.contextPath}/viewcart.do">View Cart</a>
                <br/>
            <%}%>

        </p>      

        <table width="70%" border="1" bordercolordark="#000000" bordercolorlight="#FFFFFF" cellpadding="3" cellspacing="0">

            <tr> <font size="2" face="Verdana">

            <td width="14%" valign="center" align="middle">
                <table width="100%" border="1" bordercolordark="#000000" bordercolorlight="#FFFFFF" cellpadding="3" cellspacing="0">

                    <tr>
                        <%
                            List<Category> categories = (List<Category>) request.getAttribute("categories");
                            for (Category category : categories) {

                        %>
                        <td width="14%" valign="center" align="middle">
                            <a href="category.do?categoryid=<%=category.getId()%>">
                                <%=category.getName()%>
                            </a>
                        </td>
                    </tr>
                    <% }%>
                </table>
            </td>    
            <td>
                <table width="100%" border="1" bordercolordark="#000000" bordercolorlight="#FFFFFF" cellpadding="3" cellspacing="0">
                    <%
                        List<Product> products = (List<Product>) request.getAttribute("products");
                        for (Product product : products) {
                    %>

                    <tr>
                        <td width="25%" valign="center" align="middle">
                            <img src="img/products/<%=product.getName()%>.png">
                        </td>
                        <td width="25%" valign="center" align="middle">
                            <strong><%=product.getName()%></strong><br>
                            <%=product.getDescription()%>
                        </td>
                        <td width="25%" valign="center" align="middle">
                            <%=product.getPrice()%> &euro;
                        </td>

                        <td width="25%" valign="center" align="middle">
                            <form action="${pageContext.request.contextPath}/neworder.do?categoryid=<%=((Category) request.getAttribute("category")).getId()%>" method="post">
                                <button type="submit" name="addToCart" value="<%=product.getId()%>">add to cart</button>
                            </form>
                        </td>
                    </tr>
                    <% }%>
                </table>
            </td>
            </font></tr>
    </table>
</body>
</html>
