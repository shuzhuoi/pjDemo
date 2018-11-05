package com.emgcy.core.system.sys.web;


import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.emgcy.core.common.base.BaseMsg;
import com.emgcy.core.common.constants.Constant;
import com.emgcy.core.common.util.TokenGenerator;
import com.emgcy.core.system.sys.dto.LoginUserParaDTO;
import com.emgcy.core.system.sys.entity.Org;
import com.emgcy.core.system.sys.entity.User;
import com.emgcy.core.system.sys.service.IOrgService;
import com.emgcy.core.system.sys.service.IUserService;
import com.emgcy.core.system.sys.service.IUserTokenService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * 
 * @author bgy
 * @date 2017-12-5
 *
 */
@Api(tags="系统登录")
@RestController
@RequestMapping("/login")
public class LoginController {

	private static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@Autowired
	private IUserService userService;
	@Autowired
	private IOrgService orgService;
	@Autowired
	private IUserTokenService userTokenService;
	
	@ApiOperation(value = "院内登录")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "hospitalId", value = "医院ID(手机号登录时为空)", paramType="form", dataType = "String"),
		@ApiImplicitParam(name = "username", value = "用户名(手机号或工号)", paramType="form", dataType = "String"),
		@ApiImplicitParam(name = "password", value = "密码", paramType="form", dataType = "String") })
	@RequestMapping(value = "/sysLogin", method = RequestMethod.POST)
	public Map<String, Object> login(LoginUserParaDTO dto) {
		String hospitalId = dto.getHospitalId();
		String username = dto.getUsername();
		String password = dto.getPassword();
		
		if(StringUtils.isBlank(username)){
			return BaseMsg.errorMsg("账号不能为空");
		}
		if (StringUtils.isBlank(password)){
			return BaseMsg.errorMsg("密码不能为空");
		}
		
		EntityWrapper<User> userWrapper = new EntityWrapper<>();
		if (StringUtils.isBlank(hospitalId)){
			userWrapper.eq("phone", username);
		} else{
			userWrapper.eq("org_hospital_id", hospitalId);
			userWrapper.eq("employee_id", username);
		}
		List<User> userList = userService.selectList(userWrapper);

		if(userList==null||userList.size()==0){
			return BaseMsg.errorMsg("账号或密码不正确");
		}else if(userList.size()>1){
			return BaseMsg.errorMsg("账号对应多个用户,请联系管理员");
		}else{
			User user = userList.get(0);
			if (user.getStatus()== 0) {
				return BaseMsg.errorMsg("账号未激活,请先激活账号");
			}
			if (user.getStatus()== -1) {
				return BaseMsg.errorMsg("账号已被停用,请联系管理员");
			}
			if( !user.getPassword().equals(TokenGenerator.entryptPassword(password, user.getSalt()))) {
				return BaseMsg.errorMsg("账号或密码不正确");
			}
			//生成token并返回跳转到前台或后台
			BaseMsg msg = userTokenService.createToken(user);
			return msg;
		}
	}
	
	@ApiOperation(value = "token验证")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "token", value = "token", dataType = "String",paramType="query") })
	@GetMapping("/checkToken")
	public BaseMsg checkToken(String token) {
		
		if(StringUtils.isBlank(token)){
			return BaseMsg.errorMsg("token不能为空");
		}
		
		//验证token并返回跳转到前台或后台
		BaseMsg msg = userTokenService.checkToken(token);
		return msg;
	}
	
	@ApiOperation(value = "获取医院列表")
	@GetMapping("/getHospitalList")
	public BaseMsg getHospitalList() {
		
		Wrapper<Org> wrapper = new EntityWrapper<Org>(new Org());

		wrapper.eq("type", Constant.ORG_HTYPE);
		//医院名称升序
		wrapper.orderBy("name",true);

		List<Org> lists = orgService.selectList(wrapper);
		if (lists==null||lists.size()==0) {
			return BaseMsg.errorMsg("医院列表为空，请联系管理员");
		}
		return BaseMsg.successData(lists);
	}
	
//	@RequestMapping("captcha.jpg")
//	public void captcha(HttpServletResponse response)throws ServletException, IOException {
//		response.setHeader("Cache-Control", "no-store, no-cache");
//		response.setContentType("image/jpeg");
//
//		//生成文字验证码
//		String text = producer.createText();
//		//生成图片验证码
//		BufferedImage image = producer.createImage(text);
//		//保存到shiro session
//		ShiroUserUtil.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);
//
//		ServletOutputStream out = response.getOutputStream();
//		ImageIO.write(image, "jpg", out);
//		IOUtils.closeQuietly(out);
//	}
//
//	@GetMapping("/logout")
//	public BaseMsg logout(RedirectAttributes redirectAttributes) {
//		SecurityUtils.getSubject().logout();
//		return BaseMsg.successMsg("您已安全退出");
////		redirectAttributes.addFlashAttribute("message", "您已安全退出");
////		return "redirect:login";
//	}
	

}
