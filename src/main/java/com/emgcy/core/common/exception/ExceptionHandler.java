//package com.emgcy.core.common.exception;
//
//import javax.validation.ConstraintDeclarationException;
//import javax.validation.ValidationException;
//import javax.ws.rs.core.Response;
//import javax.ws.rs.ext.ExceptionMapper;
//import javax.ws.rs.ext.Provider;
//
//import com.emgcy.core.common.base.BaseMsg;
//import com.emgcy.core.common.enm.ResultCode;
//
//@Provider
//public class ExceptionHandler  implements ExceptionMapper<ValidationException> {
//
//    @Override
//    public Response toResponse(ValidationException e) {
//
//    	System.out.println("ExceptionHandler.toResponse()");
//    	 if (e instanceof ConstraintDeclarationException) {
//    		return Response.ok().entity(BaseMsg.customMsg(ResultCode.PARAMETER_ERROR)).build();
//    	}
////    	System.out.println(e);
//        return Response.ok().entity(BaseMsg.error()).build();
//    }
//
//
//}