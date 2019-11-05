package com.codeup.springblog.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class PostController {

    @GetMapping("/posts")
    @ResponseBody
    public String index() {
        return "posts index page";
    }

    @GetMapping("/posts/{id}")
    @ResponseBody
    public String show(@PathVariable long id) {
        return "view ad id = " + id;
    }

    @GetMapping("/posts/create")
    @ResponseBody
    public String showForm() {
        return "view the form for creating an ad";
    }

    @PostMapping("/posts/create")
    @ResponseBody
    public String create(@RequestParam String title, @RequestParam String body) {
        System.out.println("title = " + title);
        System.out.println("body = " + body);
        return "create a new ad";
    }
}
