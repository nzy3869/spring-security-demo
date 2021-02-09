package top.happubull.happybullspringsecurity.filter;

import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static top.happubull.happybullspringsecurity.constant.BusContstant.CAPTCHA;

public class MyWebAuthenticationDetails extends WebAuthenticationDetails {

    private String requestCode;
    private String sessionCode;

    public MyWebAuthenticationDetails(HttpServletRequest request) {
        super(request);
        requestCode = request.getParameter(CAPTCHA);
        HttpSession session = request.getSession();
        sessionCode = (String) session.getAttribute(CAPTCHA);
    }

    public String getRequestCode() {
        return requestCode;
    }

    public String getSessionCode() {
        return sessionCode;
    }
}
