package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberMapper memberMapper;

    @GetMapping("/")
    public String index(){
        int count = memberMapper.getMemberCount();
        System.out.println("HomeController.index");
        System.out.println("---> getMemberCount :" + count);
        return "index";
    }
}
