package top.happubull.happybullspringsecurity.configuration;

import com.alibaba.fastjson.JSONObject;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import top.happubull.happybullspringsecurity.filter.VerificationCodeFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {



    @Override
    protected void configure(HttpSecurity http) throws Exception {

        /*
        根据角色控制不同的url访问
         */
        http.authorizeRequests()
                .antMatchers("/admin/api/**").hasRole("ADMIN")
                .antMatchers("/user/api/**").hasRole("USER")
                .antMatchers("/app/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .csrf().disable()
                .formLogin().loginPage("/myLogin.html")
                .loginProcessingUrl("/auth/form")
                .permitAll()
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
                        response.setStatus(401);
                        response.setContentType("application/json;charset=UTF-8");
                        JSONObject resp = new JSONObject();
                        resp.put("error_code","401");
                        resp.put("name",exception.getClass());
                        resp.put("message",exception.getMessage());
                    }
                });
        http.addFilterBefore(new VerificationCodeFilter(), UsernamePasswordAuthenticationFilter.class);

        /*
        // 表单校验
        http.authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .formLogin().permitAll().and().csrf().disable();
         */

    }
}
