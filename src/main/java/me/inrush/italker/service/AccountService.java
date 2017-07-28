package me.inrush.italker.service;

import com.google.common.base.Strings;
import me.inrush.italker.bean.api.account.AccountRspModel;
import me.inrush.italker.bean.api.account.LoginModel;
import me.inrush.italker.bean.api.account.RegisterModel;
import me.inrush.italker.bean.api.base.ResponseModel;
import me.inrush.italker.bean.db.User;
import me.inrush.italker.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author inrush
 */
// 127.0.0.1/api/account
@Path("/account")
public class AccountService {

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel login(LoginModel model) {
        if (!LoginModel.check(model)) {
            return ResponseModel.buildParameterError();
        }

        User user = UserFactory.login(model);
        if (user != null) {
            // 如果携带了pushId
            if (!Strings.isNullOrEmpty(model.getPushId())) {
                return bind(user,model.getPushId());
            }

            // 登录成功
            AccountRspModel rspModel = new AccountRspModel(user);
            return ResponseModel.buildOk(rspModel);
        } else {
            // 登录失败
            return ResponseModel.buildLoginError();
        }
    }

    @POST
    @Path("/register")
    // 响应体和请求都是JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel register(RegisterModel model) {
        if (!RegisterModel.check(model)) {
            return ResponseModel.buildParameterError();
        }

        try {
            User user = UserFactory.findByPhone(model.getAccount().trim());
            if (user != null) {
                return ResponseModel.buildHaveAccountError();
            }

            user = UserFactory.findByName(model.getName().trim());
            if (user != null) {
                return ResponseModel.buildHaveNameError();
            }

            // 开始注册逻辑
            user = UserFactory.register(model);

            if (user != null) {
                // 如果携带了pushId
                if (!Strings.isNullOrEmpty(model.getPushId())) {
                    return bind(user,model.getPushId());
                }
                AccountRspModel rspModel = new AccountRspModel(user);
                return ResponseModel.buildOk(rspModel);
            }
        } catch (Exception e) {
            // 服务器异常
            return ResponseModel.buildServiceError();
        }

        return null;
    }

    @POST
    @Path("/bind/{pushId}")
    // 响应体和请求都是JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    // 从请求头中获取Token字段
    // 从Url中获取pushId字段
    public ResponseModel bind(@HeaderParam("token") String token,
                              @PathParam("pushId") String pushId) {

        if (Strings.isNullOrEmpty(token) ||
                Strings.isNullOrEmpty(pushId)) {
            return ResponseModel.buildParameterError();
        }
        //根据Token获取到相应的用户
        User user = UserFactory.findByToken(token);
        if (user != null) {
            return bind(user,pushId);
        } else {
            // token 失效,无法进行绑定
            return ResponseModel.buildAccountError();
        }
    }

    /**
     * 绑定pushId
     * @param self 账户
     * @param pushId 设备Id
     * @return 响应
     */
    private ResponseModel bind(User self,String pushId){
        User user = UserFactory.bindPushId(self, pushId);
        if (user == null) {
            // 绑定失败则是服务器异常
            return ResponseModel.buildServiceError();
        }

        // 返回当前的账户,并且已经绑定了设备Id
        AccountRspModel rspModel = new AccountRspModel(user,true);
        return ResponseModel.buildOk(rspModel);
    }
}
