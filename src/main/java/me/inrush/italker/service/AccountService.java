package me.inrush.italker.service;

import me.inrush.italker.bean.api.account.AccountRspModel;
import me.inrush.italker.bean.api.account.LoginModel;
import me.inrush.italker.bean.api.account.RegisterModel;
import me.inrush.italker.bean.api.base.ResponseModel;
import me.inrush.italker.bean.card.ApiResponse;
import me.inrush.italker.bean.card.UserCard;
import me.inrush.italker.bean.db.User;
import me.inrush.italker.bean.db.UserFollow;
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
    public ResponseModel login(LoginModel loginModel){
        if(!LoginModel.check(loginModel)){
            return ResponseModel.buildParameterError();
        }

        User user = UserFactory.login(loginModel);
        if(user != null){
            // 登录成功
            AccountRspModel rspModel = new AccountRspModel(user);
            return ResponseModel.buildOk(rspModel);
        }else{
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

        try {
            User user = UserFactory.findByPhone(model.getAccount().trim());
            if(user != null){
                return ResponseModel.buildHaveAccountError();
            }

            user = UserFactory.findByName(model.getName().trim());
            if(user != null){
                return ResponseModel.buildHaveNameError();
            }

            // 开始注册逻辑
            user = UserFactory.register(model);

            if(user != null){
                AccountRspModel rspModel = new AccountRspModel(user);
                return ResponseModel.buildOk(rspModel);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResponseModel.buildRegisterError();
        }

        return null;
    }
}
