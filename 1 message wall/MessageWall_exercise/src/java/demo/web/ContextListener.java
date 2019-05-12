package demo.web;

import demo.impl.MessageWall_and_RemoteLogin_Impl;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Web application lifecycle listener.
 *
 * @author juanluis
 */
public class ContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        context.setAttribute("remoteLogin", new MessageWall_and_RemoteLogin_Impl());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        
    }
}
