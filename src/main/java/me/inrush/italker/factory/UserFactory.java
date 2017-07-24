package me.inrush.italker.factory;

import me.inrush.italker.bean.api.account.RegisterModel;
import me.inrush.italker.bean.db.User;
import me.inrush.italker.utils.Hib;
import me.inrush.italker.utils.TextUtil;
import org.hibernate.Session;

/**
 * @author inrush
 * @date 2017/7/24.
 * @package me.inrush.italker.factory
 */
public class UserFactory {


    /**
     * 用户注册
     * 注册操作需要写入数据库,并返回数据库中的User信息
     * @param model 用户注册Model
     * @return 数据库中User信息
     */
    public static User register(RegisterModel model){
        processRegisterModel(model);

        User user = new User();
        user.setName(model.getName());
        user.setPassword(model.getPassword());
        // 账户就是手机号
        user.setPhone(model.getAccount());

        // 进行数据库操作
        // 首先创建一个会话
        Session session = Hib.session();
        // 开启一个事务
        session.beginTransaction();
        try {
            // 保存操作
            session.save(user);
            // 提交事务
            session.getTransaction().commit();
            return user;

        }catch (Exception e){
            // 回滚事务
            session.getTransaction().rollback();
            return null;
        }
    }

    /**
     * 对密码进行MD5非对称加密
     * 对密码进行Base64对称加密
     * @param password 需加密的密码
     * @return 加密后的密码
     */
    private static String encodePassword(String password){
        password = TextUtil.getMD5(password);
        password = TextUtil.encodeBase64(password);
        return password;
    }

    /**
     * 处理注册Model信息
     * @param model 注册信息
     */
    private static void processRegisterModel(RegisterModel model){
        String account = model.getAccount().trim();
        String password = model.getPassword().trim();

        model.setAccount(account);
        model.setPassword(encodePassword((password)));
    }
}
