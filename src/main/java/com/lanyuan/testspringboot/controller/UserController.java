package com.lanyuan.testspringboot.controller;

import com.github.pagehelper.PageInfo;
import com.lanyuan.testspringboot.interfaces.R;
import com.lanyuan.testspringboot.pojo.User;
import com.lanyuan.testspringboot.service.RoleService;
import com.lanyuan.testspringboot.service.UserService;
import com.lanyuan.testspringboot.util.CodeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    RoleService roleService;


    @PostMapping("/addUser")
    public R addUser(User user){

        //if( ( user.getAccount()==null || "".equals(user.getAccount()) ) || user.getPassword() == null || "".equals(user.getPassword())  || user.getName()==null || "".equals(user.getName()) || user.getPhone()==null || "".equals(user.getPhone()) || user.getEmail() == null || "".equals(user.getEmail()) ){
        if( ( user.getAccount()==null || "".equals(user.getAccount()) ) || user.getPassword() == null || "".equals(user.getPassword())   ){
            //return R.error().data("mess","你对象的参数为空,(请填上账号、密码、姓名、手机号、邮箱)才能新增成功,");
            return R.error().data("mess","你对象的参数为空,(请填上账号、密码)才能新增成功,");
        }else{
            User byAcunt = userService.findByAccunt2(user.getAccount());
            System.out.println(byAcunt);
            if(byAcunt==null){
                user.setCreatetime(new Date());

                System.out.println("新增的时候: "+user.getAccount());
                SimpleHash md5 = new SimpleHash(
                        "md5",
                        user.getPassword(),
                        user.getAccount()+"",
                        1024
                );
                user.setPassword(String.valueOf(md5));

                int i = userService.addUser(user);
                if(i>0){
                    return R.ok();
                }else{
                    return R.error();
                }
            }else{
                return R.error().data("mess","对象已存在");
            }
        }

    }


    @PutMapping("/updateUser")
    public R updateUser( User user){
        if(user.getId() == null){
            return R.error().data("mess","id的参数为空");
        }else{
            User byAcunt = userService.getById(user.getId());
            if(byAcunt==null){
                return R.error().data("mess","对象不存在");
            }else{
                if(user.getAccount() != null ||  user.getPassword() != null || user.getName()!=null || user.getPhone()!=null || user.getEmail() != null || user.getStatus() != null || user.getCreatetime() != null || user.getSex() != null){
                    if("".equals(user.getAccount()) || "".equals(user.getPassword()) || "".equals(user.getName())  || "".equals(user.getPhone()) || "".equals(user.getEmail()) || "".equals(user.getStatus()) || "".equals(user.getCreatetime()) || "".equals(user.getSex())){
                        return R.error().data("mess","对象的参数为空");
                    }else{
                        if( (  user.getSex() !=null && ( !("0".equals(user.getSex()) || "1".equals(user.getSex()) ) )  )  ||  (  user.getStatus()!=null  &&  ( !("1".equals(user.getStatus()) || "2".equals(user.getStatus())  ))  )  ){
                            return R.error().data("mess","你输入的值不对");
                        }else{
                            int i = userService.updateById(user);
                            if(i>0){
                                return R.ok();
                            }else{
                                return R.error();
                            }
                        }
                    }

                }else{
                    return R.error().data("mess","对象的参数为空");
                }
            }

        }
    }



    @DeleteMapping("/doDelUser")
    public R removeUser(Integer id){
        if(id == null){
            return R.error().data("mess","id的参数为空");
        }else{
            User user = new User();
            user.setId(id);
            user.setDel("1");

            //先删除原来的关系表
            roleService.removeRelation(id);

            int i = userService.removeById(user);
            if(i>0){
                return R.ok();
            }else{
                return R.error();
            }
        }
    }

    @DeleteMapping("/doBathDelUser")
    public R doBathDelUser(Integer[] ids){


        if(ids == null){
            return R.error().data("mess","请传入参数,数组不能为空");
        }else{

            System.out.println("长度为:"+ids.length);

            if(ids.length == 0){
                return R.error().data("mess","数组里面的元素不能为空");
            }

            for (int i = 0; i < ids.length; i++) {
                if(ids[i] == null){
                    return R.error().data("mess","id的值不能为空");
                }
            }

            int i = 1;

            for(Integer id : ids){
                User user = userService.getById(id);
                if(user!=null){
                    i++;
                }else{
                    i = -1;
                    break;
                }
            }

            if(i>0){

                for(Integer id : ids){
                    //先删除原来的关系表
                    roleService.removeRelation(id);
                }

                userService.doBathDelUser(ids);
                return R.ok();
            }else{
                return R.error();
            }

        }
    }

    @GetMapping("findAll")
    public R findAllUser(){
        List<User> list = userService.findAll();
        return R.ok().data("items",list);
    }


    @GetMapping("pageListUser")
    public R pageListUser(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "5") Integer pageSize){
        PageInfo<User> pageInfo = userService.getPage(pageNum,pageSize);
        return R.ok().data("items",pageInfo.getList());
    }

    //带条件的分页查询
    @GetMapping("/pageList")
    public R pageList(User user,@RequestParam(defaultValue = "1") Integer pageNum,
                      @RequestParam(defaultValue = "5") Integer pageSize){

        PageInfo<User> pageInfo = null;
        if(user.getAccount()==null || "".equals(user.getAccount())){
            pageInfo = userService.getPage(user,pageNum,pageSize);
            Map<String,Object> map = new HashMap<>();
            map.put("mess","查询所有的用户");
            map.put("items",pageInfo.getList());
            return R.ok().data(map);
        }else{
            pageInfo = userService.getPage(user,pageNum,pageSize);
            Map<String,Object> map = new HashMap<>();
            map.put("搜索的条件",user.getAccount());
            map.put("items",pageInfo.getList());
            return R.ok().data(map);
        }
    }

    @GetMapping("/getUser")
    public R getUser(Integer id){


        if(id == null){
            return R.error().data("mess","id的参数为空");
        }else{
            if(id instanceof Integer){
                User user = userService.getById(id);
                if(user!=null){
                    return R.ok().data("user",user);
                }else{
                    return R.error().data("mess","查不到这个对象");
                }
            }else{
                return R.error().data("mess","你传入的不是数值");
            }
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

        if(user.getAccount()==null|| "".equals(user.getAccount())){
            return R.error().data("mess","账号不可为空");
        }else{
            if("".equals(user.getPassword()) || user.getPassword() == null ){
                return R.error().data("mess","密码不可为空");
            }else{
                if( "".equals(code) || code == null){
                    return R.error().data("mess","验证码不可为空");
                }else{
                    if(ran2.equalsIgnoreCase(code)){
                        User u = userService.login(user);
                        if(u!=null){
                            return R.ok().data("user",u);
                        }else return R.error().data("error","登录失败");
                    }else {
                        return R.error().data("error","验证码输入错误");
                    }
                }
            }
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

        if(user.getAccount()==null|| "".equals(user.getAccount())){
            return R.error().data("mess","账号不可为空");
        }else{
            if("".equals(user.getPassword()) || user.getPassword() == null ){
                return R.error().data("mess","密码不可为空");
            }else{
                if( "".equals(code) || code == null){
                    return R.error().data("mess","验证码不可为空");
                }else{
                    User byAcunt = userService.findByAccunt2(user.getAccount());
                    System.out.println(byAcunt);
                    if(byAcunt==null){
                        if(ran2.equalsIgnoreCase(code)){
                            int i = userService.addUser(user);
                            if(i>0){
                                return R.ok();
                            }else{
                                return R.error();
                            }
                        }else{
                            return R.error().data("error","验证码错误");
                        }
                    }else{
                        return R.error().data("mess","账号已注册");
                    }
                }
            }
        }

    }

    @RequestMapping("/RegisterUserShiro")
    public R add(String account, String password, String code, HttpSession session){

        ServletContext application = session.getServletContext();
        String ran2 =(String) application.getAttribute("randomCode2");

        User user = new User(account,password);
        //当前时间,作为注册时间
        user.setCreatetime(new Date());


        if(user.getAccount()==null|| "".equals(user.getAccount())){
            return R.error().data("mess","账号不可为空");
        }else{
            if("".equals(user.getPassword()) || user.getPassword() == null ){
                return R.error().data("mess","密码不可为空");
            }else{
                if( "".equals(code) || code == null){
                    return R.error().data("mess","验证码不可为空");
                }else{
                    User byAcunt = userService.findByAccunt2(user.getAccount());
                    System.out.println(byAcunt);
                    if(byAcunt==null){

                        //        System.out.println("注册的时候: "+user.getCreatetime().getTime());
                        System.out.println("注册的时候: "+user.getAccount());
                        SimpleHash md5 = new SimpleHash(
                                "md5",
                                user.getPassword(),
                                user.getAccount()+"",
                                1024
                        );
                        user.setPassword(String.valueOf(md5));

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



                    }else{
                        return R.error().data("mess","账号已注册");
                    }
                }
            }
        }
    }

    @RequestMapping("/loginShiro")
    public R loginshiro(String account, String password, String code, HttpSession session){

        ServletContext application = session.getServletContext();
        String ran2 =(String) application.getAttribute("randomCode2");
        User user = new User(account,password);

        Subject sub = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getAccount(),user.getPassword());

        if(user.getAccount()==null|| "".equals(user.getAccount())){
            return R.error().data("mess","账号不可为空");
        }else{
            if("".equals(user.getPassword()) || user.getPassword() == null ){
                return R.error().data("mess","密码不可为空");
            }else{
                if( "".equals(code) || code == null){
                    return R.error().data("mess","验证码不可为空");
                }else{

                    if(ran2.equalsIgnoreCase(code)){

                        try {
                            sub.login(token);
                            User admin =(User) sub.getPrincipal();
                            session.setAttribute("admin",admin);
                            return R.ok().data("admin",admin);
                        } catch (AuthenticationException e) {
                            return R.error().data("error","登录失败");
                        }
                    }else {
                        return R.error().data("error","验证码输入错误");
                    }


                }
            }
        }
    }

}
