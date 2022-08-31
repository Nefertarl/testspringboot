package com.lanyuan.testspringboot.controller;

import com.github.pagehelper.PageInfo;
import com.lanyuan.testspringboot.interfaces.R;
import com.lanyuan.testspringboot.pojo.User;
import com.lanyuan.testspringboot.service.UserService;
import com.lanyuan.testspringboot.util.CodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
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

    @GetMapping("pageListUser")
    public R pageListUser(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "5") Integer pageSize){
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

    @GetMapping("login")
    public R login(String account, String password, String code, HttpSession session){
        ServletContext application = session.getServletContext();
        String ran2 =(String) application.getAttribute("randomCode2");
        User user = new User(account,password);
        if(ran2.equalsIgnoreCase(code)){
            User u = userService.login(user);
            if(u!=null){
                return R.ok().data("user",u);
            }else return R.error().data("error","账号密码输入错误");
        }else {
            return R.error().data("error","验证码输入错误");
        }

    }

    //注册验证-是否存在该账号
    @RequestMapping(value = "/checkAccount",produces = "text/html;charset=utf-8")
    public String checkAccount(String account){
        User u = userService.findByAcunt(account);
        if (u==null){
            return "账号可用";
        }else {
            return "账号不可用";
        }
    }

    @PostMapping("RegisterUser")
    public R RegisterUser(String account, String password, String code, HttpSession session){
        User user = new User(account,password);
        user.setCreatetime(new Date());
        ServletContext application = session.getServletContext();
        String ran2 =(String) application.getAttribute("randomCode2");
        if(ran2.equalsIgnoreCase(code)){
            int i = userService.addUser(user);
            if(i>0){
                return R.ok();
            }else{
                return R.error();
            }
        }else{
            return R.error().data("error","验证码输入错误");
        }
    }

}
