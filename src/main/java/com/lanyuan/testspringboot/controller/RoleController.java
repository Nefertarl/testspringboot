package com.lanyuan.testspringboot.controller;

import com.github.pagehelper.PageInfo;
import com.lanyuan.testspringboot.interfaces.R;
import com.lanyuan.testspringboot.pojo.Role;
import com.lanyuan.testspringboot.pojo.User;
import com.lanyuan.testspringboot.service.RoleService;
import com.lanyuan.testspringboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if(u.getRolename()==null || "".equals(u.getRolename())){
            return R.error().data("mess","角色名称不能为空");
        }else{
            Role role = roleService.findByRolename(u.getRolename());
            System.out.println(role);
            if(role==null){
                int i = roleService.addRole(u);
                if(i>0){
                    return R.ok();
                }else{
                    return R.error();
                }
            }else{
                return R.error().data("mess","角色已存在");
            }
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

        if(id == null){
            return R.error().data("mess","id的参数为空");
        }else{
            //先删除原来的关系表
            roleService.removeRid(id);

            int n = roleService.removeById(id);
            if(n>0){
                return R.ok();
            }else{
                return R.error();
            }
        }
    }

    @DeleteMapping("/doBathDelRole")
    public R doBathDelRole(Integer[] ids){

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
    }

    @GetMapping("/findById")
    public R findById(Integer id){

        if(id == null){
            return R.error().data("mess","id的参数不能为空");
        }else{
            Role role = roleService.getById(id);
            if(role!=null){
                return R.ok().data("role",role);
            }else{
                return R.error().data("mess","根据此id从数据库查不到该数据");
            }
        }
    }

    @PutMapping("/doUpdateRole")
    public R doUpdateRole(Role role){

        if(role.getId() == null){
            return R.error().data("mess","id的参数为空");
        }else{
            Role r = roleService.getById(role.getId());
            if(r==null){
                return R.error().data("mess","对象不存在");
            }else{
                if(role.getRolename() != null){
                    if("".equals(role.getRolename())){
                        return R.error().data("mess","角色名称不能为空");
                    }else{
                        int i = roleService.updateById(role);
                        if(i>0){
                            return R.ok();
                        }else{
                            return R.error();
                        }
                    }
                }else{
                    return R.error().data("mess","角色名称不能为空");
                }
            }
        }
    }

    //带条件的分页查询
    @GetMapping("/pageList")
    public R pageList(Role role,@RequestParam(defaultValue = "1") Integer pageNum,
                      @RequestParam(defaultValue = "5") Integer pageSize){
        PageInfo<Role> pageInfo = null;
        if(role.getRolename()==null || "".equals(role.getRolename())){
            pageInfo = roleService.getPage(role,pageNum,pageSize);
            Map<String,Object> map = new HashMap<>();
            map.put("mess","查询所有的角色");
            map.put("items",pageInfo.getList());
            return R.ok().data(map);
        }else{
            pageInfo = roleService.getPage(role,pageNum,pageSize);
            Map<String,Object> map = new HashMap<>();
            map.put("搜索的条件",role.getRolename());
            map.put("items",pageInfo.getList());
            return R.ok().data(map);
        }
    }

    //查询所有的角色
    @GetMapping("/selectRoleAll")
    public R selectRoleAll(Role role){
        List<Role> rt = roleService.selectRoleAll();
        return R.ok().data("rs",rt);
    }

    @PostMapping("/doAddRelation")
    public R doAddRelation(Integer uid,Integer[] rids){

        if(uid == null){
            return R.error().data("mess","用户id不能为空");
        }else{
            if(rids == null){
                return R.error().data("mess","角色数组不能为空");
            }else{

                System.out.println("长度为:" + rids.length);

                if(rids.length == 0){
                    return R.error().data("mess","角色数组里面的元素不能为空");
                }

                for (int i = 0; i < rids.length; i++) {
                    if(rids[i] == null){
                        return R.error().data("mess","角色id不能为空");
                    }
                }

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

    }

}
