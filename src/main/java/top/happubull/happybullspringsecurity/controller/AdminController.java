package top.happubull.happybullspringsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/api")
public class AdminController {
    @RequestMapping("/hello")
    public String admin(){
        return "hello admin ";
    }
}
