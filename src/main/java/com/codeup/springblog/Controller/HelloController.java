package com.codeup.springblog.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;

@Controller
public class HelloController {

//    @GetMapping("/")
//    public String index() {
//        return "home";
//    }

    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/hello/{name}")
    public String sayHello(@PathVariable String name, Model viewModel) {
        viewModel.addAttribute("name", name);
        return "hello";
    }

    @GetMapping("/join")
    public String showJoinForm() {
        return "join";
    }

    @PostMapping("/join")
    public String joinCohort(@RequestParam(name = "cohort") String cohort, Model model) {

        ArrayList<String> students = new ArrayList<>();

        students.add("Jason");
        students.add("Chris");
        students.add("Raymond");

        model.addAttribute("cohort", "Welcome to " + cohort + "!");
        model.addAttribute("students", students);
        return "join";
    }
}
