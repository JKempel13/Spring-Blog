package com.codeup.springblog.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class RollDiceController {


    @GetMapping("/roll-dice")
    public String showDiceForm() {
        return "rollDice";
    }

    @GetMapping("/roll-dice/{n}")
    public String numGuessed(@PathVariable int n, Model vModel) {

        int random = (int)(Math.random() * 6 + 1);

        if (n != random){
            vModel.addAttribute("random","Wrong! This is the rolled number: " + random);
        } else {
            vModel.addAttribute("dice",  "Your number: " + n + " and the random number: " + random + " match!");
        }

        return "rollDice";
    }
}
