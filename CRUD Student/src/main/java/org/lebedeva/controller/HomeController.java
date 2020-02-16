package org.lebedeva.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/error")
    public String error() {
        return "error_page";
    }

    @GetMapping("/favicon.ico")
    String favicon() {
        return "forward:/static/images/icon.png";
    }
}
