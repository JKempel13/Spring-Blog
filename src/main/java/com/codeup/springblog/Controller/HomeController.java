package com.codeup.springblog.Controller;

import com.codeup.springblog.Ad;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class HomeController {

    @GetMapping("/")
    public String index(Model vModel) {

        ArrayList<Ad> adsList = new ArrayList<>();

        adsList.add(new Ad(1,"First ad","new"));
        adsList.add(new Ad(2,"Second ad","new"));
        adsList.add(new Ad(3,"Third ad","used"));

        vModel.addAttribute("ads", adsList);

        return "home";
    }
}
