/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.action;

import cart.ShoppingCart;
import entity.Product;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.ProductModel;
import web.ViewManager;

/**
 *
 * @author enric
 */
public class updatecartAction extends Action {

    ProductModel productModel;

    public updatecartAction(ProductModel productModel) {
        this.productModel = productModel;
    }

    @Override
    public void perform(HttpServletRequest req, HttpServletResponse resp) {    
        // We will check if the input is empty, if it is we will ignore the update       
        if (!req.getParameter("quantity").equals("")) {
            HttpSession session = req.getSession();
            ShoppingCart sessionCart = (ShoppingCart) session.getAttribute("cart");

            Product product = productModel.retriveProduct(req.getParameter("productId"));
            String quantity = req.getParameter("quantity");

            sessionCart.update(product, quantity);
        }
        
        ViewManager.nextView(req, resp, "/view/cart.jsp");
    }
}
