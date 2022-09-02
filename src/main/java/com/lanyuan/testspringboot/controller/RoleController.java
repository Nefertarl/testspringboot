package com.lanyuan.testspringboot.controller;

import com.github.pagehelper.PageInfo;
import com.lanyuan.testspringboot.interfaces.R;
import com.lanyuan.testspringboot.pojo.Role;
import com.lanyuan.testspringboot.pojo.User;
import com.lanyuan.testspringboot.service.RoleService;
import com.lanyuan.testspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    RoleService roleService;

    @Autowired
    UserService userService;

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

        //先删除原来的关系表
        roleService.removeRid(id);

        int n = roleService.removeById(id);
        if(n>0){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @DeleteMapping("/doBathDelRole")
    public R doBathDelRole(Integer[] ids){

        int m = 1;

        for(Integer id : ids){
            Role role = roleService.getById(id);
            if(role!=null){
                m++;
            }else{
                m = -1;
                break;
            }
        }

        if(m > 0){
            for (Integer id : ids){
                //先删除原来的关系表
                roleService.removeRid(id);
            }
            roleService.doBathDelRole(ids);
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

    //查询所有的角色
    @GetMapping("/selectRoleAll")
    public R selectRoleAll(Role role){
        List<Role> rt = roleService.selectRoleAll();
        return R.ok().data("rs",rt);
    }

    @PostMapping("/doAddRelation")
    public R doAddRelation(Integer uid,Integer[] rids){

        User user = userService.getById(uid);
        if(user != null){
            int m = 1;

            for(Integer id : rids){
                Role role = roleService.getById(id);
                if(role!=null){
                    m++;
                }else{
                    m = -1;
                    break;
                }
            }

            if(m > 0){
                //先删除原来的关系表
                roleService.removeRelation(uid);

                //再添加现有的关系
                roleService.addRelation(uid,rids);

                return R.ok();
            }
        }

        return R.error();
    }

}
