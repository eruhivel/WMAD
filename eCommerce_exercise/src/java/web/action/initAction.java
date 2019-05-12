package web.action;

import javax.servlet.http.*;
import model.CategoryModel;
import web.ViewManager;

public class initAction extends Action {

    CategoryModel categoryModel;

    public initAction(CategoryModel categoryModel){
        this.categoryModel = categoryModel;
    }

    @Override
    public void perform(HttpServletRequest req, HttpServletResponse resp) {
        req.setAttribute("categories", categoryModel.retrieveAll());
        ViewManager.nextView(req, resp, "/view/init.jsp");
    }
}
