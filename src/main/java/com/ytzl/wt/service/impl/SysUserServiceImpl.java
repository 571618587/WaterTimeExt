package com.ytzl.wt.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Service;

import com.ytzl.wt.model.SysUser;
import com.ytzl.wt.service.SysUserService;

@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl implements SysUserService {

	@Override
	public List<SysUser> query() {
		return sysUserMapper.query();
	}

	@Override
	public SysUser findByAccount(String account) {
		return sysUserMapper.findByAccount(account);
	}

	@Override
	public List<SysUser> queryPage(Map<String, Object> params) {
		return sysUserMapper.queryPage(params);
	}

	@Override
	public Long getCount(Map<String, Object> params) {
		return sysUserMapper.getCount(params);
	}

	@Override
	public void update(SysUser sysUser) {
		sysUserMapper.update(sysUser);
	}

	@Override
	public void updateDisabled(SysUser sysUser) {
		sysUserMapper.updateDisabled(sysUser);
	}

	@Override
	public void save(SysUser sysUser) {
		sysUserMapper.save(sysUser);
	}

}
