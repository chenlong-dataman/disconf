package com.baidu.disconf.web.web.env.validator;

import com.baidu.disconf.web.service.env.bo.Env;
import com.baidu.disconf.web.service.env.service.EnvMgr;
import com.baidu.dsp.common.exception.FieldException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by paradise on 2016/12/28.
 */
@Component
public class EnvValidator {
    @Autowired
    private EnvMgr envMgr;

    /**
     * 校验
     *
     * @param id
     *
     * @return
     */
    public Env valideEnvExist(Long id) {

        //
        // config
        //
        Env env = envMgr.getById(id);
        if (env == null) {
            throw new FieldException("envId", "env.id.not.exist", null);
        }

        return env;
    }

    /**
     * 校验更新 配置值
     *
     * @param envId
     * @param value
     */
    public void validateUpdateItem(Long envId, String value) {

        //
        // config
        //
        valideEnvExist(envId);

        //
        // value
        //
        try {

            if (StringUtils.isEmpty(value)) {
                throw new Exception();
            }

        } catch (Exception e) {

            throw new FieldException("value", "env.item.value.null", e);
        }

    }
}
