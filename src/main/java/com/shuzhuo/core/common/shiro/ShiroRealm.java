package com.shuzhuo.core.common.shiro;

import java.util.Set;

import com.shuzhuo.core.system.sys.entity.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.shuzhuo.core.system.sys.entity.UserToken;
import com.shuzhuo.core.system.sys.service.ShiroService;

/**
 * 
 * @author bgy
 * @data 2017-11-21
 *
 */
@Component
public class ShiroRealm extends AuthorizingRealm {

	private static final Logger log = LoggerFactory.getLogger(ShiroRealm.class);

	@Autowired
    private ShiroService shiroService;
	
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof ShiroOAuthToken;
    }

	/**
	 * 配置成功缓存这方法不会每次执行
	 * 授权(验证权限时调用)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		User user = (User)principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		if (user!=null) {
			Long userId = user.getId();
			//用户权限列表
	        Set<String> permsSet = shiroService.getUserPermissions(userId);
	        info.setStringPermissions(permsSet);
		}
		return info;
	}

	/**
     * 认证(登录时调用)
     */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)throws AuthenticationException {
		
		String accessToken = (String) token.getPrincipal();
		
        //根据accessToken，查询用户信息
        UserToken tokenEntity = shiroService.queryByToken(accessToken);
        //token失效
        if(tokenEntity == null || tokenEntity.getExpireTime().getTime() < System.currentTimeMillis()){
            throw new IncorrectCredentialsException("token失效，请重新登录");
        }

        //查询用户信息
        User user = shiroService.queryUser(tokenEntity.getUserId());
        //账号未激活或已停用
        if (user.getStatus()== 0) {
        	throw new LockedAccountException("账号未激活,请先激活账号");
        }
        if (user.getStatus()== -1) {
        	throw new LockedAccountException("账号已被停用,请联系管理员");
        }

        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user, accessToken, getName());
        return info;
		
		
//		
//		ShiroLoginToken token = (ShiroLoginToken) authenticationToken;
//		
//		if (ShiroLoginToken.LOGIN_TYPE_EMPID.equals(token.getLoginType()) && StringUtils.isBlank(token.getHospitalId())) {
//            throw new IncorrectCredentialsException("请选择医院");
//        }
//		if (StringUtils.isBlank(token.getUsername())) {
//            String errorMsg = "";
//            if (ShiroLoginToken.LOGIN_TYPE_EMPID.equals(token.getLoginType())) {
//                errorMsg = "请输入员工号";
//            } else {
//                errorMsg = "请输入账号";
//            }
//            throw new IncorrectCredentialsException(errorMsg);
//        }
//
//        if (null == token.getPassword()) {
//            throw new IncorrectCredentialsException("请输入密码");
//        }
//        
//        EntityWrapper<User> userWrapper = new EntityWrapper<>();
//        if (ShiroLoginToken.LOGIN_TYPE_PHONE.equals(token.getLoginType())){
//        	userWrapper.eq("phone", token.getUsername());
//        } else{
//        	userWrapper.eq("org_hospital_id", token.getHospitalId());
//        	userWrapper.eq("employee_id", token.getUsername());
//        }
//        List<User> userList = userService.selectList(userWrapper);
//        
//        if(userList==null||userList.size()==0){
//        	throw new IncorrectCredentialsException("账号不存在");
//        }else if(userList.size()>1){
//        	throw new IncorrectCredentialsException("账号对应多个用户,请联系管理员");
//        } else{
//        	User user = userList.get(0);
//        	if (user.getStatus()== 0) {
//        		throw new LockedAccountException("账号未激活,请先激活账号");
//			}
//        	if (user.getStatus()== -1) {
//        		throw new LockedAccountException("账号已被停用,请联系管理员");
//			}
//        	//设置用户session
//        	ShiroUserUtil.setUserToSession(user);
//        	return new SimpleAuthenticationInfo(user, user.getPassword(), ByteSource.Util.bytes(user.getSalt()), getName());
//        	
//        }        
        
	}
}
