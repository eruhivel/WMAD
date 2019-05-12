/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web.action;

import cart.ShoppingCart;
import entity.Category;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.CategoryModel;
import model.ProductModel;
import web.ViewManager;

/**
 *
 * @author enric
 */
public class neworderAction extends Action {

    ProductModel productModel;
    CategoryModel categoryModel;

    public neworderAction(CategoryModel categoryModel, ProductModel productModel) {
        this.categoryModel = categoryModel;
        this.productModel = productModel;
    }

    @Override
    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        HttpSession session = req.getSession();

        if (req.getParameter("addToCart") != null) {
            if (null == session.getAttribute("cart")) {
                ShoppingCart cart = new ShoppingCart();
                session.setAttribute("cart", cart);
            }
            ShoppingCart sessionCart = (ShoppingCart) session.getAttribute("cart");
            sessionCart.addItem(productModel.retriveProduct(req.getParameter("addToCart")));
        }

        Category category = categoryModel.retriveCategory(req.getParameter("categoryid"));
        req.setAttribute("category", category);
        req.setAttribute("categories", categoryModel.retrieveAll());
        req.setAttribute("products", productModel.retriveProducts(category));

        ViewManager.nextView(req, resp, "/view/category.jsp");
    }
}
