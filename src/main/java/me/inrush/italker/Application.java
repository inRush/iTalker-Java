package me.inrush.italker;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
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
        System.out.println(AccountService.class.getPackage().getName());
        // 注册Json解析器
        register(JacksonJsonProvider.class);

        // 注册日志打印输出
        register(Logger.class);
    }
}
