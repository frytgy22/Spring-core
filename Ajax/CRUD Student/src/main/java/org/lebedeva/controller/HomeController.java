package org.lebedeva.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/favicon.ico")
    String favicon() {
        return "forward:/static/images/icon.png";
    }
}
