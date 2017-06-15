package com.ytzl.wt.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.ytzl.wt.mapper.SysUserMapper;

@Service("baseService")
// @Component
public class BaseServiceImpl {

	@Resource
	protected SysUserMapper sysUserMapper;

}
