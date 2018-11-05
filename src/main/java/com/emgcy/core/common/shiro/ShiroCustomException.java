package com.emgcy.core.common.shiro;

import org.apache.shiro.authc.AuthenticationException;

/**
 * 
 * @author chenshuzhuo
 * @since 2017-10-25
 */
public class ShiroCustomException extends AuthenticationException{

	
    public ShiroCustomException() {
        super();
    }

    public ShiroCustomException(String message) {
        super(message);
    }

 
    public ShiroCustomException(Throwable cause) {
        super(cause);
    }

    
    public ShiroCustomException(String message, Throwable cause) {
        super(message, cause);
    }

}
