package com.lanyuan.testspringboot.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class JspController {

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "/login";
    }

    @RequestMapping("/toHome")
    public String toHome(){
        return "/home";
    }

    @RequestMapping("/toIndex")
    public String toIndex(){
        return "/index";
    }

    @RequestMapping("/toUserAdd")
    public String toUserAdd(){
        return "/admin/add";
    }

    @RequestMapping("/toUserEdit")
    public String toUserEdit(){
        return "/admin/edit";
    }

    @RequestMapping("/toUserList")
    public String toUserList(){
        return "/admin/list";
    }

    @RequestMapping("/toUserResources")
    public String toUserResources(){
        return "/admin/resources";
    }

    @RequestMapping("/toRoleAdd")
    public String toRoleAdd(){
        return "/role/add";
    }

    @RequestMapping("/toRoleList")
    public String toRoleList(){
        return "/role/list";
    }

    @RequestMapping("/toRegister")
    public String toRegister(){
        return "/register";
    }

}
