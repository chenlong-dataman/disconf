package com.baidu.disconf.web.web.app.controller;

import java.util.List;

import javax.validation.Valid;

import com.baidu.disconf.web.service.app.bo.App;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.disconf.web.service.app.form.AppNewForm;
import com.baidu.disconf.web.service.app.service.AppMgr;
import com.baidu.disconf.web.service.app.vo.AppListVo;
import com.baidu.disconf.web.web.app.validator.AppValidator;
import com.baidu.dsp.common.constant.WebConstants;
import com.baidu.dsp.common.controller.BaseController;
import com.baidu.dsp.common.vo.JsonObjectBase;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/app")
public class AppController extends BaseController {

    protected static final Logger LOG = LoggerFactory.getLogger(AppController.class);

    @Autowired
    private AppMgr appMgr;

    @Autowired
    private AppValidator appValidator;

    /**
     * list
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBase list() {

        List<AppListVo> appListVos = appMgr.getAuthAppVoList();

        return buildListSuccess(appListVos, appListVos.size());
    }

    /**
     * create
     *
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectBase create(@Valid AppNewForm appNewForm) {

        LOG.info(appNewForm.toString());

        appValidator.validateCreate(appNewForm);

        appMgr.create(appNewForm);

        return buildSuccess("创建成功");
    }

    /**
     * 获取某个
     *
     * @param appId
     *
     * @return
     */
    @RequestMapping(value = "/{appId}", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBase getConfig(@PathVariable long appId) {


        App app = appMgr.getById(appId);

        LOG.info(app.toString());

        return buildSuccess(app);
    }

    /**
     * 更新APP信息
     *
     * @param appId
     *
     * @return
     */
    @RequestMapping(value = "/{appId}", method = RequestMethod.PUT)
    @ResponseBody
    public JsonObjectBase update(@PathVariable long appId, String name, String desc, String emails) {

        LOG.info("准备更新APP信息");

        LOG.info("App [id=" + appId + ", name=" + name + ", desc=" + desc + ", emails=" + emails + "]");

        String[] emailArray = emails.split(";");
        for(String email : emailArray) {
            if (!email.matches("[\\w\\.\\-]+@([\\w\\-]+\\.)+[\\w\\-]+")) {
                return buildSuccess(email + "不是合法的邮件地址");
            }
        }


        appMgr.update(appId, name, desc, emails);

        return buildSuccess("APP信息更新成功");
    }

    /**
     * 获取某个
     *
     * @param appId
     *
     * @return
     */
    @RequestMapping(value = "/{appId}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonObjectBase delete(@PathVariable long appId) {


        LOG.info("DELETE APP { appId = " + appId + "}");

        if(appValidator.validateDelete(appId)) {
            return buildSuccess("存在该应用的配置项，不能删除");
        }
        appMgr.delete(appId);

        return buildSuccess("删除成功");
    }
}
