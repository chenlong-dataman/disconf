package com.baidu.disconf.web.web.app.validator;

import com.baidu.disconf.web.service.config.bo.Config;
import com.baidu.disconf.web.service.config.service.ConfigFetchMgr;
import com.baidu.disconf.web.service.config.service.ConfigMgr;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baidu.disconf.web.service.app.bo.App;
import com.baidu.disconf.web.service.app.form.AppNewForm;
import com.baidu.disconf.web.service.app.service.AppMgr;
import com.baidu.dsp.common.exception.FieldException;

import java.util.List;

/**
 * 权限验证
 *
 * @author liaoqiqi
 * @version 2014-7-2
 */
@Component
public class AppValidator {

    @Autowired
    private AppMgr appMgr;

    @Autowired
    private ConfigMgr configMgr;

    /**
     * 验证创建
     */
    public void validateCreate(AppNewForm appNewForm) {

        // trim
        if (appNewForm.getApp() != null) {
            appNewForm.setApp(appNewForm.getApp().trim());
        }
        if (appNewForm.getDesc() != null) {
            appNewForm.setDesc(appNewForm.getDesc().trim());
        }

        String emails = appNewForm.getEmails().trim();
        String[] emailArray = emails.split(";");
        for(String email : emailArray) {
            if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
                throw new FieldException(email, "不是合法的邮件地址", null);
            }
        }

        App app = appMgr.getByName(appNewForm.getApp());
        if (app != null) {
            throw new FieldException(AppNewForm.APP, "app.exist", null);
        }
    }

    /**
     * 删除验证（如果该应用有配置项，则不允许删除）
     */
    public boolean validateDelete(Long appId) {
        List<Config> configList= configMgr.findConfigByApp(appId);

        if((configList==null) || (configList.size()==0)) {
            return false;
        }
        return true;
    }

}
