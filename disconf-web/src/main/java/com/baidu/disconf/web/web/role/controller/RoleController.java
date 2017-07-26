package com.baidu.disconf.web.web.role.controller;

import com.baidu.disconf.web.service.role.bo.Role;
import com.baidu.disconf.web.service.role.service.RoleMgr;
import com.baidu.disconf.web.service.roleres.vo.RoleListVo;
import com.baidu.dsp.common.constant.WebConstants;
import com.baidu.dsp.common.vo.JsonObjectBase;
import com.baidu.dsp.common.controller.BaseController;
import com.fasterxml.jackson.databind.deser.Deserializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by paradise on 2016/12/29.
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/role")
public class RoleController extends BaseController{

    protected static final Logger LOG = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    private RoleMgr roleMgr;

    /**
     * list
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBase list() {

        LOG.info("开始执行角色列表服务");

        List<Role> roleList = roleMgr.findAll();

        LOG.info(roleList.toString());

        List<RoleListVo> roleListVos = new ArrayList<RoleListVo>();
        convert(roleList, roleListVos);

        return buildListSuccess(roleListVos, roleListVos.size());
    }

    private void convert(List<Role> roleList, List<RoleListVo> roleListVos) {
        if (roleList != null && roleList.size() > 0) {
            for (Role role : roleList) {
                RoleListVo vo = new RoleListVo();
                vo.setId(role.getId());
                vo.setName(role.getRoleName());

                roleListVos.add(vo);
            }

        }
    }

}
