package com.lanyuan.testspringboot.realm;

import com.lanyuan.testspringboot.pojo.User;
import com.lanyuan.testspringboot.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

//需要继承AuthorizingRealm类
public class MyRealm extends AuthorizingRealm {

    @Autowired
    @Lazy
    UserService userService;

    //权限认证
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取登录成功后在shiro中session存储的用户信息
        User admin = (User) principals.getPrimaryPrincipal();

        //创建权限信息对象(包含了有哪些权限地址,哪些角色名称)
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();



        //负责存储角色名称,如果想存储多个角色需要传递一个List<String>
        //if(a.getR()!=null) info.addRole(a.getR().getRolename());

        //负责存储权限地址
        //List<String> list = new ArrayList<>();

        /*if(a.getR()!=null){
            for (OMenu m: a.getR().getMenus()){
                list.add(m.getUrl());
            }
        }*/

        //info.addStringPermissions(list);

        return info;
    }

    //身份认证: 登录时判断用户身份的
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1.通过令牌token对象获取用户名
        String username =(String) token.getPrincipal();
        //根据用户名,查询用户信息
        User admin = userService.findByAcunt(username);
        //如果没有用户存在 登录失败 返回null 抛出异常
        if(admin==null) return null;
        /*
         //AuthenticationInfo如果用户名匹配成功,通过它来实现密码验证
         身份认证信息对象,提供4个参数有参构造：
            参数1：需要的对象保存在session
            参数2：数据库存储的密码
            参数3：盐值(相当于加密前,拼接随机内容)(时间戳毫秒数当盐值),mysql可能出现精度丢失
            参数4：realm名称
        */
//        System.out.println("登录验证: "+admin.getCreatetime().getTime());
        System.out.println("登录验证: "+admin.getAccount());
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
                admin,
                admin.getPassword(),
                new SimpleByteSource(admin.getAccount()+""),
                "myRealm"
        );
        return info;
    }
}
