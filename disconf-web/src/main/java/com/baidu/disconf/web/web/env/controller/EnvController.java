package com.baidu.disconf.web.web.env.controller;

import java.util.List;

import com.baidu.disconf.web.web.env.validator.EnvValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.baidu.disconf.web.service.env.service.EnvMgr;
import com.baidu.disconf.web.service.env.bo.Env;
import com.baidu.disconf.web.service.env.vo.EnvListVo;
import com.baidu.dsp.common.constant.WebConstants;
import com.baidu.dsp.common.controller.BaseController;
import com.baidu.dsp.common.vo.JsonObjectBase;

/**
 * @author liaoqiqi
 * @version 2014-6-16
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/env")
public class EnvController extends BaseController {

    protected static final Logger LOG = LoggerFactory.getLogger(EnvController.class);

    @Autowired
    private EnvMgr envMgr;

    @Autowired
    private EnvValidator envValidator;

    /**
     * list
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBase list() {

        List<EnvListVo> envListVos = envMgr.getVoList();

        return buildListSuccess(envListVos, envListVos.size());
    }

    /**
     * 获取某个
     *
     * @param envId
     *
     * @return
     */
    @RequestMapping(value = "/{envId}", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBase getConfig(@PathVariable long envId) {


        Env env = envMgr.getById(envId);

        return buildSuccess(env);
    }

    /**
     * 删除某个
     *
     * @param envId
     *
     * @return
     */
    @RequestMapping(value = "/{envId}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonObjectBase delete(@PathVariable long envId) {


        //envMgr.delete(envId);

        return buildSuccess("删除成功");
    }

    /**
     * 配置项的更新
     *
     * @param envId
     * @param value
     *
     * @return
     */
    @RequestMapping(value = "/{envId}", method = RequestMethod.PUT)
    @ResponseBody
    public JsonObjectBase updateItem(@PathVariable long envId, String value) {

        // 业务校验
        envValidator.validateUpdateItem(envId, value);

        LOG.info("start to update env: " + envId);

        //
        // 更新, 并写入数据库
        //
        String result = "";
        result = envMgr.updateEnvValue(envId, value);

        return buildSuccess(result);
    }

}
