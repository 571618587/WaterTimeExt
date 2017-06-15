package com.ytzl.wt.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ytzl.wt.model.SysUser;
import com.ytzl.wt.utils.DigestUtil;

@Controller
@RequestMapping("/sysUser")
public class SysUserController extends BaseController {

	@RequestMapping("/query")
	public String query(Model model) {
		model.addAttribute("sysUserList", sysUserService.query());
		return "index";
	}

	/**
	 * 验证账号是否存在
	 * 
	 * @作者：samson
	 * @开发公司:北京云图智联科技有限公司
	 * @创建日期:2017年6月8日 下午12:10:23
	 * @param account
	 * @return
	 */
	@RequestMapping("/verifyAccount")
	@ResponseBody
	public String verifyAccount(String account) {
		SysUser sysUser = sysUserService.findByAccount(account);
		if (sysUser != null) {
			return "have";
		}
		return "noHave";
	}

	@RequestMapping("/login")
	@ResponseBody
	public String login(SysUser sysUser, HttpSession session) {
		boolean isVerify = true;
		String errorMessage = "";
		Subject currentUser = SecurityUtils.getSubject();
		if (!currentUser.isAuthenticated()) {
			System.err.println("---------" + DigestUtil.digest("yuntuzhilian"));
			String mp5Password = DigestUtil.hmacSign(sysUser.getPassword(), DigestUtil.digest("yuntuzhilian"));
			System.err.println("---------" + mp5Password);
			UsernamePasswordToken token = new UsernamePasswordToken(sysUser.getAccount(), mp5Password);
			try {
				currentUser.login(token);
			} catch (UnknownAccountException uae) {
				uae.printStackTrace();
				isVerify = false;
				errorMessage = "用户不存在，请重新输入";
			} catch (IncorrectCredentialsException ice) {
				ice.printStackTrace();
				isVerify = false;
				errorMessage = "用户名或密码错误，请重新输入";
			} catch (LockedAccountException lae) {
				lae.printStackTrace();
				isVerify = false;
				errorMessage = "账户被锁定，请联系管理员后再试";
			} catch (AuthenticationException ae) {
				ae.printStackTrace();
				isVerify = false;
				errorMessage = "登录失败，请输入正确的登录信息";
			}
		}
		if (!isVerify) {
			return errorMessage;
		}
		return "success";
	}

	/**
	 * 退出
	 * 
	 * @作者：samson
	 * @开发公司:北京云图智联科技有限公司
	 * @创建日期:2017年6月6日 上午11:47:07
	 * @return
	 */
	@RequestMapping("/logout")
	public String logout() {
		SecurityUtils.getSubject().logout();
		return "redirect:/send_index.jhtml";
	}

	@RequestMapping("/queryPage")
	@ResponseBody
	public String queryPage(Integer page, Integer rows, String account) {
		Map<String, Object> params = new HashMap<>();
		params.put("startSize", (page - 1) * rows);
		params.put("rows", rows);
		params.put("account", "%" + account + "%");
		// 分页查询
		List<SysUser> sysUsers = sysUserService.queryPage(params);
		// 查询总数量
		Long count = sysUserService.getCount(params);
		// 返回结果
		Map<String, Object> resultMap = new HashMap<>();
		resultMap.put("total", count);
		resultMap.put("rows", sysUsers);
		return toJOSN(resultMap);
	}

	/**
	 * 更新
	 * 
	 * @作者：samson
	 * @开发公司:北京云图智联科技有限公司
	 * @创建日期:2017年6月7日 下午3:21:21
	 */
	@RequestMapping("/update")
	@ResponseBody
	public void update(SysUser sysUser) {
		sysUserService.update(sysUser);
	}

	/**
	 * 添加
	 * 
	 * @作者：samson
	 * @开发公司:北京云图智联科技有限公司
	 * @创建日期:2017年6月8日 上午10:08:46
	 * @param sysUser
	 */
	@RequestMapping("/save")
	@ResponseBody
	public void save(SysUser sysUser) {
		sysUser.setUserCode(getCode());
		// 使用md5 二代加密密码
		String mp5Password = DigestUtil.hmacSign(sysUser.getPassword(), DigestUtil.digest("yuntuzhilian"));
		sysUser.setPassword(mp5Password);
		sysUserService.save(sysUser);
	}

	/**
	 * 更新禁用状态
	 * 
	 * @作者：samson
	 * @开发公司:北京云图智联科技有限公司
	 * @创建日期:2017年6月7日 下午4:00:24
	 * @param sysUser
	 */
	@RequestMapping("/updateDisabled")
	@ResponseBody
	public String updateDisabled(SysUser sysUser, HttpSession session) {
		SysUser sysUser2 = (SysUser) session.getAttribute("sysUser");
		if (sysUser2.getUserCode().equals(sysUser.getUserCode())) {
			return "不能禁用当前登录用户";
		}
		sysUserService.updateDisabled(sysUser);
		return "success";
	}

}
