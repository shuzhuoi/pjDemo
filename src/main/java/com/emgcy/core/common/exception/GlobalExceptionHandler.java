package com.emgcy.core.common.exception;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.springframework.web.bind.MissingServletRequestParameterException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.servlet.ModelAndView;
import com.emgcy.core.common.base.BaseMsg;
import com.emgcy.core.common.enm.ResultCode;
import com.emgcy.core.common.exception.bean.DataInvalidResult;

/**
 * 
 * @author chenshuzhuo
 * @since 2017-10-25
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	/**
	 * 自定义异常
	 */
	@ExceptionHandler(CustomException.class)
	public BaseMsg handleRRException(CustomException e){
		BaseMsg msg = new BaseMsg();
		msg.put("code", e.getCode());
		msg.put("msg", e.getMessage());

		return msg;
	}
	
	@ExceptionHandler(DuplicateKeyException.class)
	public BaseMsg handleDuplicateKeyException(DuplicateKeyException e){
		logger.error(e.getMessage(), e);
		return BaseMsg.errorMsg("数据库中已存在该记录");
	}
	
	@ExceptionHandler({UnauthorizedException.class, AuthorizationException.class })
	@ResponseStatus(HttpStatus.UNAUTHORIZED)
	public ModelAndView unauthenticatedException(NativeWebRequest request, UnauthorizedException e) {
		ModelAndView mv = new ModelAndView();
		mv.addObject("exception", e);
		mv.setViewName("unauthorized");
		return mv;
	}
	
    @ExceptionHandler(value = {BindException.class,MethodArgumentNotValidException.class,ValidationException.class,MissingServletRequestParameterException.class})
    @ResponseBody
    public Object dataBindExceptionHandler(HttpServletRequest request, Exception ex) throws Exception {
        String clientToken = request.getParameter("token");
        if(StringUtils.isNotBlank(clientToken)){
            request.getSession(false).setAttribute("token", clientToken);

        }

        logger.error("BindException");
//        String requestURL = request.getRequestURL().toString();
        //按需重新封装需要返回的错误信息
        List<DataInvalidResult> results = new ArrayList<>();
        BindingResult bindingResult = null;

        if(ex instanceof BindException){
            bindingResult = ((BindException) ex).getBindingResult();
            //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
            for (FieldError error : bindingResult.getFieldErrors()) {
                DataInvalidResult dataInvalidResult = new DataInvalidResult();
                dataInvalidResult.setDefaultMessage(error.getDefaultMessage());
                dataInvalidResult.setField(error.getField());
                dataInvalidResult.setRejectedValue(error.getRejectedValue());
                results.add(dataInvalidResult);
            }
        }else if (ex instanceof MethodArgumentNotValidException){
            bindingResult = ((MethodArgumentNotValidException) ex).getBindingResult();
            //解析原错误信息，封装后返回，此处返回非法的字段名称，原始值，错误信息
            for (FieldError error : bindingResult.getFieldErrors()) {
                DataInvalidResult dataInvalidResult = new DataInvalidResult();
                dataInvalidResult.setDefaultMessage(error.getDefaultMessage());
                dataInvalidResult.setField(error.getField());
                dataInvalidResult.setRejectedValue(error.getRejectedValue());
                results.add(dataInvalidResult);
            }
        }else if (ex instanceof ConstraintViolationException){
            Set<ConstraintViolation<?>> constraintViolationSet = ((ConstraintViolationException) ex).getConstraintViolations();
            if(!constraintViolationSet.isEmpty()){
                Iterator<ConstraintViolation<?>> iterator = constraintViolationSet.iterator();
                while (iterator.hasNext()){
                    ConstraintViolation<?> next = iterator.next();
                    DataInvalidResult dataInvalidResult = new DataInvalidResult();
                    dataInvalidResult.setDefaultMessage(next.getMessage());
                    dataInvalidResult.setField(next.getMessageTemplate());
                    dataInvalidResult.setRejectedValue(next.getInvalidValue());
                    results.add(dataInvalidResult);
                }
            }
        }else if(ex instanceof MissingServletRequestParameterException){
        	return BaseMsg.customMsg(ResultCode.PARAMETER_ERROR);
        }
        return BaseMsg.customMsg(ResultCode.PARAMETER_ERROR, results);
    }

	
	@ExceptionHandler(value = Exception.class)
	public BaseMsg defaultErrorHandler(HttpServletRequest request, Exception ex) throws Exception {
		logger.error(ex.getMessage(),ex);
		return BaseMsg.errorMsg("系统未知错误");
	}

}
