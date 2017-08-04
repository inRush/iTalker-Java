package me.inrush.italker.service;

import me.inrush.italker.bean.db.User;

import javax.ws.rs.core.Context;
import javax.ws.rs.core.SecurityContext;

/**
 * @author inrush
 * @date 2017/8/4.
 * @package me.inrush.italker.service
 */
public class BaseService {
    // 添加一个行下文注解,该注解会给securityContext赋值
    // 具体的值是用户授权拦截器中的用户
    @Context
    protected SecurityContext securityContext;


    // 从上下文中获取自己的信息
    protected User getSelf(){
        return (User) securityContext.getUserPrincipal();
    }
}
