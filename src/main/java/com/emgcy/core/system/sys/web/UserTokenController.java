package com.emgcy.core.system.sys.web;


import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import com.emgcy.core.system.sys.service.IUserTokenService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 系统用户Token 前端控制器
 * </p>
 *
 * @author buguangyi
 * @since 2017-11-10
 */
@Controller
@RequestMapping("/sys/userToken")
public class UserTokenController {

	private static final Logger logger = LoggerFactory.getLogger(UserTokenController.class);
	
	@Autowired
	private IUserTokenService userTokenService;
}
