package com.netcracker.controller;

import com.netcracker.model.Greeting;
import com.netcracker.model.MyUploadForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.File;
import java.io.IOException;

@Controller
public class GreetingController {

    @GetMapping("/greeting")
    public String greetingForm(Model model) {
        model.addAttribute("greeting", new Greeting());
        model.addAttribute("myUploadForm", new MyUploadForm());
        return "greeting";
    }

    @GetMapping("/search")
    public String searchByName(Model model) {
        model.addAttribute("greeting", new Greeting());
        return "search";
    }

    @GetMapping("/find")
    public String find(Model model) {
        model.addAttribute("find", new Greeting());
        return "find";
    }

    @PostMapping("/search")
    public String searchByName(@ModelAttribute Greeting greeting, Model model) throws IOException {

        Greeting tempGreeting =  new Greeting("0", "0",
                "0", 0,0,"0", "0");
        Greeting greet = greeting.searchByName();
        if(greet.equals(tempGreeting)){
            return "sorry";
        } else {
            model.addAttribute("greeting", greet);
            return "find";
        }
    }

    @PostMapping("/fromExcel")
    public String greetingFormExcel(Model model) throws IOException{
        Greeting greet = new Greeting();
        greet.fromExcel(new File("your.xlsx"));
        model.addAttribute("greeting", greet);
        model.addAttribute("myUploadForm", new MyUploadForm());
        return "greeting";
    }

    @GetMapping("/sorry")
    public String sorry() {
        return "sorry";
    }

    @PostMapping("/toExcel")
    public String greetingToExcel(@ModelAttribute Greeting greeting, Model model) throws IOException{
        greeting.toExcel();
        model.addAttribute("greeting", greeting);
        model.addAttribute("myUploadForm", new MyUploadForm());
        return "greeting";
    }
}