package top.happubull.happybullspringsecurity;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@SpringBootApplication
@MapperScan("top.happubull.happybullspringsecurity.dao")
public class HappybullSpringSecurityApplication {
    public static void main(String[] args) {
        SpringApplication.run(HappybullSpringSecurityApplication.class, args);
    }

}
