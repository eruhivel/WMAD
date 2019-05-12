<%-- 
    Document   : category
    Created on : 12/03/2019, 12:15:41
    Author     : enric
--%>

<%@page import="java.util.Locale"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.math.RoundingMode"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="cart.ShoppingCartItem"%>
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
        <%ShoppingCart cart = (ShoppingCart) session.getAttribute("cart");%>
        <%
            NumberFormat nf = NumberFormat.getNumberInstance(Locale.UK);
            DecimalFormat df = new DecimalFormat("#.##");
            df.setRoundingMode(RoundingMode.DOWN);
            String totalAmount = df.format(cart.getTotal());
        %>
        <p>
            <img src="img/cart.gif">
            <%=cart.getNumberOfItems()%> Items
        </p>   
        <h2>Your shopping cart contains <%=cart.getNumberOfItems()%> items.</h2>
        <a href="clearcart.do">Clear cart</a> <br/>
        <a href="${pageContext.request.contextPath}/">Continue shopping</a> <br/>
        <form action="https://www.paypal.com/cgi-bin/webscr" method="post">
            <input type="hidden" name="cmd" value="_xclick">
            <input type="hidden" name="business" value="you@youremail.com">
            <input type="hidden" name="item_name" value="Shopping Cart">
            <input type="hidden" name="currency_code" value="EUR">
            <input type="hidden" name="amount" value=<%=totalAmount%>>
            <input type="image" src="http://www.paypal.com/es_XC/i/btn/x-click-but01.gif" name="submit" alt="Make payments with PayPal - it's fast, free and secure!">
        </form>

        <table width="100%" border="1" bordercolordark="#000000" bordercolorlight="#FFFFFF" cellpadding="3" cellspacing="0">
            <tr> <font size="2" face="Verdana">
            <th width="25%">Product</th>
            <th width="25%">Name</th>
            <th width="25%">Price</th>
            <th width="25%">Quantity</th>
        </tr>
        <%
            for (ShoppingCartItem item : cart.getItems()) {
        %>
        <tr>
            <td width="25%" valign="center" align="middle">
                <img src="img/products/<%= item.getProduct().getName()%>.png"></td>
            <td width="25%" valign="center" align="middle">
                <%=item.getProduct().getDescription()%></td>
            <td width="25%" valign="center" align="middle">
                <%=item.getProduct().getPrice()%> &euro;</td>
            <td width="25%" valign="center" align="middle">
                <form action="${pageContext.request.contextPath}/updatecart.do" method="post">
                    <input name="quantity"  value="<%=item.getQuantity()%>"></input>
                    <button type="submit" name="productId" value="<%=item.getProduct().getId()%>">Update</button>
                </form>
            </td>
            <%}%>
            </font></tr>
    </table>
    <p>Total Amount <%=totalAmount%> &euro;</p>
</body>
</html>
