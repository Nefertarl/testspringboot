package com.lanyuan.testspringboot.controller;

import com.github.pagehelper.PageInfo;
import com.lanyuan.testspringboot.interfaces.R;
import com.lanyuan.testspringboot.pojo.Role;
import com.lanyuan.testspringboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @GetMapping("/pageListRole")
    public R pageListRole(@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "5") Integer pageSize){
        PageInfo<Role> pageInfo = roleService.getPage(pageNum,pageSize);
        return R.ok().data("items",pageInfo.getList());
    }

    @PostMapping("/doAdd")
    public R doAdd( Role u){
        int i = roleService.addRole(u);
        if(i>0){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //验证角色名称是否可用
    @RequestMapping(value = "/checkRolename",produces = "text/html;charset=utf-8")
    public String checkRolename(String rolename){
        Role u = roleService.findByRolename(rolename);
        if (u==null){
            return "角色没重复";
        }else {
            return "角色不可用";
        }
    }

    @DeleteMapping("/doDelRole")
    public R doDelRole(Integer id){
        int n = roleService.removeById(id);
        if(n>0){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @DeleteMapping("/doBathDelRole")
    public R doBathDelRole(Integer[] ids){
        int i = roleService.doBathDelRole(ids);
        if(i>0){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @GetMapping("/findById")
    public R findById(Integer id){
        Role role = roleService.getById(id);
        if(role!=null){
            return R.ok().data("role",role);
        }else{
            return R.error();
        }
    }

    @PutMapping("/doUpdateRole")
    public R doUpdateRole(Role role){
        int i = roleService.updateById(role);
        if(i>0){
            return R.ok();
        }else{
            return R.error();
        }
    }

    //带条件的分页查询
    @GetMapping("/pageList")
    public R pageList(Role role,@RequestParam(defaultValue = "1") Integer pageNum,
                          @RequestParam(defaultValue = "5") Integer pageSize){
        PageInfo<Role> pageInfo = roleService.getPage(role,pageNum,pageSize);
        return R.ok().data("items",pageInfo.getList());
    }

}
