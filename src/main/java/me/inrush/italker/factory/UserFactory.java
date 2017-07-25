package me.inrush.italker.factory;

import me.inrush.italker.bean.api.account.LoginModel;
import me.inrush.italker.bean.api.account.RegisterModel;
import me.inrush.italker.bean.db.User;
import me.inrush.italker.utils.Hib;
import me.inrush.italker.utils.TextUtil;
import org.hibernate.Session;

import java.util.UUID;

/**
 * @author inrush
 * @date 2017/7/24.
 * @package me.inrush.italker.factory
 */
public class UserFactory {

    /**
     * 通过电话号码查询用户
     *
     * @param phone 需要查询的电话
     * @return 查询到的用户
     */
    public static User findByPhone(String phone) {
        return Hib.query(session -> (User) session
                .createQuery("from User where phone=:inPhone")
                .setParameter("inPhone", phone)
                .uniqueResult());

    }

    /**
     * 通过电话号码查询用户
     *
     * @param name 需要查询的电话
     * @return 查询到的用户
     */
    public static User findByName(String name) {
        return Hib.query(session -> (User) session
                .createQuery("from User where name=:inName")
                .setParameter("inName", name)
                .uniqueResult());
    }

    /**
     * 通过token查询用户信息
     * 只能自己使用,查询的信息是个人的信息,非他人的信息
     * @param token 令牌
     * @return 用户信息
     */
    public static User findByToken(String token){
        return Hib.query(session -> (User) session
                .createQuery("from User where token=:token")
                .setParameter("token", token)
                .uniqueResult());
    }

    /**
     * 使用账号密码登录
     * @param model 登录信息
     * @return 登录成功的用户
     */
    public static User login(LoginModel model) {
        final String accountStr = model.getAccount();
        final String passwordStr = encodePassword(model.getPassword());

        User user = Hib.query(session -> (User) session
                .createQuery("from User where phone=:phone and password=:password")
                .setParameter("phone", accountStr)
                .setParameter("password", passwordStr)
                .uniqueResult());
        if(user != null){
            // 对用户的Token进行更新
            user = updateToken(user);
        }
        return user;

    }

    /**
     * 用户注册
     * 注册操作需要写入数据库,并返回数据库中的User信息
     *
     * @param model 用户注册Model
     * @return 数据库中User信息
     */
    public static User register(RegisterModel model) {
        processRegisterModel(model);

        User user = createUser(model.getAccount(), model.getPassword(), model.getName());
        if (user != null) {
            user = updateToken(user);
        }
        return user;
    }

    /**
     * 创建一个用户,对用户进行数据库存储
     *
     * @param account  账号
     * @param password 密码
     * @param name     用户名
     * @return 返回创建成功的用户
     */
    private static User createUser(String account, String password, String name) {
        User user = new User();
        user.setName(name);
        user.setPassword(password);
        // 账户就是手机号
        user.setPhone(account);

        return Hib.query(session -> (User) session.save(user));
    }

    /**
     * 对用户的token进行更新
     * @param user 需要登录的用户
     * @return 登录成功的用户
     */
    private static User updateToken(User user) {
        // 使用一个随机的UUID值充当Token
        String token = UUID.randomUUID().toString();
        // 对Token进行一次Base64格式化
        token = TextUtil.encodeBase64(token);
        user.setToken(token);
        return Hib.query(session -> {
            session.saveOrUpdate(user);
            return user;
        });
    }

    /**
     * 对密码进行MD5非对称加密
     * 对密码进行Base64对称加密
     *
     * @param password 需加密的密码
     * @return 加密后的密码
     */
    private static String encodePassword(String password) {
        password = TextUtil.getMD5(password);
        password = TextUtil.encodeBase64(password);
        return password;
    }

    /**
     * 处理注册Model信息
     * @param model 注册信息
     */
    private static void processRegisterModel(RegisterModel model) {
        String account = model.getAccount().trim();
        String password = model.getPassword().trim();

        model.setAccount(account);
        model.setPassword(encodePassword((password)));
    }
}
