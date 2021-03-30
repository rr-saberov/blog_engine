package ru.spring.app.engine.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Api
@Controller
@RequestMapping("/")
public class DefaultController {
    public String index() {
        return "index";
    }
}
