package me.inrush.italker;

import me.inrush.italker.provider.AuthRequestFilter;
import me.inrush.italker.provider.GsonProvider;
import me.inrush.italker.service.AccountService;
import org.glassfish.jersey.server.ResourceConfig;

import java.util.logging.Logger;

/**
 * @author inrush
 */
public class Application extends ResourceConfig {
    public Application(){
        // 注册逻辑处理的包
        packages(AccountService.class.getPackage().getName());
        // 注册我们的全局请求拦截器
        register(AuthRequestFilter.class);
        // 注册Json解析器
//        register(JacksonJsonProvider.class);
        // 注册Jackson解析器
        register(GsonProvider.class);
        // 注册日志打印输出
        register(Logger.class);
    }
}
