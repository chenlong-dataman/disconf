package com.baidu.disconf.web.service.user.form;

import com.baidu.dsp.common.form.RequestFormBase;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * 新建配置文件表格
 *
 * @author liaoqiqi
 * @version 2014-7-3
 */
public class UserNewForm  extends RequestFormBase {


    @NotNull(message = "name.empty")
    @NotEmpty(message = "name.empty")
    private String name;
    public static final String NAME = "name";

    @NotNull(message = "user_password.empty")
    @NotEmpty(message = "user_password.empty")
    private String user_password;
    public static final String USER_PASSWORD = "user_password";


    @NotNull(message = "confirm_password.empty")
    @NotEmpty(message = "confirm_password.empty")
    private String confirm_password;
    public static final String CONFIRM_PASSWORD = "confirm_password";

    @NotNull(message = "ownapps.empty")
    @NotEmpty(message = "ownapps.empty")
    private String ownapps;
    public static final String OWNAPPS = "ownapps";

    @NotNull(message = "role.empty")
    @NotEmpty(message = "role.empty")
    private String role;
    public static final String ROLE = "role";




    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getOwnapps() {
        return ownapps;
    }

    public void setOwnapps(String ownapps) {
        this.ownapps = ownapps;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
