package com.steelrain.springboot.lilac.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/member")
public class MemberController {

    @GetMapping("/login")
    public String loginForm(){
        return "member/login";
    }

    @GetMapping("/registration")
    public String registrationForm(){
        return "member/registration";
    }
}
