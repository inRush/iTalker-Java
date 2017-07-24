package me.inrush.italker.service;

import me.inrush.italker.bean.api.account.RegisterModel;
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
    @Path("/register")
    // 响应体和请求都是JSON
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public UserCard register(RegisterModel model) {
        User user = UserFactory.register(model);

        if(user != null){
            UserCard userCard = new UserCard();
            userCard.setName(user.getName());
            userCard.setPhone(user.getPhone());
            userCard.setSex(user.getSex());
            userCard.setModifyAt(user.getUpdateAt());
            userCard.setFollow(true);
            return userCard;
        }
        return null;
    }
}
