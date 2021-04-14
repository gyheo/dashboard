package com.dashboard.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WelcomeController {
    @GetMapping("/helloworld")
    // @RequestMapping(value="/hellworld", method=@RequestMethod.GET)
    public String welcome(String name, Model model) {
        System.out.println("name : " + name);
        // System.out.println("User : " + user);
        model.addAttribute("name", name);
        // model.addAttribute("age", age);
        return "welcome";
    }
}
