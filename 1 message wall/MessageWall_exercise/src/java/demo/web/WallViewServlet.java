package demo.web;

import demo.impl.Message_Impl;
import demo.spec.Message;
import demo.spec.UserAccess;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class WallViewServlet extends HttpServlet {
    
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
        
        HttpSession session = request.getSession();
        UserAccess conn = (UserAccess)session.getAttribute("useraccess");
        Message msg = conn.getLast();

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        out.println("<html><head><title>Message Wall</title></head><body>");
        out.println("<h3>User <em>"+conn.getUser()+"</em> &nbsp; &nbsp;");
        out.println("<a href=logout.do>[Close session]</a></h3>");
        
        out.println("<HR WIDTH=\"100%\" SIZE=\"2\">");
        
        out.println("<h2>Last message:</h2><pre>");
        out.println("<p>Sent by: <pre>"+msg.getOwner()+"</pre></p>");
        out.println("<p>Message: <pre>"+msg.getContent()+"</pre></p>");
        
        out.println("<HR WIDTH=\"100%\" SIZE=\"2\">");

        out.println("<form action=\"put.do\" method=POST>");
        out.print("New message:<input type=text name=msg size=10>");
        out.println("<input type=submit value=\"Send message\"></form>");
        
        out.println("<HR WIDTH=\"100%\" SIZE=\"2\">");

        out.println("<form action=\"refresh.do\" method=POST>");
        out.println("<input type=submit value=\"Refresh last message\"></form>");

        out.println("</body></html>");
    }
}
