package com.ytzl.wt.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SendController {

	@RequestMapping("/send_{sendUrl}")
	public String sendUrl(@PathVariable String sendUrl) {
		// send_sysUser_save.jhtml
		// sendUrl = sysUser_save
		// sysUser_save = sysUser/save
		// send_index
		// sendUrl = index
		return "WEB-INF/jsp/" + sendUrl.replace("_", "/");
	}

}
