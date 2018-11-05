package com.shuzhuo.core.common.shiro;

import org.apache.shiro.authc.AuthenticationToken;

public class ShiroOAuthToken implements AuthenticationToken{
	
	private static final long serialVersionUID = 8728641959538957532L;
	
	private String token;

    public ShiroOAuthToken(String token){
        this.token = token;
    }

    @Override
    public String getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

}
