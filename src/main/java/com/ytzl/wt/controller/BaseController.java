package com.ytzl.wt.controller;

import java.util.UUID;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.ytzl.wt.service.SysUserService;
import com.ytzl.wt.utils.DigestUtil;

@Controller
public class BaseController {

	@Resource
	protected SysUserService sysUserService;

	/**
	 * 将数据转换成JSON字符串
	 * 
	 * @作者：samson
	 * @开发公司:北京云图智联科技有限公司
	 * @创建日期:2017年6月7日 下午12:23:22
	 * @param object
	 * @return
	 */
	public String toJOSN(Object object) {
		return JSON.toJSONString(object, SerializerFeature.DisableCircularReferenceDetect);
	}

	/**
	 * 生成唯一Code
	 * 
	 * @作者：samson
	 * @开发公司:北京云图智联科技有限公司
	 * @创建日期:2017年6月8日 上午10:46:28
	 * @return
	 */
	public String getCode() {
		return DigestUtil.hmacSign("ytzl_" + UUID.randomUUID(), "ytzl_" + System.currentTimeMillis());
	}

}
