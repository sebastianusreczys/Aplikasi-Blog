package co.id.sebastianus.controller;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CostomErrorController implements ErrorController {


    @RequestMapping("/error")
    public String handleError() {
        // Mengembalikan nama view error.html
        return "/404";
    }

    public String getErrorPath() {
        return "/404";
    }
}
