package ru.spring.app.engine.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.spring.app.engine.api.response.InitResponse;

@Controller
@RequestMapping("/")
public class DefaultController {

    private final InitResponse initResponse;

    public DefaultController(InitResponse initResponse) {
        this.initResponse = initResponse;
    }

    public String index() {
        System.out.println(initResponse.getTitle());
        return "index";
    }
}
