package util;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.transaction.UserTransaction;
import model.CategoryModel;
import model.ProductModel;


/**
 *
 * @author  juanluis
 * @version
 *
 * Web application lifecycle listener.
 */
public class ContextListener implements ServletContextListener, HttpSessionListener {

    @Resource
    private UserTransaction utx;
    @PersistenceContext
    private EntityManager em;

    public void contextInitialized(ServletContextEvent event) {
        try {
            ServletContext context = event.getServletContext();

            CategoryModel categoryModel = new CategoryModel(em, utx);
            ProductModel productModel = new ProductModel(em, utx);
            
            context.setAttribute("categoryModel", categoryModel);
            context.setAttribute("productModel", productModel);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent evt) {

    }

    public void sessionCreated(HttpSessionEvent arg0) {

        arg0.getSession().setAttribute("id_session", "prove");

    }

    public void sessionDestroyed(HttpSessionEvent arg0) {

        arg0.getSession().getAttribute("id_session");
        
    }
}
