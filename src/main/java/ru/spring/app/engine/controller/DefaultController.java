package ru.spring.app.engine.controller;

import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.spring.app.engine.api.response.InitResponse;

@Api
@Controller
@RequestMapping("/")
public class DefaultController {

    private final InitResponse initResponse;

    @Autowired
    public DefaultController(@RequestParam InitResponse initResponse) {
        this.initResponse = initResponse;
    }

    public String index() {
        System.out.println(initResponse.getTitle());
        return "index";
    }
}
