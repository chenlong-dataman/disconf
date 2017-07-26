package com.baidu.disconf.web.web.auth;

import com.baidu.disconf.web.service.app.bo.App;
import com.baidu.disconf.web.service.app.service.AppMgr;
import com.baidu.disconf.web.service.config.vo.ConfListVo;
import com.baidu.disconf.web.service.role.service.RoleMgr;
import com.baidu.disconf.web.service.role.bo.Role;
import com.baidu.disconf.web.service.sign.utils.SignUtils;
import com.baidu.dsp.common.exception.FieldException;
import com.baidu.dsp.common.vo.JsonObject;
import org.hibernate.validator.constraints.Range;
import com.baidu.disconf.web.service.user.form.UserNewForm;
import com.baidu.disconf.web.service.sign.service.SignMgr;
import com.baidu.disconf.web.service.user.bo.User;
import com.baidu.disconf.web.service.user.dto.Visitor;
import com.baidu.disconf.web.service.user.form.PasswordModifyForm;
import com.baidu.disconf.web.service.user.service.UserMgr;
import com.baidu.disconf.web.service.user.vo.UserVo;
import com.baidu.disconf.web.web.auth.constant.LoginConstant;
import com.baidu.disconf.web.web.auth.login.RedisLogin;
import com.baidu.disconf.web.web.auth.validator.AuthValidator;
import com.baidu.dsp.common.annotation.NoAuth;
import com.baidu.dsp.common.constant.ErrorCode;
import com.baidu.dsp.common.constant.WebConstants;
import com.baidu.dsp.common.controller.BaseController;
import com.baidu.dsp.common.vo.JsonObjectBase;
import com.baidu.ub.common.commons.ThreadContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.ArrayList;
/**
 * @author liaoqiqi
 * @version 2014-1-20
 */
@Controller
@RequestMapping(WebConstants.API_PREFIX + "/user")
public class CustomUserController extends BaseController {

    protected static final Logger LOG = LoggerFactory.getLogger(CustomUserController.class);

    @Autowired
    private UserMgr userMgr;

    @Autowired
    private RedisLogin redisLogin;

    @Autowired
    private RoleMgr roleMgr;

    @Autowired
    private AppMgr appMgr;

    /**
     * 登录
     *
     * @param userNewForm
     * @param request
     *
     * @return
     */
    @RequestMapping(value = "/modify", method = RequestMethod.POST)
    @ResponseBody
    public JsonObjectBase modify(@Valid UserNewForm userNewForm, HttpServletRequest request) {

        LOG.info(userNewForm.toString());

        // 验证
        Visitor visitor = ThreadContext.getSessionVisitor();
        LOG.info("用户roleId=" + visitor.getRoleId());
        if(visitor.getRoleId()!=2) {
            return buildSuccess("提示：您没有添加用户的权限！");
        }


        User dbUser= userMgr.getUserByName(userNewForm.getName());
        if(dbUser!=null){
             throw new FieldException(UserNewForm.NAME, "用户名重复!", null);
          //  return buildSuccess( "提示：用户名重复！");
        }

        // 2017-01-04 修改
        try {
            validateAppExistence(userNewForm.getOwnapps());
        } catch(FieldException ex){
            LOG.info(ex.getErrorMessage());
            return buildSuccess(ex.getErrorMessage());
        }

        User user = new User();
        user.setName(userNewForm.getName()  );
        user.setPassword(SignUtils.createPassword(userNewForm.getUser_password()));
        user.setOwnApps(userNewForm.getOwnapps());

        user.setRoleId(Integer.valueOf(userNewForm.getRole()));
        // token
        user.setToken(SignUtils.createToken(userNewForm.getName()));
        Long userid= userMgr.create(user );
        LOG.info("增加用户 success" +  userid);


        // 过期时间
       // int expireTime = LoginConstant.SESSION_EXPIRE_TIME;

       // redisLogin.login(request, user, expireTime);

      //   VisitorVo visitorVo = userMgr.getCurVisitor();

         return buildSuccess("提示：用户新建成功！");
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBase list() {

        LOG.info("开始执行用户列表服务");

        List<User> userList = userMgr.getAll();

        LOG.info(userList.toString());

        List<UserVo> userListVos= new ArrayList<UserVo>();

        convert(userList, userListVos);

        LOG.info(userListVos.toString());

        return buildListSuccess(userListVos, userListVos.size());
    }

    private void convert( List<User> userList, List<UserVo> appListVos){
        if(userList!=null &&userList.size()>0){
            for(User user:userList){
                UserVo vo = new UserVo();
                vo.setId(user.getId());
                vo.setName(user.getName());
                /*
                 * 根据roleid获取rolename
                 */
                Role role = roleMgr.get(user.getRoleId());
                vo.setRole(role.getRoleName());

                /*
                 * 根据拥有的app ids获取app names
                 */

                vo.setOwnapps(appMgr.getAppsNameByIds(user.getOwnApps()));
                appListVos.add(vo);
            }

        }
    }

    /**
     * 获取某个
     *
     * @param userId
     *
     * @return
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    @ResponseBody
    public JsonObjectBase getConfig(@PathVariable long userId) {


        User user = userMgr.getUser(userId);

        UserVo vo = new UserVo();
        vo.setId(user.getId());
        vo.setName(user.getName());
        vo.setRoleId(user.getRoleId());
        /*
         * 根据roleid获取rolename
         */
        Role role = roleMgr.get(user.getRoleId());
        vo.setRole(role.getRoleName());

        /*
         * 根据拥有的app ids获取app names
         */

        vo.setOwnapps(appMgr.getAppsNameByIds(user.getOwnApps()));

        return buildSuccess(vo);
    }

    /**
     * 删除某个用户
     *
     * @return
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.DELETE)
    @ResponseBody
    public JsonObjectBase delete(@PathVariable long userId) {

        //antuValidator.validateDelete(userId);
        User userfordel = userMgr.getUser(userId);

        if(userfordel.getRoleId() == 2) {
            return buildSuccess("管理员不能被删除");
        }

        userMgr.delete(userId);

        return buildSuccess("删除成功");
    }

    /**
     * 更新某个用户
     *
     */
    @RequestMapping(value = "/{userId}", method = RequestMethod.PUT)
    @ResponseBody
    public JsonObjectBase update(@PathVariable long userId, String roleId, String ownapps) {

        LOG.info("准备更新用户信息");
        LOG.info("User [id=" + userId + ", roleId=" + roleId + ", ownapps=" + ownapps + "]");

        try {
            validateAppExistence(ownapps);
        } catch(FieldException ex){
            LOG.info(ex.getErrorMessage());
            return buildSuccess(ex.getErrorMessage());
        }

        userMgr.updateUser(userId, Integer.valueOf(roleId), ownapps);

        return buildSuccess("用户信息更新成功");
    }

    /**
     * 验证APP_ID是否存在
     */
    private void validateAppExistence(String ownApps) {
        StringBuilder sbExist = new StringBuilder();

        String[] appIds = ownApps.split(",");
        for(String appId : appIds) {
            App app = appMgr.getById(Long.valueOf(appId));
            if(app == null) {
                sbExist.append("[id=").append(appId).append("] 的APP不存在");
                throw new FieldException("输入的APP有误", sbExist.toString(), null);
            }
        }

    }

}
