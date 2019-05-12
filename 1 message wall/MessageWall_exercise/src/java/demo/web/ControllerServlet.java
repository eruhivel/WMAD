package demo.web;

import demo.spec.RemoteLogin;
import demo.spec.UserAccess;
import java.io.IOException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ControllerServlet extends HttpServlet {

    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        process(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        process(request, response);
    }

    protected void process(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String view = perform_action(request);
        forwardRequest(request, response, view);
    }

    protected String perform_action(HttpServletRequest request)
            throws IOException, ServletException {

        String serv_path = request.getServletPath();
        HttpSession session = request.getSession();

        if (serv_path.equals("/login.do")) {
            RemoteLogin login = getRemoteLogin();
            UserAccess user = login.connect(request.getParameter("user"), request.getParameter("password"));
            // Do login, if OK go to page, else go to error (Change true for w/e)
            if (user != null) {
                session.setAttribute("useraccess", user);
                return "/view/wallview.jsp";
            } else {
                return "/error-no-user_access.html";
            }
        } else if (serv_path.equals("/put.do")) {
            if (session.getAttribute("useraccess") != null) {
                UserAccess user = (UserAccess) session.getAttribute("useraccess");
                user.put(request.getParameter("msg"));
                return "/view/wallview.jsp";
            } else {
                return "/login.html";
            }
        } else if (serv_path.equals("/refresh.do")) {
            if (session.getAttribute("useraccess") != null) {
                return "/view/wallview.jsp";
            } else {
                return "/login.html";
            }
        } else if (serv_path.equals("/delete.do")) {
            if (session.getAttribute("useraccess") != null) {
                UserAccess user = (UserAccess) session.getAttribute("useraccess");
                user.delete(Integer.parseInt(request.getParameter("index")));
                return "/view/wallview.jsp";
            } else {
                return "/login.html";
            }
        } else if (serv_path.equals("/logout.do")) {
            return "/goodbye.html";
        } else {
            return "/error-bad-action.html";
        }
    }

    public RemoteLogin getRemoteLogin() {
        return (RemoteLogin) getServletContext().getAttribute("remoteLogin");
    }

    public void forwardRequest(HttpServletRequest request, HttpServletResponse response, String view)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(view);
        if (dispatcher == null) {
            throw new ServletException("No dispatcher for view path '" + view + "'");
        }
        dispatcher.forward(request, response);
    }
}
