package com.lanyuan.testspringboot.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import com.lanyuan.testspringboot.realm.MyRealm;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

// 针对于Shiro配置类(把不经常修改配置,在类中完成)

@Configuration //标识该类是配置类
public class ShiroConfig {

    /*
      @Bean等价于之前<bean>
        下面对应方法返回值就是bean对象类型
        方法名可以任意写,等价于bean对象的id
     */
    @Bean //加密方式
    HashedCredentialsMatcher credentialsMatcher(){
        HashedCredentialsMatcher h = new HashedCredentialsMatcher();
        //配置算法
        h.setHashAlgorithmName("MD5");
        //配置次数
        h.setHashIterations(1024);
        return h;
    }

    @Bean //MyRealm
    MyRealm myRealm(){
        MyRealm my = new MyRealm();
        //注入加密方式
        my.setCredentialsMatcher(credentialsMatcher());
        return my;
    }

    @Bean //安全管理
    DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager sm = new DefaultWebSecurityManager();
        sm.setRealm(myRealm());
        return sm;
    }

    @Bean //过滤规则
    ShiroFilterChainDefinition shiroFilterChainDefinition(){
        DefaultShiroFilterChainDefinition filters = new DefaultShiroFilterChainDefinition();
        //设置的规则(放行 静态资源  登录退出 其他全都拦截)
        /*
            放行 anon
            静态资源 /static/**
            登录退出 logout
            其他全都拦截  authc
         */

        filters.addPathDefinition("/user/RegisterUserShiro","anon");
        filters.addPathDefinition("/user/loginShiro","anon");

        filters.addPathDefinition("/toRegister","anon");
        filters.addPathDefinition("/user/RegisterUser","anon");
        filters.addPathDefinition("/user/getCode","anon");
        filters.addPathDefinition("/user/checkAccount","anon");
        filters.addPathDefinition("/toLogin","anon");
        filters.addPathDefinition("/user/login","anon");

        filters.addPathDefinition("/js/**","anon");
        filters.addPathDefinition("/images/**","anon");
        filters.addPathDefinition("/css/**","anon");
        filters.addPathDefinition("/fonts/**","anon");

        filters.addPathDefinition("/logout","logout");


        filters.addPathDefinition("/**","authc");

        return filters;
    }

    @Bean //方言: 是为了整合thymeleaf时使用的,目的让它支持shiro标签
    ShiroDialect shiroDialect(){
        return  new ShiroDialect();
    }
}
