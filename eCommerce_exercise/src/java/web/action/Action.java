package web.action;

import javax.servlet.http.*;

public abstract class Action {

    public abstract void perform(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}