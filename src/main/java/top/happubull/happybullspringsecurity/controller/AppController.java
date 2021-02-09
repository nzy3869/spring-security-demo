package top.happubull.happybullspringsecurity.controller;

import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static top.happubull.happybullspringsecurity.constant.BusContstant.CAPTCHA;

@RestController
@RequestMapping("/app/api")
public class AppController {

    @Autowired
    private Producer captchaProducer;

    @RequestMapping("/")
    public String app(){
        return "hello app ";
    }

       @GetMapping("/captcha.jpg")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("image/jpeg");
        String capText = captchaProducer.createText();
        request.getSession().setAttribute(CAPTCHA,capText);
        BufferedImage bi = captchaProducer.createImage(capText);
        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(bi,"jpg",out);
        try {
            out.flush();
        }finally {
            out.close();
        }
    }
}
