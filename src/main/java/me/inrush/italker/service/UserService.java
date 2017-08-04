package me.inrush.italker.service;

import com.google.common.base.Strings;
import me.inrush.italker.bean.api.base.ResponseModel;
import me.inrush.italker.bean.api.user.UpdateInfoModel;
import me.inrush.italker.bean.card.UserCard;
import me.inrush.italker.bean.db.User;
import me.inrush.italker.factory.UserFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.SecurityContext;

/**
 * 用户信息处理
 *
 * @author inrush
 * @date 2017/8/3.
 * @package me.inrush.italker.service
 */
@Path("/user")
public class UserService extends BaseService {


    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public ResponseModel<UserCard> update(UpdateInfoModel model) {

        if (!UpdateInfoModel.check(model)) {
            return ResponseModel.buildParameterError();
        }

        //根据Token获取到相应的用户
        User user = getSelf();
        user = model.updateToUser(user);
        user = UserFactory.update(user);
        UserCard userCard = new UserCard(user, true);
        return ResponseModel.buildOk(userCard);

    }
}
