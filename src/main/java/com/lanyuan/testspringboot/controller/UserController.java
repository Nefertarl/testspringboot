package com.lanyuan.testspringboot.controller;

import com.github.pagehelper.PageInfo;
import com.lanyuan.testspringboot.interfaces.R;
import com.lanyuan.testspringboot.pojo.User;
import com.lanyuan.testspringboot.service.UserService;
import com.lanyuan.testspringboot.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping("addUser")
    public R addUser(@RequestBody User user){
        user.setCreatetime(new Date());
        int i = userService.addUser(user);
        if(i>0){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //TODO: 后期需要完善

    @DeleteMapping("{id}")
    public R removeUser(@PathVariable Integer id){
      User user = new User();
      user.setId(id);
      user.setDel("1");
      int i = userService.removeById(user);
        if(i>0){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @GetMapping("findAll")
    public R findAllUser(){
        List<User> list = userService.findAll();
        return R.ok().data("items",list);
    }

    //TODO: 后期需要完善

    @GetMapping("pageListUser/{pageNum}/{pageSize}")
    public R pageListUser(@PathVariable Integer pageNum,@PathVariable Integer pageSize){
        PageInfo<User> pageInfo = userService.getPage(pageNum,pageSize);
        return R.ok().data("items",pageInfo.getList());
    }

    //TODO: 后期需要完善

    @GetMapping("getUser/{id}")
    public R getUser(@PathVariable Integer id){
        User user = userService.getById(id);
        return R.ok().data("user",user);
    }

    @PostMapping("updateUser")
    public R updateUser(@RequestBody User user){
        int i = userService.updateById(user);
        if(i>0){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @GetMapping("getCode")
    public void getCode(HttpSession session, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CodeUtil.processRequest(req,resp);
        String code2 = (String) session.getServletContext().getAttribute("randomCode2");
        System.err.println("application存的验证码是["+code2+"]");
    }




}
