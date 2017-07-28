package me.inrush.italker.bean.api.account;

import com.google.common.base.Strings;
import com.google.gson.annotations.Expose;

/**
 * @author inrush
 * @date 2017/7/25.
 * @package me.inrush.italker.bean.api.account
 */
public class LoginModel {
    @Expose
    private String account;
    @Expose
    private String password;
    @Expose
    private String pushId;

    public String getAccount() {
        return account.trim();
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password.trim();
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPushId() {
        return pushId;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    /**
     * 对登录信息进行校验
     * @param model 登录信息
     * @return 校验成功或者失败
     */
    public static boolean check(LoginModel model){
        return model != null
                && !Strings.isNullOrEmpty(model.account)
                && !Strings.isNullOrEmpty(model.password);
    }
}
