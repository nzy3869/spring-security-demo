package top.happubull.happybullspringsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user/api")
public class UserController {
    @RequestMapping("/")
    public String user(){
        return "hello user";
    }
}
