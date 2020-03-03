package app.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("app")
public class MainController {

    @GetMapping("/{name}")
    public String greeting(@PathVariable String name) {
        return "Hi!! " + name;
    }
}

