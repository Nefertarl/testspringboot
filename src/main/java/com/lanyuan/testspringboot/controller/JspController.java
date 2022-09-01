package com.lanyuan.testspringboot.controller;

import com.github.pagehelper.PageInfo;
import com.lanyuan.testspringboot.pojo.Role;
import com.lanyuan.testspringboot.pojo.User;
import com.lanyuan.testspringboot.service.RoleService;
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

    @Autowired
    RoleService roleService;

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
    public String toUserList(User user,@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize, Map map){
        PageInfo<User> pageInfo = userService.getPage(user,pageNum,pageSize);
        System.out.println(pageInfo.getList());
        map.put("search2",user.getAccount());
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

    @RequestMapping("/toRoleEdit")
    public String toRoleEdit(Integer id,Map map){
        Role role = roleService.getById(id);
        map.put("role",role);
        return "/role/edit";
    }

    @RequestMapping("/toRoleList")
    public String toRoleList(Role role,@RequestParam(defaultValue = "1") Integer pageNum,
                             @RequestParam(defaultValue = "5") Integer pageSize, Map map){
        PageInfo<Role> pageInfo = roleService.getPage(role,pageNum,pageSize);
        map.put("search",role.getRolename());
        map.put("items",pageInfo);
        return "/role/list";
    }

    @RequestMapping("/logout")
    public String logout(){
        return "/login";
    }

}
