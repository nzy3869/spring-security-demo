package top.happubull.happybullspringsecurity.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class HelloworldController {

    private static final SimpleDateFormat SDF_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @RequestMapping("/")
    public String hello(){
        return "hello security "+SDF_YYYY_MM_DD.format(new Date());
    }

}
