package com.steelrain.springboot.lilac.controller;

import com.steelrain.springboot.lilac.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminController {

    private final AdminService m_adminService;


    @GetMapping("/admin-menu")
    public String adminForm(){

        return "/admin/admin-menu";
    }
}
