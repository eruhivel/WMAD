/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.action;

import cart.ShoppingCart;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import web.ViewManager;

/**
 *
 * @author enric
 */
public class clearcartAction extends Action {

    public clearcartAction() {
    }

    @Override
    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();
        ShoppingCart sessionCart = (ShoppingCart) session.getAttribute("cart");
        sessionCart.clear();
        ViewManager.nextView(req, resp, "/view/cart.jsp");
    }
}
