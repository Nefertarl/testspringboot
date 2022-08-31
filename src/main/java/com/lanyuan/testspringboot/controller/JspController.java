package com.lanyuan.testspringboot.controller;

import com.github.pagehelper.PageInfo;
import com.lanyuan.testspringboot.pojo.User;
import com.lanyuan.testspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class JspController {

    @Autowired
    UserService userService;

    @RequestMapping("/toLogin")
    public String toLogin(){
        return "/login";
    }

    @RequestMapping("/toRegister")
    public String toRegister(){
        return "/register";
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
    public String toUserList(@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize, Map map){
        PageInfo<User> pageInfo = userService.getPage(pageNum,pageSize);
        System.out.println(pageInfo.getList());
        map.put("items",pageInfo);
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

    @RequestMapping("/logout")
    public String logout(){
        return "/login";
    }

}
