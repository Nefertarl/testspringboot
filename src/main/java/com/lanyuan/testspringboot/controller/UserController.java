package com.lanyuan.testspringboot.controller;

import com.github.pagehelper.PageInfo;
import com.lanyuan.testspringboot.interfaces.R;
import com.lanyuan.testspringboot.pojo.User;
import com.lanyuan.testspringboot.service.RoleService;
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

    @Autowired
    RoleService roleService;


    @PostMapping("/addUser")
    public R addUser(User user){

        /*System.out.println("前端传过来的是啥:"+user.getHeadPic());

        int n = user.getHeadPic().lastIndexOf("\\");
        String substring = user.getHeadPic().substring(n+1);

        System.out.println("相当于[MultipartFile myHead][myHead.getOriginalFilename()]:"+substring);

        String path = null;

        try {
            //springboot使用的是内置tomcat,地址都是虚拟的
            //需要手动获取项目根目录
            path = ResourceUtils.getURL("classpath:").getPath();
            path=path+"static/upload";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("上传的地址:"+path);

        File f = new File(path);
        if(!f.exists()) f.mkdirs();
        String oldName = substring;
        String suffix = oldName.substring(oldName.lastIndexOf("."));
        String newName = UUID.randomUUID().toString()+suffix;

        path = path + File.separator + newName;

        *//*try {
            //实现上传  参数是一个文件对象
            my.transferTo(new File(path));
        } catch (IOException e) {
            e.printStackTrace();
        }*//*

        System.out.println("返回值是:"+newName);

        user.setHeadPic(newName);*/

        user.setCreatetime(new Date());
        int i = userService.addUser(user);
        if(i>0){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @PutMapping("/updateUser")
    public R updateUser( User user){

        if((user.getSex().equals("0") || user.getSex().equals("1")) && (user.getStatus().equals("1") || user.getStatus().equals("2"))){
            int i = userService.updateById(user);
            if(i>0){
                return R.ok();
            }else{
                return R.error();
            }
        }else{
            return R.error();
        }
    }



    @DeleteMapping("/doDelUser")
    public R removeUser(Integer id){
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

    @DeleteMapping("/doBathDelUser")
    public R doBathDelUser(Integer[] ids){

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
        PageInfo<User> pageInfo = userService.getPage(user,pageNum,pageSize);
        return R.ok().data("items",pageInfo.getList());
    }

    @GetMapping("/getUser")
    public R getUser(Integer id){
        User user = userService.getById(id);
        if(user!=null){
            return R.ok().data("user",user);
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
