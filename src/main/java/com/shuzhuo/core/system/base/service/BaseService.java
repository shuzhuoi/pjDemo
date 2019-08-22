package com.shuzhuo.core.system.base.service;

import com.shuzhuo.core.common.exception.CustomException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author
 */
@Transactional(readOnly = true)
public abstract class BaseService {

    /**
     * 日志对象
     */
    private static final Logger logger = LoggerFactory.getLogger(BaseService.class);

    public static Logger getLogger() {
        return logger;
    }

    /**
     * 记录logger抛异常
     * @param message 异常信息
     */
    public void exception(String message) {
        exception(500,message);
    }

    public void exception(int status ,String message) {
        getLogger().error(message);
        throw new CustomException(message,status);
    }

}
