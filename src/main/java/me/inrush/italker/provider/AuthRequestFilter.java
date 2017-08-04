package me.inrush.italker.provider;

import com.google.common.base.Strings;
import me.inrush.italker.bean.api.base.ResponseModel;
import me.inrush.italker.bean.db.User;
import me.inrush.italker.factory.UserFactory;
import org.glassfish.jersey.server.ContainerRequest;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;
import javax.ws.rs.ext.Provider;
import java.io.IOException;
import java.security.Principal;

/**
 * 用于所有请求接口过滤和拦截,授权中间件
 *
 * @author inrush
 * @date 2017/8/3.
 * @package me.inrush.italker.provider
 */
@Provider
public class AuthRequestFilter implements ContainerRequestFilter {
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        // 检测是否是登陆注册地址
        String relationPath = ((ContainerRequest) requestContext).getPath(false);
        if (relationPath.startsWith("account/login") ||
                relationPath.startsWith("account/register")) {
            // 直接return 返回
            return;
        }
        // 从headers中获取到第一个token
        String token = requestContext.getHeaders().getFirst("token");
        if (!Strings.isNullOrEmpty(token)) {
            // 查询用户信息
            final User self = UserFactory.findByToken(token);
            if (self != null) {
                // 给当前请求添加一个上下文
                requestContext.setSecurityContext(new SecurityContext() {
                    // 主体部分
                    @Override
                    public Principal getUserPrincipal() {
                        // User 实现 Principal 接口
                        return self;
                    }

                    @Override
                    public boolean isUserInRole(String role) {
                        // 可以在这里写入用户权限,role是权限名
                        return true;
                    }

                    @Override
                    public boolean isSecure() {
                        // 一般是检查https
                        return false;
                    }

                    @Override
                    public String getAuthenticationScheme() {
                        return null;
                    }
                });
                // 写入上下文以后返回
                return;
            }
        }

        ResponseModel model = ResponseModel.buildAccountError();
        Response response = Response.status(Response.Status.OK)
                .entity(model).build();
        // 停止请求的继续下发,调用改方法后直接返回请求,不会走到service中去
        requestContext.abortWith(response);
    }
}
