package com.ytzl.wt.service;

import java.util.List;
import java.util.Map;

import com.ytzl.wt.model.SysUser;

public interface SysUserService {

	/** 查询全部 */
	List<SysUser> query();

	/** 根据用户名查询用户 */
	SysUser findByAccount(String account);

	/** 分页查询数据 */
	List<SysUser> queryPage(Map<String, Object> params);

	/** 获取分页总条数 */
	Long getCount(Map<String, Object> params);

	/** 更新 */
	void update(SysUser sysUser);

	/** 更新禁用状态 */
	void updateDisabled(SysUser sysUser);

	/** 保存数据 */
	void save(SysUser sysUser);

}