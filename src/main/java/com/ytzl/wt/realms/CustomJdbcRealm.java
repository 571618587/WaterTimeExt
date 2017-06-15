package com.ytzl.wt.realms;

import java.util.Collection;

import javax.annotation.Resource;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.springframework.stereotype.Component;

import com.ytzl.wt.model.SysUser;
import com.ytzl.wt.service.SysUserService;

@Component
public class CustomJdbcRealm extends JdbcRealm {
	@Resource
	protected SysUserService sysUserService;
	@Resource
	protected SessionDAO sessionDAO;

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("------------doGetAuthenticationInfo---------------" + token);
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		// 获取当前用户名
		String username = upToken.getUsername();
		// apache shiro获取所有在线用户
		Collection<Session> sessions = sessionDAO.getActiveSessions();
		for (Session session : sessions) {
			String loginUsername = String.valueOf(session.getAttribute(DefaultSubjectContext.PRINCIPALS_SESSION_KEY));// 获得session中已经登录用户的名字
			if (username.equals(loginUsername)) { // 这里的username也就是当前登录的username
				session.setTimeout(0); // 这里就把session清除，
			}
		}
		// 通过用户名获取用户
		SysUser sysUser = sysUserService.findByAccount(username);
		if (sysUser == null) {
			throw new UnknownAccountException("用户名不存在");
		}
		if (sysUser.getIsUse() == 0) {
			throw new LockedAccountException("账户被锁定");
		}
		// if (sysUser.getPassword().equals(upToken.getPassword())) {
		if (!sysUser.getPassword().equals(new String(upToken.getPassword()))) {
			throw new IncorrectCredentialsException("用户名密码错误");
		}
		// 将用户保存到Session
		SecurityUtils.getSubject().getSession().setAttribute("sysUser", sysUser);
		return new SimpleAuthenticationInfo(username, sysUser.getPassword(), getName());
	}

}
