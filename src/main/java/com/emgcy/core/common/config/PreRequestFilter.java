//package com.emgcy.core.common.config;
//
//import java.io.IOException;
//
//import javax.ws.rs.container.ContainerRequestContext;
//import javax.ws.rs.container.ContainerRequestFilter;
//import javax.ws.rs.container.PreMatching;
//
//import org.glassfish.jersey.server.ContainerRequest;
//
//@PreMatching
//public class PreRequestFilter implements ContainerRequestFilter {
//	
//	@Override
//	public void filter(ContainerRequestContext requestContext) throws IOException {
//		if (requestContext instanceof ContainerRequest) {
//			ContainerRequest containerRequest=(ContainerRequest)requestContext;
//			String baseUri = containerRequest.getBaseUri().toString();
//			String substring = containerRequest.getRequestUri().toString().substring(baseUri.length()-1);
//			System.out.println(baseUri+substring);
//			substring=substring.replace("/rest", "");
//			System.out.println(baseUri+substring);
////			containerRequest.setRequestUri(URI.create(baseUri+substring));
//			System.out.println(containerRequest.getRequestUri());
//			
//		}
//	}
//	 
//}
