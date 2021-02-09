package top.happubull.happybullspringsecurity.filter;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import top.happubull.happybullspringsecurity.exception.VerificationCodeException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;

import static top.happubull.happybullspringsecurity.constant.BusContstant.CAPTCHA;

@Slf4j
public class VerificationCodeFilter extends OncePerRequestFilter {

    private AuthenticationFailureHandler authenticationFailureHandler = new AuthenticationFailureHandler(){
        @Override
        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            JSONObject resp = new JSONObject();
            resp.put("error_code","401");
            resp.put("name",exception.getClass());
            resp.put("message",exception.getMessage());
            PrintWriter writer = response.getWriter();
            writer.write(resp.toJSONString());
        }
    };

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("URI: {}", request.getRequestURI());
        log.info("URL: {}", request.getRequestURL().toString());
        if (!"/auth/form".equals(request.getRequestURI())) {
            filterChain.doFilter(request, response);
        } else {

            try {
                verificationCode(request);
                filterChain.doFilter(request, response);
            }catch (VerificationCodeException e){
                authenticationFailureHandler.onAuthenticationFailure(request,
                        response,
                        e);
            }


        }
    }

    private void verificationCode(HttpServletRequest request) throws VerificationCodeException {
        String requestCode = request.getParameter(CAPTCHA);
        HttpSession session = request.getSession();
        String saveCode = (String) session.getAttribute(CAPTCHA);
        if (!StringUtils.hasLength(saveCode)) {
            session.removeAttribute(CAPTCHA);
        }
        if (!StringUtils.hasLength(requestCode)
                || !StringUtils.hasLength(saveCode)
                || !requestCode.equals(saveCode)) {
            throw new VerificationCodeException();
        }
    }
}
