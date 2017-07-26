package com.baidu.disconf.web.service.user.service;

import java.util.List;

import com.baidu.disconf.web.service.user.bo.User;
import com.baidu.disconf.web.service.user.dto.Visitor;
import com.baidu.disconf.web.service.user.vo.VisitorVo;
import com.baidu.dsp.common.dao.Columns;

/**
 * @author liaoqiqi
 * @version 2013-11-28
 */
public interface UserMgr {

    /**
     * 获取用户的基本信息（登录用户）
     *
     * @return
     */
    Visitor getVisitor(Long userId);

    VisitorVo getCurVisitor();

    User getUser(Long userId);

    /**
     * @return
     */
    Long create(User user);

    /**
     * @param user
     */
    void create(List<User> user);

    /**
     * @return
     */
    List<User> getAll();

    /**
     * 为某个user添加一个app
     *
     * @param userId
     */
    String addOneAppForUser(Long userId, int appId);

    /**
     * 修改密码
     *
     * @param newPassword
     */
    void modifyPassword(Long userId, String newPassword);

    /**
     * 根据用户名获取user信息
     * @param name
     * @return
     */
    User  getUserByName(String name) ;

    /**
     * 根据传入的ID删除用户
     * @param userId
     *
     */
    void delete(Long userId);

    void updateUser(Long userId, int roleId, String ownapps);
}
