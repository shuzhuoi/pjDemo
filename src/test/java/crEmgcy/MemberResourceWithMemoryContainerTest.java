//package crEmgcy;
//
//import javax.ws.rs.client.Client;
//import javax.ws.rs.client.ClientBuilder;
//import javax.ws.rs.client.Entity;
//import javax.ws.rs.client.WebTarget;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import org.junit.Test;
//
//import com.emgcy.core.interfaces.restful.web.emergency.dto.EmergencyDto;
//
//public class MemberResourceWithMemoryContainerTest {
//	
////	private WebTarget target;
//
//	private final String serverUri = "http://localhost:8081/crEmgcy/rest/";
//
//	/**
//	 * super.setUp();开启内置容器
//	 */
////	@Before
////	public void setUp() throws Exception {
////		super.setUp();
////		target = client().target("http://localhost:8081/crEmgcy/rest/");
////	}
//
//	@Test
//	public void postTest() {
//		Client client = ClientBuilder.newClient();
//		WebTarget target = client.target(serverUri + "emergency/ordinarySave");
//
//		EmergencyDto bean = new EmergencyDto();
////		bean.setName("34555");
////		bean.setIdentity("1");
////		bean.setSex("66");
////		bean.setCritical("1");
////		bean.setGrade("0");
////		bean.setType(0L);
//		Entity<EmergencyDto> entity = Entity.entity(bean, MediaType.APPLICATION_JSON);
////		Response post = target.request().
////		System.out.println("MemberResourceWithMemoryContainerTest.postTest()");
////		System.out.println(post);
//		Response response = target.request().buildPost(entity).invoke();
//		System.out.println("-->相应信息");
//		System.out.println(response);
//		String readEntity = response.readEntity(String.class);  
//	    System.out.println(readEntity);  
//		response.close();
//
//	}
//	
////	@Test  
////    public void getTest() {  
////  
////        Client client = ClientBuilder.newClient();  
////        WebTarget target = client.target(serverUri + "test/hello1");  
////        Response response = target.request()  
////                                  .buildGet()  
////                                  .invoke();  
////        String readEntity = response.readEntity(String.class);  
////        System.out.println(readEntity);  
////        response.close();  
////          
////    } 
//}
