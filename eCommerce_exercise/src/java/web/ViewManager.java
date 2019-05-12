package web;

import javax.servlet.*;
import javax.servlet.http.*;

public class ViewManager {

    public static void nextView(HttpServletRequest req, HttpServletResponse resp, String jsp) {
        
        try {
            RequestDispatcher dispatcher = req.getRequestDispatcher(jsp);
            if (dispatcher != null) {
                dispatcher.forward(req, resp);
            }
        } catch (Exception e) {
            System.out.println("Error at dispatcher in nextView " + e.toString());
        }
    }
}
