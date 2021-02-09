package top.happubull.happybullspringsecurity.configuration;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import top.happubull.happybullspringsecurity.filter.MyAuthenticationProvider;
import top.happubull.happybullspringsecurity.filter.VerificationCodeFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyAuthenticationProvider myAuthenticationProvider;

    @Autowired
    private AuthenticationDetailsSource<HttpServletRequest, WebAuthenticationDetails> myWebAuthenticationDetailsSource;


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /*
        根据角色控制不同的url访问
         */
        http.authenticationProvider(myAuthenticationProvider).authorizeRequests()
                .antMatchers("/admin/api/**").hasRole("ADMIN")
                .antMatchers("/user/api/**").hasRole("USER")
                .antMatchers("/app/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin().authenticationDetailsSource(myWebAuthenticationDetailsSource).loginPage("/myLogin.html")
                .loginProcessingUrl("/auth/form")
                .permitAll()
                .failureHandler((request, response, exception) -> {
                    response.setStatus(401);
                    response.setContentType("application/json;charset=UTF-8");
                    JSONObject resp = new JSONObject();
                    resp.put("error_code","401");
                    resp.put("name",exception.getClass());
                    resp.put("message",exception.getMessage());
                    PrintWriter writer = response.getWriter();
                    writer.write(resp.toJSONString());

                });
//        http.addFilterBefore(new VerificationCodeFilter(), UsernamePasswordAuthenticationFilter.class);

        /*
        // 表单校验
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll().and().csrf().disable();
         */

    }
}
