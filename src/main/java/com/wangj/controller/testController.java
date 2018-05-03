package com.wangj.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/test")
public class testController {

    @GetMapping
    public ModelAndView test(Model model){
        model.addAttribute("title","helloworld!");
        return new ModelAndView("test","model",model);
    }
}